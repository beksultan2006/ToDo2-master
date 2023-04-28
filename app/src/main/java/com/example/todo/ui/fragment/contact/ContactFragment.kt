package com.example.todo.ui.fragment.contact

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.R
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentContactBinding
import com.example.todo.ui.fragment.contact.data.model.ContactModel

class ContactFragment : BaseFragment<FragmentContactBinding>(FragmentContactBinding::inflate) {

    private val adapter by lazy { ContactAdapter() }

    override fun setupUI() {
        binding.rvContact.adapter = adapter
    }

    private fun grtContact() {
        val list = listOf<ContactModel>()

        val contentResolver = requireActivity().contentResolver
        val cursor = contentResolver.query()
        ContactsContract.Contacts.CONTENT_URI,

    }


}