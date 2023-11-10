package com.example.samurairoad.ui.activities

import android.content.ClipDescription
import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samurairoad.R
import com.example.samurairoad.adapters.FullExercisesAdapter
import com.example.samurairoad.adapters.ParentWorkoutAdapter
import com.example.samurairoad.databinding.FragmentActivitiesBinding
import com.example.samurairoad.databinding.FragmentFullWorkoutBinding
import com.example.samurairoad.repository.WorkoutRepository
import kotlinx.coroutines.launch

class FullWorkoutFragment: Fragment() {

    private val viewModel: WorkoutsListViewModel by viewModels()

    private var _binding: FragmentFullWorkoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: FullExercisesAdapter

    private var workoutID: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullWorkoutBinding.inflate(inflater, container, false)

        workoutID = arguments?.getLong("workoutId")

        loadWorkout(requireContext(), workoutID!!)

        viewModel.loadExercises(inflater.context, workoutID!!)

        adapter = FullExercisesAdapter()

        binding.exercisesRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        binding.exercisesRv.adapter = adapter

        viewModel.exercisesFullAdapterList.observe(viewLifecycleOwner){
            adapter.list = it
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadWorkout(context: Context, id: Long){
        lifecycleScope.launch {
            val workout = viewModel.getWorkout(context, id).await()
            updateUI(workout.Title, workout.Description)
        }
    }

    private fun updateUI(title: String, description: String){
        with(binding){
            titleTv.text = title
            descriptionTv.text = description
        }
    }
}