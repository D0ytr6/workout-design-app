package com.example.samurairoad.ui.activities

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.samurairoad.R
import com.example.samurairoad.adapters.WorkoutAdapter
import com.example.samurairoad.databinding.CreateWorkoutFragmentBinding
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

        viewModel.workouts.observe(viewLifecycleOwner){
            Log.d("MyTag", "Fragment" + it.size.toString())
            adapter.workouts = it
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