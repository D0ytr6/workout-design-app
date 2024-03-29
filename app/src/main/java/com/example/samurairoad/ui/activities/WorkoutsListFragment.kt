package com.example.samurairoad.ui.activities

import android.content.Context
import android.content.Context.*
import android.os.Bundle
import android.os.Parcel
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.samurairoad.R
import com.example.samurairoad.adapters.ParentWorkoutAdapter
import com.example.samurairoad.adapters.models.Workout
import com.example.samurairoad.databinding.FragmentWorkoutsListBinding
import com.example.samurairoad.dialogs.CreateWorkoutDialog
import com.example.samurairoad.dialogs.DeleteWorkoutDialog
import com.example.samurairoad.repository.WorkoutRepository
import com.example.samurairoad.room.tables.WorkoutTableModel
import kotlinx.coroutines.launch


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
            viewModel.insertWorkout(requireContext(), title, description, color, expanded = true)
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(p0: Parcel, p1: Int) {
        }

    }

    private val deleteListener = object : DeleteWorkoutDialog.OnDeleteClickListener{

        override fun onDeleteClick(id: Long, cascade: Boolean) {
            viewModel.deleteWorkout(requireContext(), id, cascade)
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

    override fun onDeleteClick(workoutId: Long) {
        val bundle = bundleOf("deleteListener" to deleteListener, "id" to workoutId)
        findNavController().navigate(R.id.action_workoutsListFragment_to_deleteWorkoutDialog, bundle)
    }

    override fun onEditClick(editText: EditText, textView: TextView) {

        editText.setOnEditorActionListener{ _: TextView, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                editText.visibility = View.INVISIBLE
                val title = textView.text.toString()

                textView.text = editText.text.toString()
                textView.visibility = View.VISIBLE

                lifecycleScope.launch {
                    val workout = viewModel.returnWorkoutByName(requireContext(), title).await()
                    workout.Title = textView.text.toString()
                    viewModel.updateWorkout(requireContext(), workout)
                }

                val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
                return@setOnEditorActionListener true
            }
            else {
                Toast.makeText(requireContext(), "False", Toast.LENGTH_SHORT).show()
                false
            }
        }

        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onExpandClick(isExpand: Boolean, title: String) {
        lifecycleScope.launch {
            val workout = viewModel.returnWorkoutByName(requireContext(), title).await()
            workout.Expand = isExpand
            viewModel.updateWorkout(requireContext(), workout)
        }
    }

}