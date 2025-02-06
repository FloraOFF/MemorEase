package com.memorease.model;

import lombok.Data;

@Data
public class Note {
    private int id;
    private String titulo;
    private String conteudo;
    private String data;
    private ETipo tipoNote;
}
