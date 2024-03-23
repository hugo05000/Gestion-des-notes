package com.example.gestiondesnotes

/**
 * Interface pour gérer les interactions avec les notes.
 * @author Hugo MARCEAU
 * @author Florent DIABI
 */
interface INoteListener {
    fun onNoteClick(laNote : Note, indice : Int)
    fun saveNote()
}