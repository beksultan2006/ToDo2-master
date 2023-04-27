package com.example.todo.ui.fragment.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo.R
import com.example.todo.databinding.PageBoardBinding

class BoardAdapter(private val listener: CLickListener): Adapter<BoardAdapter.BoardViewHolder>() {

    private val listImg =
        arrayListOf(R.drawable.img1, R.drawable.img2, R.drawable.img3)
    private val listTitle =
        arrayListOf("To-do list!", "Share your crazy idea ^_^", "Flexibility")

    private val listDes =
        arrayListOf("Here you can write down something important or make a schedule for tomorrow:)",
            "You can easily share with your report, list or schedule and it's convenient",
            "Your note with you at home, at work, even at the resort")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BoardViewHolder(
        PageBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = listImg.size

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.onBind(position)
        holder.binding.boardBtnStart.setOnClickListener {
            listener.click()
        }
        holder.binding.btnNext.setOnClickListener{
            listener.NextCLicked()
        }
        holder.binding.btnSkip.setOnClickListener{
            listener.SkipClicked()
        }
    }

    inner class BoardViewHolder(val binding: PageBoardBinding) : ViewHolder(binding.root) {
        fun onBind(position: Int) {
            binding.boardImg.setImageResource(listImg[position])
            binding.boardTvTitle.text = listTitle[position]
            binding.boardTvDesc.text = listDes[position]
            //    ? (1)     ==    1 = 2     - 1
            if (position == listImg.size - 1) {
                binding.boardBtnStart.isVisible = true
                binding.btnSkip.isGone = true
                binding.btnNext.isGone = true
            } else {
                binding.boardBtnStart.isGone = true
            }
        }
    }

    interface CLickListener {
        fun click()
        fun NextCLicked()
        fun SkipClicked()
    }
}