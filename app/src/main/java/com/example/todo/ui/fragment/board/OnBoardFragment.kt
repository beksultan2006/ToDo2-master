package com.example.todo.ui.fragment.board

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.browser.trusted.Token
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentOnBoardBinding
import com.example.todo.ui.App
import com.google.android.gms.auth.api.credentials.IdToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OnBoardFragment : BaseFragment<FragmentOnBoardBinding>(FragmentOnBoardBinding::inflate),
    BoardAdapter.CLickListener {
    private lateinit var adapter: BoardAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSingClient: GoogleSignInClient

    override fun setupUI() {
        adapter = BoardAdapter(this)
        binding.boardPager.adapter = adapter
        TabLayoutMediator(binding.dotsIndicator,binding.boardPager) { tab, pasition ->
            tab.setIcon(R.drawable.selector_tab)}.attach()
        }

    override fun setupObserver() {
        initGoogleSingInClient()
    }

    private fun initGoogleSingInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.defaul_web_client_id))
            .requestEmail()
            .build()

        googleSingClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = Firebase.auth
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) {task ->
                if (task.isSuccessful){
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SING_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException){
                e.localizedMessage?.let { Log.e("ololo", it) }
            }
        }
    }

    private fun singIn(){
        val intent = googleSingClient.signInIntent
        startActivityForResult(intent, RC_SING_IN)
    }


    override fun click() {
       singIn()
        App.prefs.saveBoardState()
    }

    override fun NextCLicked() {
        binding.boardPager.setCurrentItem(++binding.boardPager.currentItem, true)
    }

    override fun SkipClicked() {
        binding.boardPager.setCurrentItem(binding.boardPager.adapter?.itemCount ?: 0, true)
    }

companion object {
    private const val RC_SING_IN = 9001
    }
}