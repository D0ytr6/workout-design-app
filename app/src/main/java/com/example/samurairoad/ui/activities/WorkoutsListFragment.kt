package com.example.samurairoad.ui.activities

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.samurairoad.adapters.Exercise
import com.example.samurairoad.adapters.Workout
import com.example.samurairoad.adapters.ParentWorkoutAdapter
import com.example.samurairoad.databinding.AddWorkoutDialogBinding
import com.example.samurairoad.databinding.FragmentWorkoutsListBinding
import com.example.samurairoad.room.tables.ExerciseTableModel
import com.example.samurairoad.room.tables.WorkoutTableModel


class WorkoutsListFragment : Fragment() {

    private var _binding: FragmentWorkoutsListBinding? = null
    private lateinit var adapter: ParentWorkoutAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: WorkoutsListViewModel

    private var workoutList = listOf<WorkoutTableModel>()
    private var exerciseList = listOf<ExerciseTableModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutsListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(WorkoutsListViewModel::class.java)

        adapter = ParentWorkoutAdapter()

        binding.workoutListRv.layoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.workoutListRv.adapter = adapter

        val dialog = Dialog(requireContext())

        // subscribe on view model data
        viewModel.workouts.observe(viewLifecycleOwner){
            Log.d("MyTag", "Fragment " + it.size.toString())
            //adapter.workouts = it
            workoutList = it
            updateRecyclerList()
        }

        viewModel.exercises.observe(viewLifecycleOwner){
            exerciseList = it
            updateRecyclerList()
        }

        binding.fabBtnCreateWorkout.setOnClickListener {
            var dialogBinding = AddWorkoutDialogBinding.inflate(inflater)

            dialogBinding.CancelButton.setOnClickListener {
                dialog.dismiss()
                Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
            }

            dialogBinding.SaveButton.setOnClickListener {
                if(!dialogBinding.nameEt.text.equals("") and !dialogBinding.descriptionEt.equals("")){
                    viewModel.insertWorkout(requireContext(), dialogBinding.nameEt.text.toString(), dialogBinding.descriptionEt.text.toString())
                    dialog.dismiss()
                }
            }

            dialog.setContentView(dialogBinding.root)

            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            dialog.setCancelable(true)
            dialog.show()

        }
        return binding.root
    }

    private fun updateRecyclerList(){
        val list = roomDataToRecycler()
        adapter.workouts = list
    }

    private fun roomDataToRecycler(): List<Workout>{

        var listExercise = mutableListOf<Exercise>()
        var listWorkout = mutableListOf<Workout>()

        val i: Int = 0

        for(exercise in exerciseList){
            i.inc()
            listExercise.add(Exercise(exercise.Title, i))
        }

        for (workout in workoutList){
            listWorkout.add(Workout(workout.Title, workout.Description, listExercise))
        }

        return listWorkout

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWorkouts(requireContext())
        viewModel.getExercises(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorkoutsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}