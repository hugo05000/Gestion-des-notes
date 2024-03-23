package com.example.gestiondesnotes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Activité principale de l'application.
 * @author Hugo MARCEAU
 * @author Florent DIABI
 */
class MainActivity : AppCompatActivity(), INoteListener {
    private lateinit var recyclerView : RecyclerView
    private var notes = ArrayList<Note>()

    /**
     * Méthode appelée lors de la création de l'activité.
     * Elle initialise l'interface utilisateur, charge les notes existantes et met en place les événements des boutons.
     */
    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerNoteId)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadNote()
        val noteAdapter = NoteAdapter(notes, this)

        recyclerView.adapter = noteAdapter

        val addNoteButton = findViewById<FloatingActionButton>(R.id.AddNoteId)

        addNoteButton.setOnClickListener {

            val dialogView = LayoutInflater.from(this).inflate(R.layout.add_note_layout,null)

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setView(dialogView)

            val alertDialog = alertDialogBuilder.create()

            alertDialog.show()
            alertDialog?.window?.setBackgroundDrawable(getDrawable(R.drawable.bord_arrondis))

            val confirmButton = dialogView.findViewById<Button>(R.id.butonAjouterId)
            val cancelButton = dialogView.findViewById<Button>(R.id.butonAnnulerId)

            cancelButton.setOnClickListener {
                alertDialog.dismiss()
            }

            confirmButton.setOnClickListener {
                val nom = alertDialog.findViewById<TextView>(R.id.noteNameId)?.text.toString()
                val detail = alertDialog.findViewById<TextView>(R.id.noteDescriptionId)?.text.toString()
                noteAdapter.addNote(Note(nom,detail));
                saveNote()

                noteAdapter.notifyDataSetChanged()
                alertDialog.dismiss()
            }

            val zoneTextNomNote = alertDialog.findViewById<TextView>(R.id.noteNameId)
            val zoneTextDetailNote = alertDialog.findViewById<TextView>(R.id.noteDescriptionId)

            confirmButton.isEnabled = false

            // On vérifie si les zones de texte ne sont pas vide pour activer le bouton "Ajouter"
            zoneTextNomNote?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Rien à mettre ici
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val nouveauTexte = s.toString()

                        if (nouveauTexte?.length != 0 && zoneTextDetailNote?.text.toString().length != 0) {
                            confirmButton.isEnabled = true
                        } else {
                            confirmButton.isEnabled = false
                        }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Rien à mettre ici
                }
            })

            zoneTextDetailNote?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Rien à mettre ici
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val nouveauTexte = s.toString()

                    if (nouveauTexte?.length != 0 && zoneTextNomNote?.text.toString().length != 0) {
                        confirmButton.isEnabled = true
                    } else {
                        confirmButton.isEnabled = false
                    }

                }

                override fun afterTextChanged(s: Editable?) {
                    // Rien à mettre ici
                }
            })

        }

        val detailEdit = intent.getStringExtra("detailEdit")
        val titreEdit = intent.getStringExtra("titreEdit")
        val indice = intent.getIntExtra("indice",-1)

        if(indice != - 1 ){
            notes[indice].detail = detailEdit
            notes[indice].nom = titreEdit
            saveNote()
        }
    }

    /**
     * Sauvegarde la liste des notes.
     */
    override fun saveNote() {
        val sharedPreferences = getSharedPreferences("gestiondesnotes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(notes)
        editor.putString("notes", json)
        editor.apply()
    }

    /**
     * Charge la liste des notes
     */
    private fun loadNote() {
        val sharedPreferences = getSharedPreferences("gestiondesnotes", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("notes", null)
        val type = object : TypeToken<ArrayList<Note>>() {}.type
        if (json != null) {
            notes = gson.fromJson(json, type)
        }
    }

    /**
     * Méthode appelée lorsque l'activité est mise en arrière-plan.
     * Sauvegarde les notes avant que lors de la fermeture de l'application.
     */
    override fun onStop() {
        super.onStop()
        saveNote()
    }

    /**
     * Écouteur pour le clic sur une note.
     * Ouvre le détail d'une note.
     */
    override fun onNoteClick(laNote: Note, indice: Int) {
        val intent = Intent(this, NoteDetailsActivity::class.java)
        intent.putExtra("titreNote", laNote.nom)
        intent.putExtra("detailNote", laNote.detail)
        intent.putExtra("indice",indice)
        startActivity(intent)
    }
}