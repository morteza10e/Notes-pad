package com.example.notebook.note_opened_page

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.example.notebook.databinding.NoteOpenedBinding
import com.example.notebook.database.DBhandler
import com.example.notebook.database.NoteTable
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteOpenedActivity : AppCompatActivity() {
    private lateinit var binding: NoteOpenedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NoteOpenedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //  آیا کاربر داره یادداشت جدید ایحاد میکنه یا یادداشت قبلی رو ویرایش میکنه؟
        val fromWhere = intent.getStringExtra("FromWhere")
        val id = intent.getIntExtra("notesId", 0)


        val db = DBhandler.getDatabse(this)


        when (fromWhere) {
            "fromHome" -> {
//                binding.txtDate.text = getDate()
            }

            "fromRoot" -> {
                var t: String? = null
                var d: String? = null
                CoroutineScope(Dispatchers.IO).launch {
                    var returned = db.NoteDao().getNoteWithID(id)
                    launch(Dispatchers.Main) {
                        val edit = Editable.Factory()
                        binding.title.text = edit.newEditable(returned.title)
                        binding.detail.text = edit.newEditable(returned.description)
//                        binding.txtDate.text = returned.date
                    }
                }
            }
        }


        binding.fabSave.setOnClickListener {
            val title = binding.title.text.toString()
            val detail = binding.detail.text.toString()
            if (title.isNotEmpty()) {
                when (fromWhere) {
                    "fromHome" -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            db.NoteDao().insertNote(NoteTable(0, title, detail, getPersianDate(), "false"))
                        }
                        toast("یادداشت ذخیره شد")
                        finish()
                    }
                    "fromRoot" -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            db.NoteDao().UpdateNote(NoteTable(id, title, detail, getPersianDate(), "false"))
                        }
                        toast("یادداشت ذخیره شد")
                        finish()
                    }
                }
            } else
                toast("عنوان نمیتواند خالی باشد")
        }
    }

    private fun getPersianDate(): String {
        val p = PersianDate()
        val currentDate = "${p.year}/${p.month}/${p.day}"
        val currentTime = "${p.hour}:${p.min}"
        return "$currentDate | $currentTime"
    }


    private fun snack(s: String) {
        val snack = Snackbar.make(binding.root, s, Snackbar.LENGTH_SHORT)
        snack.setBackgroundTint(Color.DKGRAY)
        ViewCompat.setLayoutDirection(snack.view, ViewCompat.LAYOUT_DIRECTION_LTR)
        snack.show()
    }


    private fun toast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }


}