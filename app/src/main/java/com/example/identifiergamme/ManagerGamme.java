package com.example.identifiergamme;

import android.util.Log;

import com.example.scalefinder.detectionnote.ManagerNote;

import java.util.ArrayList;
import java.util.List;

public class ManagerGamme {
    private List<Gamme> gammesTriees = new ArrayList<>();
    private int nombreDeNotesAjoutees;

    public ManagerGamme() {
        this.gammesTriees.add(new Gamme("DO", new ArrayList<String>() {{ add("DO"); add("RE"); add("MI"); add("FA"); add("SOL"); add("LA"); add("SI");}}));
        this.gammesTriees.add(new Gamme("REB", new ArrayList<String>() {{ add("REB"); add("MIB"); add("FA"); add("SOLB"); add("LAB"); add("SIB"); add("DO");}}));
        this.gammesTriees.add(new Gamme("RE", new ArrayList<String>() {{ add("RE"); add("MI"); add("SOLB"); add("SOL"); add("LA"); add("SI"); add("REB");}}));
        this.gammesTriees.add(new Gamme("MIB", new ArrayList<String>() {{ add("MIB"); add("FA"); add("SOL"); add("LAB"); add("SIB"); add("DO"); add("RE");}}));
        this.gammesTriees.add(new Gamme("MI", new ArrayList<String>() {{ add("MI"); add("SOLB"); add("LAB"); add("LA"); add("SI"); add("REB"); add("MIB");}}));
        this.gammesTriees.add(new Gamme("FA", new ArrayList<String>() {{ add("FA"); add("SOL"); add("LA"); add("SIB"); add("DO"); add("RE"); add("MI");}}));
        this.gammesTriees.add(new Gamme("SOLB", new ArrayList<String>() {{ add("SOLB"); add("LAB"); add("SIB"); add("DOB"); add("REB"); add("MIB"); add("FA");}}));
        this.gammesTriees.add(new Gamme("SOL", new ArrayList<String>() {{ add("SOL"); add("LA"); add("SI"); add("DO"); add("RE"); add("MI"); add("SOLB");}}));
        this.gammesTriees.add(new Gamme("LAB", new ArrayList<String>() {{ add("LAB"); add("SIB"); add("DO"); add("REB"); add("MIB"); add("FA"); add("SOL");}}));
        this.gammesTriees.add(new Gamme("LA", new ArrayList<String>() {{ add("LA"); add("SI"); add("REB"); add("RE"); add("MI"); add("SOLB"); add("LAB");}}));
        this.gammesTriees.add(new Gamme("SIB", new ArrayList<String>() {{ add("SIB"); add("DO"); add("RE"); add("MIB"); add("FA"); add("SOL"); add("LA");}}));
        this.gammesTriees.add(new Gamme("SI", new ArrayList<String>() {{ add("SI"); add("REB"); add("MIB"); add("MI"); add("SOLB"); add("LAB"); add("SIB");}}));

        nombreDeNotesAjoutees = 0;
    }

    public List<Gamme> ajouterOccurenceDeNote(String note) {
        Log.e("Ajout des occurences", "Pour chaque gamme, on a tant d'occurences de notes :");
        for (int i = 0; i < gammesTriees.size(); i++) {
            if (gammesTriees.get(i).possedeLaNote(note)) {
                gammesTriees.get(i).incrementerScore();
                Log.e(gammesTriees.get(i).nom(), Integer.toString(gammesTriees.get(i).scoreGamme()));
            }
        }
        Log.e("Fin d'ajout", "Et voilà !");

        nombreDeNotesAjoutees++;
        Log.e("On a ajouté en tout :",nombreDeNotesAjoutees + " notes !");

        boolean permutationAEuLieu = true;
        while (permutationAEuLieu) {
            permutationAEuLieu = false;
            for (int i = 0; i < gammesTriees.size(); i++) {
                try{
                    if (gammesTriees.get(i).scoreGamme() < gammesTriees.get(i+1).scoreGamme() && i < gammesTriees.size()-1) {
                        Gamme temp = gammesTriees.get(i);
                        gammesTriees.set(i, gammesTriees.get(i+1));
                        gammesTriees.set(i+1, temp);
                        permutationAEuLieu = true;
                    }
                }
                catch(Throwable t) {}
            }
        }
        Log.e("Gammes triées"," : ");
        for (int i = 0; i < gammesTriees.size(); i++) {
            Log.e(gammesTriees.get(i).nom(), Integer.toString(gammesTriees.get(i).scoreGamme()));
        }
        return gammesTriees;
    }

    public int nombreDeNotesAjoutees() { return nombreDeNotesAjoutees; }
}
