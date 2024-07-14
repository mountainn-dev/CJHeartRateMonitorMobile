package com.san.heartratemonitormobile.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.san.heartratemonitormobile.databinding.FragmentWorkingBinding

class WorkingFragment : Fragment() {
    private lateinit var binding: FragmentWorkingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWorkingBinding.inflate(layoutInflater)

        return binding.root
    }
}