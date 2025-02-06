package com.memorease.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.memorease.R;
import com.memorease.controller.NoteController;
import com.memorease.model.Note;

import java.util.List;

public class ShowNoteActivity extends AppCompatActivity {

    ListView listarDados;
    EditText searchEditText;

    private NoteController noteController;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        searchEditText = findViewById(R.id.searchEditText);
        listarDados = findViewById(R.id.listViewDados); // Certifique-se de ter este ID no seu layout
        noteController = new NoteController(this);

        listarDados();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String searchTerm = charSequence.toString();
                // Chama o método de filtro
                List<Note> filteredNotes = noteController.get(searchTerm);
                // Atualiza o adaptador da ListView
                updateListView(filteredNotes);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        /*listarDados.setOnItemClickListener((parent, view, position, id) -> {
            Note note = (Note) parent.getItemAtPosition(position);

            Toast.makeText(this, position + " Anotação: " + note.getTitulo(), Toast.LENGTH_SHORT).show();
            if (note != null) {
                // Implemente aqui a navegação para a tela de edição
                Intent verAnotacao = new Intent(this, NoteViewActivity.class);
                verAnotacao.putExtra("note_id", note.getId());
                startActivity(verAnotacao);
            }
        });*/
    }

    public void listarDados() {
        List<Note> notes = noteController.get();
        adapter = new ListAdapter(this, notes);
        listarDados.setAdapter(adapter);
    }

    private void updateListView(List<Note> filteredNotes) {
        adapter = new ListAdapter(this, filteredNotes);
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
