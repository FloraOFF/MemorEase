package com.memorease.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.memorease.R;
import com.memorease.controller.NoteController;
import com.memorease.model.Note;

import java.util.List;

public class ShowNoteActivity extends AppCompatActivity {

    ListView listarDados;

    private NoteController noteController;
    private Note note;

    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_note);

        listarDados = findViewById(R.id.listViewDados); // Certifique-se de ter este ID no seu layout
        noteController = new NoteController(this);

        listarDados();

    /*    listarDados.setOnItemClickListener((parent, view, position, id) -> {
            Note note = adapter.getItem(position);
            if (note != null) {
                // Implemente aqui a navegação para a tela de edição
                Intent editarAnotacao = new Intent(this, AddNoteActivity.class);
                editarAnotacao.putExtra("note_id", note.getId());
                startActivity(editarAnotacao);
                // Intent intent = new Intent(this, EditNoteActivity.class);
                // intent.putExtra("note_id", note.getId());
                // startActivity(intent);
            }
        });*/
    }

    public void listarDados() {
        List<Note> notes = noteController.get();

        adapter = new ListAdapter(this, notes);
        listarDados.setAdapter(adapter);

    }

    public void addNote(View view) {
        Intent novaAnotacao = new Intent(getApplicationContext(), AddNoteActivity.class);
        startActivity(novaAnotacao);
    }

    public void startNote(View view) {
        Intent inicio = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(inicio);
    }
}