package com.san.heartratemonitormobile.view.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.san.heartratemonitormobile.databinding.FragmentUserBinding
import com.san.heartratemonitormobile.domain.model.AccountModel

class UserFragment(private val account: AccountModel) : Fragment() {
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserBinding.inflate(layoutInflater)

        return binding.root
    }
}