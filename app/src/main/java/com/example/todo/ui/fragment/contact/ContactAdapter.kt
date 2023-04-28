package com.example.todo.ui.fragment.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo.databinding.ItemContactBinding
import com.example.todo.ui.fragment.contact.data.model.ContactModel

class ContactAdapter : Adapter<ContactAdapter.ContactViewHolger>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolger {
        ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    }

    override fun onBindViewHolder(holder: ContactViewHolger, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

  inner  class ContactViewHolger(private val binding: ItemContactBinding) :
      ViewHolder(binding.root) {
      fun onBind(model: ContactModel){
          binding.itemContactName.text = model.name
          binding.itemContactName.text = model.phone
      }
    }
}