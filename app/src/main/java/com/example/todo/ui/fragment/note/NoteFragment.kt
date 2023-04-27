package com.example.todo.ui.fragment.note


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.base.BaseFragment
import com.example.todo.data.model.NoteModel
import com.example.todo.databinding.FragmentNoteBinding
import com.example.todo.ui.App

class NoteFragment : BaseFragment<FragmentNoteBinding>(FragmentNoteBinding::inflate) {
    private val adapter: NoteAdapter by lazy { NoteAdapter() }

    override fun setupUI() {
        binding.rvNote.adapter = adapter

    }

    override fun setupObserver() {
        deleteNote()
        binding.addNoteBtn.setOnClickListener {
            findNavController().navigate(R.id.addNoteFragment)
        }
        adapter.setList(App.db.getDao().getAllNote() as ArrayList<NoteModel>)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteNote() {
        val simpleCallback =
            object : SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Вы точно хотите удалить?")
                        .setNegativeButton("Нет") { _: DialogInterface, _: Int ->
                            adapter.notifyItemChanged(viewHolder.adapterPosition)
                        }
                        .setPositiveButton("Да") { _: DialogInterface, _: Int ->
                            adapter.deleteItem(viewHolder.adapterPosition)
                        }.show()
                }
            }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvNote)
        adapter.notifyDataSetChanged()


    }
}