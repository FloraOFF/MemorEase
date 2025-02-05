package com.memorease.controller;

import android.content.Context;

import com.memorease.dao.NoteDAO;
import com.memorease.model.Note;

import java.util.List;

public class NoteController {
    private NoteDAO dao;

    public NoteController(Context context) {
        this.dao = new NoteDAO(context);
    }
    public List<Note> get() {
        return dao.get();
    }
    public Note get(int id) {
        return dao.get(id);
    }

    /*public List<Note> get(String termoBusca) {
        return dao.get(termoBusca);
    }*/
    public Note createOrUpdate(Note note) {
        return dao.save(note);
    }
    public void delete(int id) {
        dao.delete(id);
    }
}
