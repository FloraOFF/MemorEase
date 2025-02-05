package com.memorease.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.memorease.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDAO extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "memorease.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "notes";

    public NoteDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, conteudo TEXT, data TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<Note> get() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY data DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setTitulo(cursor.getString(1));
                note.setConteudo(cursor.getString(2));
                note.setData(cursor.getString(3));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notes;
    }

    public Note get(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"id", "titulo", "conteudo", "data"},
                "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Note note = new Note();
            note.setId(cursor.getInt(0));
            note.setTitulo(cursor.getString(1));
            note.setConteudo(cursor.getString(2));
            note.setData(cursor.getString(3));
            cursor.close();
            db.close();
            return note;
        }

        return null;
    }

    public Note save(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", note.getTitulo());
        values.put("conteudo", note.getConteudo());
        values.put("data", note.getData());

        if (note.getId() <= 0) { // Novo registro
            long id = db.insert(TABLE_NAME, null, values);
            note.setId((int) id);
        } else { // Atualizar existente
            db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(note.getId())});
        }

        db.close();
        return note;
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
