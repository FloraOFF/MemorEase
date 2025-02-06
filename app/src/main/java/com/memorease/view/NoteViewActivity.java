package com.memorease.view;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView titulo;
    TextView conteudo;
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_view);

        noteController = new NoteController(this);

        Bundle extra = getIntent().getExtras();

        titulo = findViewById(R.id.textTitulo);
        conteudo = findViewById(R.id.textConteudo);
        data = findViewById(R.id.textData);

        if (extra != null) {
            try {
                Integer noteId = extra.getInt("note_id", -1);

                note = noteController.get(noteId);

                if (note != null) {
                    titulo.setText(note.getTitulo());
                    conteudo.setText(note.getConteudo());
                    data.setText(note.getData());
                } else {
                    Toast.makeText(this, "Nota não encontrada!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "ID da nota inválido!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}