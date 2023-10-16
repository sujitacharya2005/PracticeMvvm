package com.example.practiceingeneral

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceingeneral.room.Note
import com.example.practiceingeneral.room.NoteAdapter
import com.example.practiceingeneral.room.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel:NoteViewModel
    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.button_add_note)
        floatingActionButton.setOnClickListener{
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

        val recyclerView:RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)


        val adapter = NoteAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = adapter


        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        noteViewModel.getAllNotes().observe(this) {
           // Toast.makeText(this, "onChanged", Toast.LENGTH_SHORT).show()
            adapter.setNotes(it as ArrayList<Note>)
        }

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recyclerView)
        adapter.setOnItemClickListener(object: NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.description)
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.priority)
                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE) ?: ""
            val description = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION) ?: ""
            val priority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0) ?: 0

            val note = Note(title = title, description = description, priority = priority)
            noteViewModel.insert(note)

            Toast.makeText(this, "Note saved", Toast.LENGTH_LONG).show()
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            val id = data?.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1) ?: -1
            if(id == -1) {
                Toast.makeText(this, "Note can't be update", Toast.LENGTH_SHORT).show()
                return
            }
            val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE) ?: ""
            val description = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION) ?: ""
            val priority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0) ?: 0
            val note = Note(title = title , description = description, priority = priority)
            note.id = id
            noteViewModel.update(note)
            Toast.makeText(this, "Note updated", Toast.LENGTH_LONG).show()
        }else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
