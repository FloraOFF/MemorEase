package com.memorease.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.memorease.R;
import com.memorease.controller.NoteController;
import com.memorease.model.ETipo;
import com.memorease.model.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {

    EditText titulo;
    EditText conteudo;
    EditText data;

    private Spinner spinnerTipo;

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
        String dataText = data.getText().toString();
        spinnerTipo = findViewById(R.id.spinnerTipo);

        // Torna o EditText da data não editável
        data.setFocusable(false);
        data.setClickable(true);

        // Configura o click listener para abrir o DatePicker
        data.setOnClickListener(v -> showDatePickerDialog());

        // Converte os valores do enum ETipo para um array de Strings
        String[] tipos = new String[ETipo.values().length];
        for (int i = 0; i < ETipo.values().length; i++) {
            tipos[i] = ETipo.values()[i].name(); // Converte para string
        }

        // Configura o Adapter para o Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_color, tipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

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

                    int position = adapter.getPosition(note.getTipoNote().name());
                    spinnerTipo.setSelection(position);

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
        String tipoSelecionado = spinnerTipo.getSelectedItem().toString();

        // Validação básica
        if (tituloText.isEmpty() || conteudoText.isEmpty() || dataText.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Define os valores na nota
        note.setTitulo(tituloText);
        note.setConteudo(conteudoText);
        note.setData(dataText);
        note.setTipoNote(ETipo.valueOf(tipoSelecionado));

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

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    // Formata a data como dd/MM/yyyy
                    String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d",
                            dayOfMonth, (monthOfYear + 1), year1);
                    data.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }
}