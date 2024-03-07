package com.example.samurairoad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.samurairoad.R
import com.example.samurairoad.databinding.FragmentHomeBinding
import com.example.samurairoad.databinding.WorkoutItemMaterialBinding

class TestFragment : Fragment() {

    private var _binding: WorkoutItemMaterialBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = WorkoutItemMaterialBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }
}