package com.example.samurairoad.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.samurairoad.R
import com.example.samurairoad.databinding.CreateExerciseFragmentBinding
import com.example.samurairoad.room.tables.WorkoutTableModel
import java.time.Duration

class CreateExerciseFragment : Fragment() {

    private var _binding: CreateExerciseFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var saveWorkoutBtn: Button

    private var workoutsList = listOf<WorkoutTableModel>()

    private lateinit var viewModel:WorkoutsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateExerciseFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this).get(WorkoutsListViewModel::class.java)

        viewModel.workouts.observe(viewLifecycleOwner){
            Log.d("MyTag", "Fragment" + it.size.toString())
            workoutsList = it

//            set up exposed menu
            val workouts = getWorkoutName()
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, workouts)

//        get auto complete text and set an adapter
            val autoCompleteTextView = binding.autoCompleteTextView
            autoCompleteTextView.setAdapter(arrayAdapter)
        }

        saveWorkoutBtn = binding.saveBtn
        saveWorkoutBtn.setOnClickListener {
            if(binding.nameEt.text.toString() != "" && binding.descriptionEt.text.toString() !="" && binding.autoCompleteTextView.text
                    .toString() != "" && binding.setsTv.text.toString() !="" && binding.repsTv.text.toString() != "" && binding.weightTv.text.toString() != ""){
                //createWorkoutViewModel.insertWorkout(requireContext(), binding.workoutTitle.text.toString(), binding.workoutDescription.text.toString())
                Toast.makeText(requireContext(), "full information", Toast.LENGTH_SHORT).show()
                val name = binding.nameEt.text.toString()
                val description = binding.descriptionEt.text.toString()
                val sets = binding.setsInputEt.text.toString().toInt()
                val reps = binding.repsInputEt.text.toString().toInt()
                val weight = binding.weightInputEt.text.toString().toInt()

                viewModel.insertExercise(requireContext(), name, description, sets, reps, weight)

            }
            else {
                Toast.makeText(requireContext(), "Not full information", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWorkouts(requireContext())
    }

    private fun getWorkoutName(): List<String>{
        val nameList = mutableListOf<String>()

        if(workoutsList.isNotEmpty()){
            for (workout in workoutsList){
                nameList.add(workout.Title)
            }
        }
        return nameList
    }

}