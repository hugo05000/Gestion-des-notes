package com.example.gestiondesnotes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Activité pour afficher et éditer les détails d'une note.
 * @author Hugo MARCEAU
 * @author Florent DIABI
 */
class NoteDetailsActivity: AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)

        // Récupération des données de la note à afficher
        val noteTitre = intent.getStringExtra("titreNote")
        val noteDetail = intent.getStringExtra("detailNote")
        val indice = intent.getIntExtra("indice",-1)

        // Initialisation des vues
        val titreTextView = findViewById<EditText>(R.id.titreTextView)
        val detailTextView = findViewById<EditText>(R.id.detailTextView)

        // Affichage des données de la note dans les vues
        titreTextView.setText(noteTitre)
        detailTextView.setText(noteDetail)

        // Bouton de confirmation pour sauvegarder les modifications et retourner à l'activité principale
        val confirmButton = findViewById<Button>(R.id.confirm_button)

        // Clique sur le bouton de confirmation
        confirmButton.setOnClickListener() {
            val intent = Intent(this@NoteDetailsActivity, MainActivity::class.java)
            intent.putExtra("indice",indice)
            intent.putExtra("detailEdit", detailTextView.text.toString())
            intent.putExtra("titreEdit", titreTextView.text.toString())

            startActivity(intent)
        }
    }
}