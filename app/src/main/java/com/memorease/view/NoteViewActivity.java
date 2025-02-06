package com.memorease.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.memorease.R;
import com.memorease.controller.NoteController;
import com.memorease.model.Note;

public class NoteViewActivity extends AppCompatActivity {
    private NoteController noteController;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_view);

        noteController = new NoteController(this);

        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            try {
                Integer noteId = extra.getInt("note_id", -1);

                note = noteController.get(noteId);

                if (note != null) {

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}