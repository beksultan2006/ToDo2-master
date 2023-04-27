package com.example.todo.ui.fragment.note

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.todo.data.model.NoteModel
import com.example.todo.databinding.ItemNoteBinding
import com.example.todo.ui.App
import com.example.todo.ui.utils.loadImage


class NoteAdapter : Adapter<NoteAdapter.NoteViewHolder>() {
    private var list : ArrayList<NoteModel> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<NoteModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int)  {
        App.db.getDao().deleteNote(list.removeAt(position))
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
        ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(list[position])
    }


    class NoteViewHolder(private val binding: ItemNoteBinding) : ViewHolder(binding.root) {

        fun onBind(model: NoteModel) {
            model.image?.let { binding.itemImg.loadImage(it) }
            binding.itemTvTitle.text = model.title
            binding.itemTvDes.text = model.description
            binding.itemTvDate.text = model.date


        }
    }
}


