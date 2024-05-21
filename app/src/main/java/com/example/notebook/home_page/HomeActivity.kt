package com.example.notebook.home_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.note_opened_page.NoteOpenedActivity
import com.example.notebook.databinding.HomeBinding
import com.example.notebook.database.DBhandler
import com.example.notebook.database.NoteTable
import com.example.notebook.delete_page.DeleteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: HomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //set some settings in recyclerview
        binding.RecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        //add some note
        val db = DBhandler.getDatabse(this)
        CoroutineScope(Dispatchers.IO).launch {
            db.NoteDao().insertNote(NoteTable(0, "sss", "sss", "ss", "false"))
            db.NoteDao().insertNote(NoteTable(0, "sss", "sss", "ss", "false"))
            db.NoteDao().insertNote(NoteTable(0, "sss", "sss", "ss", "false"))
            db.NoteDao().insertNote(NoteTable(0, "sss", "sss", "ss", "false"))
        }


        fillRecyclerView()
        binding.add.setOnClickListener {
            val intent = Intent(this, NoteOpenedActivity::class.java)
            intent.putExtra("FromWhere", "fromHome")
            startActivity(intent)
        }
        binding.RecycleBin.setOnClickListener {
            startActivity(Intent(this, DeleteActivity::class.java))
        }
    }









    //fill update RecyclerView
    private fun fillRecyclerView() {
        val db = DBhandler.getDatabse(this)
        CoroutineScope(Dispatchers.IO).launch {
            var returned = db.NoteDao().getAllNotesFALSE()
            launch(Dispatchers.Main) {
                returned.collect {
                    binding.RecyclerView.adapter = AdapterForHome(this@HomeActivity, it as ArrayList<NoteTable>)
                }
            }
        }
    }


    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }


    // محظ احتیاط لیست رو بروز میکنه
    override fun onStart() {
        super.onStart()
        fillRecyclerView()
    }


}