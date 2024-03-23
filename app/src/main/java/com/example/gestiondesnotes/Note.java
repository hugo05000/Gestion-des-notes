package com.example.gestiondesnotes;

/**
 * Classe représentant une note avec un nom et un détail.
 * @author Hugo MARCEAU
 * @author Florent DIABI
 */
public class Note {
    /**
     * Le nom de la note.
     */
    String nom;
    /**
     * Le détail de la note.
     */
    String detail;

    /**
     * Constructeur de la classe Note.
     * @param nom Le nom de la note.
     * @param detail Le détail de la note.
     */
    public Note(String nom, String detail) {
        this.nom = nom;
        this.detail = detail;
    }
}
