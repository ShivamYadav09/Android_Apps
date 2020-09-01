package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.HashSet;

import static com.example.notes.MainActivity.arrayAdapter;
import static com.example.notes.MainActivity.notes;

public class NoteEditor extends AppCompatActivity {

    int noteID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = findViewById(R.id.editText);
        Intent intent = getIntent();
        noteID = intent.getIntExtra("noteID", -1);

        if(noteID != -1){
            editText.setText(notes.get(noteID));
        }
        else{
            notes.add(""); //creating an empty note
            noteID = notes.size()-1;
            arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /* Even if the note to be added is empty still it is added. Work on how to fix that

                Log.i("info", String.valueOf(charSequence.length()));
                Log.i("info", String.valueOf(noteID));
                //notes.remove(1);
                if(String.valueOf(charSequence.length()).equalsIgnoreCase("0")) {
                    notes.remove(noteID+1);
                    arrayAdapter.notifyDataSetChanged();
                    //notes.set(noteID, String.valueOf(charSequence));
                    //arrayAdapter.notifyDataSetChanged();
                }
                else{
                    notes.set(noteID, String.valueOf(charSequence));
                    arrayAdapter.notifyDataSetChanged();
                }*/
                notes.set(noteID, String.valueOf(charSequence));
                arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
