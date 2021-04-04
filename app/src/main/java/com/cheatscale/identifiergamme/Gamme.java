package com.cheatscale.identifiergamme;

import java.util.List;

public class Gamme {
    private String nom;
    private List<String> notesDeLaGamme;
    private String relativeMineure;
    private int scoreGamme;

    public Gamme(String nom, List<String> notesDeLaGamme, String relativeMineure) {
        this.nom = nom;
        this.notesDeLaGamme = notesDeLaGamme;
        this.scoreGamme = 0;
        this.relativeMineure = relativeMineure;
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

    public List<String> notesDeLaGamme() { return this.notesDeLaGamme; }
    public String relativeMineure() { return this.relativeMineure; }
}