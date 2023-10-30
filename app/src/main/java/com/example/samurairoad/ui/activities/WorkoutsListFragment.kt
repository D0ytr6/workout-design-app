package com.example.samurairoad.ui.activities

import android.R
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
import com.example.samurairoad.adapters.WorkoutAdapter
import com.example.samurairoad.databinding.AddWorkoutDialogBinding
import com.example.samurairoad.databinding.FragmentWorkoutsListBinding


class WorkoutsListFragment : Fragment() {

    private var _binding: FragmentWorkoutsListBinding? = null
    private lateinit var adapter: WorkoutAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: WorkoutsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutsListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(WorkoutsListViewModel::class.java)

        adapter = WorkoutAdapter()

        binding.workoutListRv.layoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.workoutListRv.adapter = adapter

        val dialog = Dialog(requireContext())

        // subscribe on view model data
        viewModel.workouts.observe(viewLifecycleOwner){
            Log.d("MyTag", "Fragment " + it.size.toString())
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWorkouts(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorkoutsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}