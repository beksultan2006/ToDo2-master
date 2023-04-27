package com.example.todo.ui.fragment.board

import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentOnBoardBinding
import com.example.todo.ui.App

class OnBoardFragment : BaseFragment<FragmentOnBoardBinding>(FragmentOnBoardBinding::inflate),
    BoardAdapter.CLickListener {
    private lateinit var adapter: BoardAdapter

    override fun setupUI() {
        adapter = BoardAdapter(this)
        binding.boardPager.adapter = adapter
        binding.dotsIndicator.setViewPager2(binding.boardPager)
    }

    override fun click() {
        App.prefs.saveBoardState()
        findNavController().navigate(R.id.noteFragment)
    }

    override fun NextCLicked() {
        binding.boardPager.setCurrentItem(++binding.boardPager.currentItem, true)
    }

    override fun SkipClicked() {
        binding.boardPager.setCurrentItem(binding.boardPager.adapter?.itemCount ?: 0, true)
    }
}