package com.example.samurairoad.ui.activities

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.samurairoad.R
import com.example.samurairoad.databinding.CreateWorkoutFragmentBinding
import com.example.samurairoad.databinding.FragmentActivitiesBinding

class ActivitiesFragment : Fragment() {

    private lateinit var viewModel: ActivitiesViewModel

    private var _binding: FragmentActivitiesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivitiesBinding.inflate(inflater, container, false)

        binding.createWorkoutButton.setOnClickListener(View.OnClickListener {
            openCreateWorkout()
        })

        binding.allWorkoutsBtn.setOnClickListener{
            openAllWorkout()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    private fun openCreateWorkout(){
        findNavController().navigate(R.id.action_activitiesFragment_to_createWorkoutFragment)
    }

    private fun openAllWorkout(){
        findNavController().navigate(R.id.action_activitiesFragment_to_workoutsListFragment)
    }

}