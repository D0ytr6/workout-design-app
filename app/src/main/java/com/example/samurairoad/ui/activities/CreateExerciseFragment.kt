package com.example.samurairoad.ui.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.samurairoad.R
import com.example.samurairoad.databinding.CreateExerciseFragmentBinding
import com.example.samurairoad.repository.WorkoutRepository
import com.example.samurairoad.room.tables.WorkoutTableModel

class CreateExerciseFragment : Fragment() {

    private var _binding: CreateExerciseFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var saveWorkoutBtn: Button
    private lateinit var imgPickBtn: Button
    private lateinit var ImgTestPick: ImageView
    private lateinit var selectedImgBitmap: Bitmap

    private var workoutsList = listOf<WorkoutTableModel>()

    private lateinit var viewModel:WorkoutsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateExerciseFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this).get(WorkoutsListViewModel::class.java)

        saveWorkoutBtn = binding.saveBtn
        imgPickBtn = binding.pickImgBtn
        ImgTestPick = binding.testImgPick

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

        saveWorkoutBtn.setOnClickListener {
            if(binding.nameEt.text.toString() != "" && binding.descriptionEt.text.toString() !="" && binding.autoCompleteTextView.text
                    .toString() != "" && binding.setsTv.text.toString() !="" && binding.repsTv.text.toString() != "" && binding.weightTv.text.toString() != ""){
                Toast.makeText(requireContext(), "full information", Toast.LENGTH_SHORT).show()
                val name = binding.nameEt.text.toString()
                val description = binding.descriptionEt.text.toString()
                val sets = binding.setsInputEt.text.toString().toInt()
                val reps = binding.repsInputEt.text.toString().toInt()
                val weight = binding.weightInputEt.text.toString().toInt()
                val workoutName = binding.autoCompleteTextView.text.toString()
                viewModel.insertExercise(requireContext(), name, description, sets, reps, weight, selectedImgBitmap, workoutName)

            }
            else {
                Toast.makeText(requireContext(), "Not full information", Toast.LENGTH_SHORT).show()
            }
        }

        imgPickBtn.setOnClickListener{
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllWorkouts(requireContext())
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

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                if (imgUri != null){
                    Log.d(WorkoutRepository.MyTag, imgUri.toString())
                    val imgBitmap = WorkoutRepository.uriToBitmap(requireContext(), imgUri)
                    if (imgBitmap != null) {
                        selectedImgBitmap = imgBitmap
                        ImgTestPick.setImageURI(imgUri)
                    }

                }

            }
        }

}