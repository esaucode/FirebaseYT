package com.esaudev.firebaseyt.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esaudev.firebaseyt.databinding.ItemNotesBinding
import com.esaudev.firebaseyt.domain.model.Note

class NoteListAdapter: ListAdapter<Note, NoteListAdapter.NoteViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    inner class NoteViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = currentList[position]

        holder.binding.tvNote.text = note.note
        holder.binding.tvAutor.text = note.author

        holder.itemView.apply {
            setOnClickListener {
                onNoteClickListener?.let { click ->
                    click(note)
                }
            }
        }
    }

    protected var onNoteClickListener : ((Note) -> Unit)? = null

    fun setNoteClickListener(listener: (Note) -> Unit){
        onNoteClickListener = listener
    }
}