package com.example.identifiergamme;

import java.util.ArrayList;
import java.util.List;

public class Gamme {
    private String nom;
    private List<String> notesDeLaGamme;
    private int scoreGamme;

    public Gamme(String nom, List<String> notesDeLaGamme) {
        this.nom = nom;
        this.notesDeLaGamme = notesDeLaGamme;
        this.scoreGamme = 0;
    }

    public String nom() { return this.nom; }
    public void incrementerScore() { this.scoreGamme++; }
    public int scoreGamme() { return this.scoreGamme; }

    public boolean possedeLaNote(String note) {
        for (int i = 0; i < notesDeLaGamme.size(); i++) {
            if (note.compareTo(notesDeLaGamme.get(i)) == 0) {
                return true;
            }
        }
        return false;
    }
}