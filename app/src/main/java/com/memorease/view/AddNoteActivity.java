package com.memorease.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.memorease.R;
import com.memorease.controller.NoteController;
import com.memorease.model.Note;

public class AddNoteActivity extends AppCompatActivity {

    EditText titulo;
    EditText conteudo;
    EditText data;

    private NoteController noteController;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);

        // Inicializa o controller
        noteController = new NoteController(this);

        // Inicializa as referências dos campos
        titulo = findViewById(R.id.textTitulo);
        conteudo = findViewById(R.id.textConteudo);
        data = findViewById(R.id.textData);

        // Aplica a máscara para o campo de data
        /*data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Adiciona a barra "/" ao digitar a data
                if (charSequence.length() == 2 || charSequence.length() == 5) {
                    data.append("/");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });*/

        Bundle extra = getIntent().getExtras();

        if (extra != null) {

            try {
                Integer noteId = extra.getInt("note_id", -1);

                note = noteController.get(noteId); // Busca a nota no banco

                if (note != null) {
                    // Atualiza os campos com os dados da nota encontrada
                    titulo.setText(note.getTitulo());
                    conteudo.setText(note.getConteudo());
                    data.setText(note.getData());
                } else {
                    Toast.makeText(this, "Nota não encontrada!", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID da nota inválido!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void salvarNota(View view) {
        if (note == null) {
            note = new Note();
        }

        // Obtém os textos dos campos
        String tituloText = titulo.getText().toString().trim();
        String conteudoText = conteudo.getText().toString().trim();
        String dataText = data.getText().toString().trim();

        // Validação básica
        if (tituloText.isEmpty() || conteudoText.isEmpty() || dataText.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Define os valores na nota
        note.setTitulo(tituloText);
        note.setConteudo(conteudoText);
        note.setData(dataText);

        // Salva a nota
        try {
            //Toast.makeText(this, "Tipo do ID: " + ((Object) note.getId()).getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
            if (note.getId() > 0) {
                // Atualiza a nota existente
                noteController.createOrUpdate(note);
                Toast.makeText(this, "Nota atualizada com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                // Cria uma nova nota
                noteController.createOrUpdate(note);
                Toast.makeText(this, "Nota salva com sucesso!", Toast.LENGTH_SHORT).show();
            }

            // Limpa os campos após salvar
            limparCampos();

            // Opcional: fecha a activity após salvar
            finish();

            Intent anotacoes = new Intent(getApplicationContext(), ShowNoteActivity.class);
            startActivity(anotacoes);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao salvar a nota: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void limparCampos() {
        titulo.setText("");
        conteudo.setText("");
        data.setText("");
    }

    private void listarNotes(View view) {
        Intent anotacoes = new Intent(getApplicationContext(), ShowNoteActivity.class);
        startActivity(anotacoes);
    }
}