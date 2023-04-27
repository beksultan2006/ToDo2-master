package com.example.todo.ui.fragment.addnote

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todo.base.BaseFragment
import com.example.todo.data.model.NoteModel
import com.example.todo.databinding.FragmentAddNoteBinding
import com.example.todo.ui.App
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
private const val PICK_IMAGE_REQUEST = 1
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(FragmentAddNoteBinding::inflate) {

    private var imageUrl = ""

    override fun setupUI() {

}

    val date = getCurrentDateTime()
    val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    override fun setupObserver() {
        pickImage()
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val des = binding.etDes.text.toString()
            App.db.getDao().addNote(
                NoteModel(
                    image = imageUrl,
                    title = title,
                    description = des,
                    date = dateInString
                )
            )
            findNavController().navigateUp()
        }
    }

    private fun pickImage(){
        binding.addImg.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type= "image/"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageUrl = data?.data.toString()
        if (requestCode == PICK_IMAGE_REQUEST && requestCode == RESULT_OK && data?.data != null)
            try {
               val bitmap =
                   MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, data.data)
                binding.addImg.setImageBitmap(bitmap)
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
            }catch (e: IOException){
                e.printStackTrace()
            }
    }
}