package com.example.practiceingeneral.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.practiceingeneral.R

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteHolder>(DIFF_CALLBACK) {
    private var onItemClickListener:OnItemClickListener?= null
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.title == newItem.title
                        && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
            }

        }
    }

    fun getNoteAt(position: Int) = getItem(position)

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle : TextView
        val textViewDescription : TextView
        val textViewPriority : TextView
        init {
            textViewTitle = itemView.findViewById(R.id.text_view_title)
            textViewDescription = itemView.findViewById(R.id.text_view_description)
            textViewPriority = itemView.findViewById(R.id.text_view_priority)


            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    onItemClickListener?.onItemClick(getItem(position))
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)

        return NoteHolder(itemView)
    }


    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = getItem(position)
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description
        holder.textViewPriority.text = currentNote.priority.toString()
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener (onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}