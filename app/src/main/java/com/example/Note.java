package com.example;

public class Note {
    int note;
    double frequence;

    public Note(int note, double frequence){
        this.note = note;
        this.frequence = frequence;
    }

    public Note() {
        this.note = 0;
        this.frequence = 0;
    }

    public String toString() {
        switch (note) {
            case 1:
                return "DO";
            case 2:
                return "REB";
            case 3:
                return "RE";
            case 4:
                return "MIB";
            case 5:
                return "MI";
            case 6:
                return "FA";
            case 7:
                return "SOLB";
            case 8:
                return "SOL";
            case 9:
                return "LAB";
            case 10:
                return "LA";
            case 11:
                return "SIB";
            case 12:
                return "SI";
            default:
                return "##";
        }
    }

    public void setNote(int note) {
        this.note = note;
    }
    public int getNote() {
        return note;
    }

    public void setFrequence(float frequence) {
        this.frequence = frequence;
    }
    public double getFrequence() {
        return frequence;
    }

}
