package com.example.practiceingeneral

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.practiceingeneral.databinding.ActivityAddNoteBinding

class AddEditNoteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddNoteBinding
    companion object {
        const val EXTRA_ID = "com.example.practiceingeneral.EXTRA_ID"
        const val EXTRA_TITLE = "com.example.practiceingeneral.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.example.practiceingeneral.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY= "com.example.practiceingeneral.EXTRA_PRIORITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        binding.numberPickerPriority.minValue = 1
        binding.numberPickerPriority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        if(intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            binding.editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            binding.editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            binding.numberPickerPriority.value = intent.getIntExtra(EXTRA_PRIORITY, 0)
        }
        else {
            title = "Add Note"
        }
    }

    private fun saveNote() {
        val title = binding.editTextTitle.text.toString()
        val description = binding.editTextDescription.text.toString()
        val priority = binding.numberPickerPriority.value
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, description)
        data.putExtra(EXTRA_PRIORITY, priority)

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(RESULT_OK, data)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}