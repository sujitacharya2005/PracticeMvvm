package com.example.practiceingeneral

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceingeneral.car.Car
import com.example.practiceingeneral.car.CarComponent
import com.example.practiceingeneral.car.DaggerCarComponent
import com.example.practiceingeneral.car.DieselEngineModule
import com.example.practiceingeneral.databinding.ActivityMainBinding
import com.example.practiceingeneral.room.Note
import com.example.practiceingeneral.room.NoteAdapter
import com.example.practiceingeneral.room.NoteViewModel
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var car:Car

    private lateinit var noteViewModel:NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }

    private fun openAddNoteActivityForResult() {
        val intent = Intent(this, AddEditNoteActivity::class.java)
        startForAddNoteResult.launch(intent)
    }
    private fun openEditNoteActivityForResult(note:Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
        intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
        intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.description)
        intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.priority)
        startForEditNoteResult.launch(intent)
    }

    private val startForAddNoteResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE) ?: ""
            val description = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION) ?: ""
            val priority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0) ?: 0

            val note = Note(title = title, description = description, priority = priority)
            noteViewModel.insert(note)

            Toast.makeText(this, "Note saved", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_LONG).show()
        }
    }


    private val startForEditNoteResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data

                val id = data?.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1) ?: -1
                if(id == -1) {
                    Toast.makeText(this, "Note can't be update", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                }
                val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE) ?: ""
                val description = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION) ?: ""
                val priority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0) ?: 0
                val note = Note(title = title , description = description, priority = priority)
                note.id = id
                noteViewModel.update(note)
                Toast.makeText(this, "Note updated", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.buttonAddNote.setOnClickListener{
            openAddNoteActivityForResult();
        }

        with(binding.recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            noteAdapter = NoteAdapter()
            adapter = noteAdapter
        }


        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        noteViewModel.getAllNotes().observe(this) {
           // Toast.makeText(this, "onChanged", Toast.LENGTH_SHORT).show()
            noteAdapter.submitList(it as ArrayList<Note>)
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
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(binding.recyclerView)
        noteAdapter.setOnItemClickListener(object: NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                openEditNoteActivityForResult(note)
            }

        })
        daggerCheck()
    }

    fun daggerCheck() {

//        val component = DaggerCarComponent.create()
//        car = component.getCar()
//        car.drive()

        val component: CarComponent = DaggerCarComponent.builder()
            .horsePower(100)
            .engineCapacity(20)
            .build()
        //field injection
        component.inject(this@MainActivity)
        car.drive()
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
