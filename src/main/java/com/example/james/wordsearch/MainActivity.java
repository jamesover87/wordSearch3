package com.example.james.wordsearch;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MainActivity extends Activity implements OnClickListener {

    // get references to widgets
    private EditText letterNumEditText;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private Button searchButton;

    // define instance variables; wordSearch is the partial word that will be used in the SQLite database query
    private int letterNum;
    private String letterNumString;
    public String wordSearch;
    private ArrayList<EditText> letterBoxes;

    // instantiate database object
    private WordDB wordDB = new WordDB(this);

    // get method for wordSearch string (i.e., the partial word the user enters)
    public String getWordSearch(){
        return wordSearch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the widgets
        letterNumEditText = (EditText) findViewById(R.id.letterNumEditText);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
        searchButton = (Button) findViewById(R.id.searchButton);

        // set listeners
        searchButton.setOnClickListener(this);

        // create ArrayList of text boxes (currently only supports words of up to 6 characters, since there are only 6 text boxes)
        letterBoxes = new ArrayList<EditText>();
        letterBoxes.add(editText1);
        letterBoxes.add(editText2);
        letterBoxes.add(editText3);
        letterBoxes.add(editText4);
        letterBoxes.add(editText5);
        letterBoxes.add(editText6);
    }

    // method to search database for matches for partial word
    public void search(){

        // get the number of letters
        letterNumString = letterNumEditText.getText().toString();
        try {
            letterNum = Integer.parseInt(letterNumString);
        }
        catch (NumberFormatException e){
            letterNum = 0;
        }

        // Use StringBuilder to assemble word from characters entered by user
        String tempLetterBoxText;
        StringBuilder wordSB = new StringBuilder(letterNum);

        //  iterate through ArrayList of text boxes to build partial word; blank text boxes are converted to underscores
        for (int i = 0; i < letterNum; i++){
            if (letterBoxes.get(i).getText().length() == 0){
                tempLetterBoxText = "_";
            }
            else{
                tempLetterBoxText = letterBoxes.get(i).getText().toString();
            }
            wordSB.append(tempLetterBoxText);
        }
        // convert StringBuilder to String
        wordSearch = wordSB.toString();

        // output partial word to Logcat
        Log.d("word search", wordSearch);

        // call searchWords method and iterate through words that match partial word
        ArrayList<Word> searchWords = wordDB.searchWords(wordSearch);

        Log.d("word search", searchWords.size() + " matching word(s) have been found.");

        // output only first 20 matches
        if (searchWords.size() > 20){
            Log.d("word search", "Returning first 20 matches.");
            for (int i = 0; i < 20; i++) {
                Log.d("word search", "Word: " + searchWords.get(i).getName());
                Log.d("word search", "Definition: " + searchWords.get(i).getDefinition());
                Log.d("word search", "Other info: " + searchWords.get(i).getOther());
            }
        }

        else for (int i = 0; i < searchWords.size(); i++) {
            Log.d("word search", "Word: " + searchWords.get(i).getName());
            Log.d("word search", "Definition: " + searchWords.get(i).getDefinition());
            Log.d("word search", "Other info: " + searchWords.get(i).getOther());
        }

    }

    // call search method when search button is clicked
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.searchButton:
                search();
                break;
        }
    }
}
