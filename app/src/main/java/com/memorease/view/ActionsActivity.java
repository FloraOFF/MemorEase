package com.memorease.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.memorease.R;

public class ActionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actions);
    }

    public void addNote(View view) {
        Intent salvarAnotacao = new Intent(getApplicationContext(), AddNoteActivity.class);
        startActivity(salvarAnotacao);
    }

    public void showNote(View view) {
        Intent mostrarAnotacao = new Intent(getApplicationContext(), ShowNoteActivity.class);
        startActivity(mostrarAnotacao);
    }
}