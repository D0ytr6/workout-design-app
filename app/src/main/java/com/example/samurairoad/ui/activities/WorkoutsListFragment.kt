package com.example.samurairoad.ui.activities

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.view.children
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

    private var workoutsShowList = listOf<Workout>()

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

        viewModel.workoutAdapterList.observe(viewLifecycleOwner){
            workoutsShowList = it
            adapter.workouts = it
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

        binding.materialSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                Toast.makeText(requireContext(), "Turn ON", Toast.LENGTH_SHORT).show()
                adapter.isFullList = true
            }
            else{
                Toast.makeText(requireContext(), "Turn OFF", Toast.LENGTH_SHORT).show()
                adapter.isFullList = false
            }

        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllWorkouts(requireContext())
        viewModel.getAllExercises(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorkoutsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}