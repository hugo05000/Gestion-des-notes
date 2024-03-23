package com.example.gestiondesnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Adaptateur pour le RecyclerView utilisé pour afficher la liste des notes.
 * @param noteList La liste des notes à afficher.
 * @param listener L'écouteur pour gérer les interactions avec les notes.
 * @author Hugo MARCEAU
 * @author Florent DIABI
 */
class NoteAdapter(private val noteList: ArrayList<Note>, private val listener: INoteListener):RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.nomNoteId)
        val detailView: TextView = itemView.findViewById(R.id.detailNoteId)
        val deleteButton : FloatingActionButton = itemView.findViewById(R.id.deleteNoteId)
    }

    /**
     * Crée une nouvelle instance de ViewHolder lors de la création de la vue de l'élément de la liste.
     * @param parent Le ViewGroup parent dans lequel la vue nouvellement créée sera ajoutée après que le layout ait été chargé.
     * @param viewType Le type de la vue.
     * @return Un nouvel objet ViewHolder qui contient la vue de l'élément de la liste.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item_layout, parent,false)
        return ViewHolder(view)
    }

    /**
     * Lie les données de la note à la vue de l'élément de la liste.
     * @param holder Le ViewHolder à mettre à jour.
     * @param position La position de l'élément dans la liste.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemText = noteList[position]
        holder.textView.text = itemText.nom

        if (itemText.detail.length <= 40) {
            holder.detailView.text = itemText.detail
        } else {
            holder.detailView.text = itemText.detail.substring(0,37) + "..."
        }

        // Suppression d'une note
        holder.deleteButton.setOnClickListener {
            removeNote(position)
            listener.saveNote()
        }

        holder.itemView.setOnClickListener {
            listener.onNoteClick(itemText, position)
        }
    }

    /**
     * Retourne le nombre total d'éléments dans la liste.
     * @return Le nombre total d'éléments dans la liste.
     */
    override fun getItemCount(): Int {
        return noteList.size
    }

    /**
     * Affiche une boîte de dialogue pour ajouter une nouvelle note.
     * @param view La vue à partir de laquelle la boîte de dialogue est affichée.
     */
    private fun showDialog(view : View) {
        val dialogView = LayoutInflater.from(view.context).inflate(R.layout.add_note_layout,null)

        val alertDialogBuilder = AlertDialog.Builder(view.context)
        alertDialogBuilder.setView(dialogView)

        val alertDialog = alertDialogBuilder.create()
    }

    /**
     * Ajoute une nouvelle note à la liste.
     * @param note La note à ajouter.
     */
    fun addNote(note: Note)
    {
        noteList.add(note)
        notifyItemInserted(noteList.size - 1)
    }

    /**
     * Supprime une note de la liste.
     * @param position La position de la note à supprimer.
     */
    fun removeNote(position: Int)
    {
        if (position < noteList.size) {
            noteList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, noteList.size) //
        }
    }
}