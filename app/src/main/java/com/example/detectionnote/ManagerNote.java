/***
 * @Author:Romain_Salha
 * @Date:16/01/2021
 *
 * ManagerNote est une classe permettant de savoir si une fréquence(double) donné en parametre,
 *
 * 1)Est une note: public boolean estUneNote(double frequence);
 * 2)Savoir la note la plus proche: public int getNoteLaPlusProche(double frequence);
 * 3)Savoir la distance (en Hz) de la note la plus proche: public double getDistanceNoteLaPlusProche(double frequence);
 */
package com.example.detectionnote;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class ManagerNote {

    //différentes notes
    final int DO=1;
    final int REB=2;
    final int RE=3;
    final int MIB=4;
    final int MI=5;
    final int FA=6;
    final int SOLB=7;
    final int SOL=8;
    final int LAB=9;
    final int LA=10;
    final int SIB=11;
    final int SI=12;

    //fréquence des différentes notes
    final double DO_FQ=32.70;
    final double REB_FQ=34.65;
    final double RE_FQ=36.71;
    final double MIB_FQ=38.89;
    final double MI_FQ=41.20;
    final double FA_FQ=43.65;
    final double SOLB_FQ=46.25;
    final double SOL_FQ=49.00;
    final double LAB_FQ=51.91;
    final double LA_FQ=55.00;
    final double SIB_FQ=58.27;
    final double SI_FQ=61.74;


    Map<Integer, Double> notes;
    private double margeErreur = 1.5;

    public ManagerNote(){definirNotes();}
    public ManagerNote(double margeErreur){
        this.margeErreur=margeErreur;
        definirNotes();
    }


    public int getNoteLaPlusProche(double frequence){

        int gammeTemperee=getGammeTemperee(frequence);

        int noteProche=DO;
        double distMin=abs(frequence-DO_FQ*pow(2,gammeTemperee));

        for(int i=REB;i<=SI;i++){
            double distI=abs(frequence-notes.get(i)*pow(2,gammeTemperee));
            if(distMin>distI){
                distMin=distI;
                noteProche=i;
            }
        }
        return noteProche;
    }
    public double getDistanceNoteLaPlusProche(double frequence) {
        int note = getNoteLaPlusProche(frequence);
        int gammeTemperee = getGammeTemperee(frequence);

        return frequence - notes.get(note)*pow(2,gammeTemperee);
    }
    public boolean estUneNote(double frequence){
        if (abs(getDistanceNoteLaPlusProche(frequence))>margeErreur*getGammeTemperee(frequence))return false;
        return true;
    }

    //Lier toutes les notes avec les bonnes fréquence dans laMap margeErreur
    private void definirNotes(){
        notes = new HashMap<Integer,Double>();
        notes.put(DO,DO_FQ);notes.put(REB,REB_FQ);notes.put(RE,RE_FQ);
        notes.put(MIB,MIB_FQ);notes.put(MI,MI_FQ);notes.put(FA,FA_FQ);
        notes.put(SOLB,SOLB_FQ);notes.put(SOL,SOL_FQ);notes.put(LAB,LAB_FQ);
        notes.put(LA,LA_FQ);notes.put(SIB,SIB_FQ);notes.put(SI,SI_FQ);
    }

    private int getGammeTemperee(double frequence){
        int gammeTemperee=1;
        while(frequence > SI_FQ*pow(2,gammeTemperee)+gammeTemperee*margeErreur){
            gammeTemperee++;
        }
        return gammeTemperee;
    }
}
