package com.example.samurairoad.ui.activities

import android.content.Context.*
import android.os.Bundle
import android.os.Parcel
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.samurairoad.R
import com.example.samurairoad.adapters.ParentWorkoutAdapter
import com.example.samurairoad.adapters.models.Workout
import com.example.samurairoad.databinding.FragmentWorkoutsListBinding
import com.example.samurairoad.dialogs.CreateWorkoutDialog
import com.example.samurairoad.repository.WorkoutRepository


class WorkoutsListFragment : Fragment(), ParentWorkoutAdapter.OnItemWorkoutClickListener{

    private var _binding: FragmentWorkoutsListBinding? = null
    private lateinit var adapter: ParentWorkoutAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: WorkoutsListViewModel
    private var workoutList = emptyList<Workout>()

    // callback for dialog
    private val listener = object : CreateWorkoutDialog.DialogClickListener{

        override fun onSaveClickListener(title: String, description: String, color: Int) {
            viewModel.insertWorkout(requireContext(), title, description, color)
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(p0: Parcel, p1: Int) {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(WorkoutRepository.LifecycleTag, "onCreateView")

        _binding = FragmentWorkoutsListBinding.inflate(inflater, container, false)

        //TODO most modern delegate by view models
        viewModel = ViewModelProvider(this).get(WorkoutsListViewModel::class.java)

        adapter = ParentWorkoutAdapter(this)

        binding.workoutListRv.layoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.workoutListRv.adapter = adapter

        binding.searchBar.isActivated = true;
        binding.searchBar.queryHint = "Type your keyword here";
        binding.searchBar.onActionViewExpanded();
        binding.searchBar.isIconified = false;
        binding.searchBar.clearFocus();

        viewModel.workoutAdapterList.observe(viewLifecycleOwner){
            Log.d(WorkoutRepository.MyTag, "Fragment get data from observer")
            adapter.workouts = it
            workoutList = it
        }

        binding.fabBtnCreateWorkout.setOnClickListener {
            binding.searchBar.setQuery("", false);
            binding.root.requestFocus();
            openCreateWorkoutDialog()
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

        //TODO fix hide soft keyboard
//        binding.root.setOnTouchListener(object: View.OnTouchListener{
//            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
//                binding.searchBar.setQuery("", false);
//                binding.root.requestFocus();
//                hideKeyboard()
//                return true
//            }
//
//        })

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(WorkoutRepository.LifecycleTag, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllWorkouts(requireContext())

        binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var tempList = mutableListOf<Workout>()

                for(workout in workoutList){
                    if(workout.title.lowercase().contains(p0!!)){
                        tempList.add(workout)
                    }
                }

                adapter.workouts = tempList
                return true

            }

        })

        //viewModel.getAllExercises(requireContext())
    }

    // TODO replace by delegate
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorkoutsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(WorkoutRepository.LifecycleTag, "onDestroy")
    }

    override fun onStart() {
        Log.d(WorkoutRepository.LifecycleTag, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(WorkoutRepository.LifecycleTag, "onResume")
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.d(WorkoutRepository.LifecycleTag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(WorkoutRepository.LifecycleTag, "onStop")
    }

    private fun hideKeyboard() {
        val view: View? = requireActivity().currentFocus;
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun openCreateWorkoutDialog(){
        val bundle = bundleOf("clickListener" to listener)
        findNavController().navigate(R.id.action_workoutsListFragment_to_createWorkoutDialog, bundle)
    }

    private fun openFullWorkout(id: Long){
        val bundle = bundleOf("workoutId" to id)
        findNavController().navigate(R.id.action_workoutsListFragment_to_fullWorkoutFragment, bundle)
    }

    override fun onClick(workoutId: Long) {
        openFullWorkout(workoutId)
    }

}