package com.example.james.wordsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by James on 8/1/2016.
 */
public class WordDB {

    // database constants
    public static final String DB_NAME = "word.db";
    public static final int DB_VERSION = 1;

    // word table constants
    public static final String WORD_TABLE = "word";

    public static final String WORD_ID = "_id";
    public static final int WORD_ID_COL = 0;

    public static final String WORD_NAME = "word_name";
    public static final int WORD_NAME_COL = 1;

    public static final String WORD_DEFINITION = "word_definition";
    public static final int WORD_DEFINITION_COL = 2;

    public static final String WORD_OTHER = "word_other";
    public static final int WORD_OTHER_COL = 3;

    // database object and database helper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public WordDB(Context context){
        dbHelper = new DBHelper (context, DB_NAME, null, DB_VERSION);
    }

    // CREATE and DROP table statements

    public static final String CREATE_WORD_TABLE =
            "CREATE TABLE " + WORD_TABLE + " (" +
                    WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WORD_NAME + " TEXT NOT NULL, " +
                    WORD_DEFINITION + " TEXT, " +
                    WORD_OTHER + " TEXT);";

    public static final String DROP_WORD_TABLE =
            "DROP TABLE IF EXISTS " + WORD_TABLE;

    //private static class DBHelper extends SQLiteOpenHelper {
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_WORD_TABLE);

            Log.d("word search", "Inside DBHelper.onCreate");
            Log.d("word search", "Inside DBHelper.onCreate calling loadWords");
            loadWords(db);
            Log.d("word search", "Inside DBHelper.onCreate returned from loadWords");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.d("word search", "Upgrading db from version " + oldVersion + " to " + newVersion);

            db.execSQL(WordDB.DROP_WORD_TABLE);
            onCreate(db);
        }
    }

    private void openReadableDB(){
        db = dbHelper.getReadableDatabase();
    }

    private void openWritableDB(){
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB(){
        if (db != null)
            db.close();
    }
    /*
    This method returns all words in the database.
    It's not actually used by the program and should
    probably be removed later, but I'll leave it in
    for now.
     */
    public ArrayList<Word> getWords(){

        this.openReadableDB();
        Cursor cursor = db.query(WORD_TABLE, null, null, null, null, null,null);
        ArrayList<Word> words = new ArrayList<Word>();
        while (cursor.moveToNext()){
            Word word = new Word();
            word.setId(cursor.getInt(WORD_ID_COL));
            word.setName(cursor.getString(WORD_NAME_COL));
            word.setDefinition(cursor.getString(WORD_DEFINITION_COL));
            word.setOther(cursor.getString(WORD_OTHER_COL));

            words.add(word);
        }
        if(cursor != null)
            cursor.close();
        this.closeDB();

        return words;

    }

    /*
    Searches the database using the wordSearch string provided by
    the search method in the MainActivity class. Uses a LIKE operator
    in the WHERE clause to find matching words and returns them
    as an ArrayList.
     */
    public ArrayList<Word> searchWords(String wordSearch){

        String where =
                WORD_NAME + " LIKE ?";
        String[] whereArgs = {wordSearch};

        this.openReadableDB();
        Cursor cursor = db.query(WORD_TABLE, null, where, whereArgs, null, null,null);
        ArrayList<Word> words = new ArrayList<Word>();
        while (cursor.moveToNext()){
            Word word = new Word();
            word.setId(cursor.getInt(WORD_ID_COL));
            word.setName(cursor.getString(WORD_NAME_COL));
            word.setDefinition(cursor.getString(WORD_DEFINITION_COL));
            word.setOther(cursor.getString(WORD_OTHER_COL));

            words.add(word);
        }
        if(cursor != null)
            cursor.close();
        this.closeDB();

        return words;

    }
	
	
	
	
	
	

    //private static void loadWords(SQLiteDatabase db) {
    //private void loadWords(SQLiteDatabase db) throws IOException {
    private void loadWords(SQLiteDatabase db)  {

        Log.d("word search", "Inside loadWords");

        /*
        // AssetManager stuff may have to happen inside the Activity
        AssetManager assetManager = this.getAssets();
        //assetManager.getAssets();

        try {
            //InputStream in = getClass().getResourceAsStream("/1.txt");
            //BufferedReader input = new BufferedReader(new InputStreamReader(in));

            //InputStream is = getClass().getResourceAsStream("/testWordInserts.txt");
            InputStream is = getClass().getResourceAsStream("/com/example/james/wordsearch/WordDB/testWordInserts.txt");
            Log.d("word search", "loadWords - is initialized");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //InputStreamReader isr = new InputStreamReader(is);
            //Log.d("word search", "loadWords - isr initialized");

            //BufferedReader br = new BufferedReader(isr);
            Log.d("word search", "loadWords - br initialized");

            String line;

            while ((line = br.readLine()) != null) {
                //db.execSQL(line);
                Log.d("word search", line);
            }
            br.close();
            //isr.close();
            is.close();
        }
        catch (Exception e)
        {
            // log this
            Log.d("word search", "Something went wrong in loadWords");
        }
        */


        /* plan B test data */
        db.execSQL("INSERT INTO word VALUES (NULL, 'absorption', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'achieve', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'adjectives', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'aerating', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'agrarian', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'aleut', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'alphonse', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'amoebic', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'aneurysms', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'anthers', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'apogees', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'aquatint', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'arminius', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'aspell', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'athabasca', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'aureola', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'awls', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'badgering', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'banged', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'barred', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'bazookas', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'beetling', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'benefactresses', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'beulah', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'biodegrade', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'blame', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'bloodletting', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'bode', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'booklets', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'bow', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'breadboard', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'bristling', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'bubo', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'bunged', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'butterballs', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'calaboose', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'canalizes', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'capsulizes', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'carpel', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'catastrophic', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'celandine', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'chaise', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'chaster', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'chicory', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'choppiest', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'circuity', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'clawed', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'clothesline', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'coconuts', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'collegian', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'commingles', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'compositions', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'condoms', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'conjunctions', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'consulates', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'convectors', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'corduroy', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'cosset', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'cousins', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'crayfish', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'croat', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'cryptographer', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'curtailed', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'dainty', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'davids', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'decca', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'defalcated', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'deleting', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'demurest', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'derailing', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'detained', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'dianne', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'dimmer', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'disbursements', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'disharmony', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'disquisitions', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'divesting', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'domestication', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'dowdies', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'drew', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'duisburg', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'earldom', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'eduardo', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'electorate', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'embattled', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'encamps', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'enhancing', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'enuresis', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'eris', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'ethologist', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'examiners', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'exorbitantly', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'exteriors', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'fades', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'fascinates', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'felicitate', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'fiduciary', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'firelighters', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'flashcubes', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'floral', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'folios', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'foreshadowing', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'fox', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'frequents', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'fslic', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'futzing', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'gantry', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'geared', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'gershwin', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'gizzard', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'glycogen', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'gored', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'grapevines', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'grinders', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'guarantees', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'guzman', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'hallowing', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'hardball', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'havoline', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'heavyweight', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'heralding', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'hightailed', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'hoggishly', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'honoree', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'hothouses', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'humiliated', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'hyper', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'igloos', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'impairs', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'impressionability', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'includes', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'indiscretion', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'infinitely', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'innards', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'instinct', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'interlocutors', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'introspective', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'ironwood', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'jackknife', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'jeopardizing', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'jollily', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'jurisdiction', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'kerfuffles', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'kirk', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'krauts', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'lamer', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'laterals', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'leapt', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'leonardo', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'liege', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'lintiest', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'lobby', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'looting', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'ludicrousness', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'macaws', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'mainlands', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'mandrills', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'maria', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'massing', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'mccain', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'melbourne', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'merton', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'microelectronic', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'milquetoast', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'miscalling', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'mistassini', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'molders', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'montessori', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'motifs', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'muhammadanism', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'muskox', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'napoleonic', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'necromancy', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'newbies', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'nissan', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'noninvasive', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'nostalgically', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'nutmeg', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'obtruding', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'oinks', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'opportune', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'orthographic', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'outpatients', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'overdress', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'overspreads', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'pagination', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'pantheon', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'parlays', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'paternally', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'pectic', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'pentagrams', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'permissions', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'petunia', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'phototypesetting', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'pillsbury', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'placebo', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'plenipotentiaries', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'poisson', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'poo', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'postman', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'prc', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'premiering', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'prevaricated', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'procreated', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'proof', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'provided', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'pulitzer', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'pushkin', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'quarto', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'racetrack', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'rams', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'ravened', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'reassuring', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'recommendable', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'redeveloped', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'refinish', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'rehang', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'relining', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'reorganization', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'repurchased', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'respray', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'retrogrades', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'rfd', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'rinses', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'roguery', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'roughnecks', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'runabout', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'said', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'sandpaper', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'savored', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'schisms', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'scram', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'scythia', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'seesaws', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'sentimental', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'sexology', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'sheep', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'shodden', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'shuffled', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'silicons', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'sizzlers', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'slate', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'slovaks', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'snacking', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'snuffly', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'some', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'southwests', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'speedboat', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'splines', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'sputnik', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'stalk', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'steamfitting', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'stinkbug', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'strains', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'stubbiest', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'subordination', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'suggested', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'superintendent', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'survives', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'swine', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'tabby', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'tampons', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'tauten', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'telephotography', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'terran', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'thermally', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'throwbacks', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'timmy', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'toggled', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'tors', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'traffics', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'transportation', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'tricky', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'trudged', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'turgidly', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'typhoon', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'unbiassed', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'underclassmen', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'unenlightened', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'uninstall', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'unpolluted', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'unstraps', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'uprooting', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'vagrants', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'vegetates', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'veterinarians', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'virgule', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'vomiting', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'wallowing', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'waterfalls', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'weepings', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'whiled', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'wifi', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'winters', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'woodsier', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'wriggled', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'yearns', 'Word definition here', 'Other word info here')");
        db.execSQL("INSERT INTO word VALUES (NULL, 'zephyr', 'Word definition here', 'Other word info here')");
        /* */


    }
	
	

}
