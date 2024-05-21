package com.example.notebook.delete_page

import android.app.Activity
import android.app.AlertDialog
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.R
import com.example.notebook.database.DBhandler
import com.example.notebook.databinding.ItemDeleteBinding
import com.example.notebook.database.NoteTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//set data in recyclerview
class AdapterForDelete(private val context: Activity, private var allData: ArrayList<NoteTable>) : RecyclerView.Adapter<AdapterForDelete.RecycleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecycleViewHolder(ItemDeleteBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun getItemCount() = allData.size

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        holder.setData(allData[position])
    }







    inner class RecycleViewHolder(private val binding: ItemDeleteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: NoteTable) {
            binding.txtTitle.text = data.title
            binding.imgDelete.setOnClickListener {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_NoteBook))
                    .setTitle("حذف یادداشت")
                    .setMessage("آیا یادداشت برای همیشه حذف شود؟")
                    .setIcon(R.drawable.baseline_delete_40)
                    .setNegativeButton("بله") { _, _ ->
                        val db = DBhandler.getDatabse(context)
                        CoroutineScope(Dispatchers.IO).launch {
                            db.NoteDao().DeleteUser(NoteTable(data.id, "", "", "", ""))
                        }
                        allData.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        toast("حذف شد")
                    }
                    .setPositiveButton("خیر") { _, _ -> }
                    .create()
                    .show()
            }
            binding.imgRestore.setOnClickListener {
                val db = DBhandler.getDatabse(context)
                CoroutineScope(Dispatchers.IO).launch {
                    db.NoteDao().UpdateDeleteStateToFalse(data.id)
                }
                allData.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                toast("یادداشت بازگردانی شد")
            }
        }
    }

    private fun toast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

}