package com.example.identifiergamme;

import com.example.scalefinder.detectionnote.ManagerNote;

import java.util.ArrayList;
import java.util.List;

public class ManagerGamme {
    List<Gamme> gammesTriees;

    public ManagerGamme() {
        List<String> gammeDo = new ArrayList<>();
        gammeDo.add("DO");// gammeDo.add("RE"); gammeDo.add("MI"); gammeDo.add("FA"); gammeDo.add("SOL"); gammeDo.add("LA"); gammeDo.add("SI");

        this.gammesTriees.add(new Gamme("DO", gammeDo));/*
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
        */

    }

    public List<Gamme> ajouterOccurenceDeNote(String note) {
        for (int i = 0; i < gammesTriees.size(); i++) {
            if (gammesTriees.get(i).possedeLaNote(note)) {
                gammesTriees.get(i).incrementerScore();
            }
        }

        boolean permutationAEuLieu = true;
        while (permutationAEuLieu) {
            permutationAEuLieu = false;
            for (int i = 0; i < gammesTriees.size(); i++) {
                if (gammesTriees.get(i).scoreGamme() < gammesTriees.get(i+1).scoreGamme()) {
                    Gamme temp = gammesTriees.get(i);
                    gammesTriees.set(i, gammesTriees.get(i+1));
                    gammesTriees.set(i+1, temp);
                    permutationAEuLieu = true;
                }
            }
        }

        return gammesTriees;
    }
}
