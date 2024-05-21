package com.example.notebook.delete_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.databinding.DeleteBinding
import com.example.notebook.database.DBhandler
import com.example.notebook.database.NoteTable
import com.example.notebook.home_page.AdapterForHome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeleteActivity : AppCompatActivity() {
    private lateinit var binding: DeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set some settings in recyclerview
        binding.RecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        fillRecyclerView()
    }


    private fun fillRecyclerView() {
        val db = DBhandler.getDatabse(this)
        CoroutineScope(Dispatchers.IO).launch {
            val returned = db.NoteDao().getAllNotesTRUE()
            launch (Dispatchers.Main) {
                returned.collect {
                        binding.RecyclerView.adapter = AdapterForDelete(this@DeleteActivity, it as ArrayList<NoteTable>)
                }
            }
        }
    }

}