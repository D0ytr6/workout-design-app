package com.example.samurairoad.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.samurairoad.databinding.CreateWorkoutFragmentBinding

class CreateWorkoutFragment : Fragment() {

    private var _binding: CreateWorkoutFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var saveWorkoutBtn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateWorkoutFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val createWorkoutViewModel = ViewModelProvider(this).get(CreateWorkoutViewModel::class.java)
        saveWorkoutBtn = binding.saveBtn
        saveWorkoutBtn.setOnClickListener {
            if(!binding.workoutDescription.text.equals("") and !binding.workoutTitle.equals("")){
                createWorkoutViewModel.insertWorkout(requireContext(), binding.workoutTitle.text.toString(), binding.workoutDescription.text.toString())
            }
        }
        return root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}