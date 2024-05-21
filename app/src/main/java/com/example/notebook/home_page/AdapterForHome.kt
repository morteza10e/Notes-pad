package com.example.notebook.home_page

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.R
import com.example.notebook.database.DBhandler
import com.example.notebook.note_opened_page.NoteOpenedActivity
import com.example.notebook.databinding.ItemHomeBinding
import com.example.notebook.database.NoteDAO
import com.example.notebook.database.NoteTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//set data in recyclerview
class AdapterForHome(private val context: Context, private var allData: ArrayList<NoteTable>
) : RecyclerView.Adapter<AdapterForHome.NotesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NotesViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(context), parent, false))


    override fun getItemCount() = allData.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.setData(allData[position])
    }


    inner class NotesViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: NoteTable) {
            binding.txtTitle.text = data.title
            binding.imgDelete.setOnClickListener {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_NoteBook))
                    .setTitle("انتقال به سطل زباله")
                    .setMessage("آیا یادداشت به سطل زباله منتقل شود؟")
                    .setIcon(R.drawable.baseline_delete_40)
                    .setNegativeButton("بله") { _, _ ->
                        val db = DBhandler.getDatabse(context)
                        CoroutineScope(Dispatchers.IO).launch {
                            db.NoteDao().UpdateDeleteStateToTrue(data.id)
                        }
                        allData.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        toast("یادداشت به سطل زباله منتقل شد")
                    }
                    .setPositiveButton("خیر") { _, _ -> }
                    .create()
                    .show()
            }
            binding.root.setOnClickListener {
                val i = Intent(context, NoteOpenedActivity::class.java)
                i.putExtra("FromWhere", "fromRoot")
                i.putExtra("notesId", data.id)
                context.startActivity(i)
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}