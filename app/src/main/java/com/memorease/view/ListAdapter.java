package com.memorease.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.memorease.R;
import com.memorease.controller.NoteController;
import com.memorease.model.Note;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Note> {
    private Context context;
    private List<Note> notes;
    private NoteController noteController;

    public ListAdapter(Context context, List<Note> notes) {
        super(context, R.layout.list_note_view, notes);
        this.context = context;
        this.notes = notes;
        this.noteController = new NoteController(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_note_view, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Note note = notes.get(position);
        if (note != null) {
            holder.noteTitle.setText(note.getTitulo());
            holder.noteDate.setText(note.getData());

            // Desabilita foco nos botões
            holder.editButton.setFocusable(false);
            holder.editButton.setFocusableInTouchMode(false);
            holder.deleteButton.setFocusable(false);
            holder.deleteButton.setFocusableInTouchMode(false);

            holder.editButton.setOnClickListener(v -> {
                Intent edicao = new Intent(context, AddNoteActivity.class);
                edicao.putExtra("note_id", note.getId());
                context.startActivity(edicao);
            });

            holder.deleteButton.setOnClickListener(v -> {
                noteController.delete(note.getId());
                notes.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Nota deletada com sucesso!",
                        Toast.LENGTH_SHORT).show();
            });

            // Clique no item da lista (exceto nos botões)
            holder.itemContainer.setOnClickListener(v -> {
                Intent verAnotacao = new Intent(context, NoteViewActivity.class);
                verAnotacao.putExtra("note_id", note.getId());
                context.startActivity(verAnotacao);
               // Toast.makeText(context, "Clicou no item: " + note.getTitulo(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }

    static class ViewHolder {
        LinearLayout itemContainer;
        TextView noteTitle;
        TextView noteDate;
        ImageButton editButton;
        ImageButton deleteButton;

        ViewHolder(View view) {
            itemContainer = view.findViewById(R.id.itemContainer);
            noteTitle = view.findViewById(R.id.noteTitle);
            noteDate = view.findViewById(R.id.noteDate);
            editButton = view.findViewById(R.id.editButton);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }
}

