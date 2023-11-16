package com.example.samurairoad.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samurairoad.R
import com.example.samurairoad.adapters.ColorPaletteAdapter
import com.example.samurairoad.adapters.models.PaletteCircle
import com.example.samurairoad.databinding.AddWorkoutDialogBinding
import com.example.samurairoad.ui.activities.WorkoutsListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class CreateWorkoutDialog: BottomSheetDialogFragment(){

    private lateinit var _binding: AddWorkoutDialogBinding
    private val binding get() = _binding

    private val viewModel: WorkoutsListViewModel by viewModels()

    private var selectedColor: Int? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddWorkoutDialogBinding.inflate(inflater, container, false)

        val adapter = ColorPaletteAdapter(object : ColorPaletteAdapter.OnColorClickListener{
            override fun onColorClick(color: Int) {
                selectedColor = color
            }
        })

        val list = createPalettes(binding.root.context)
        adapter.paletteList = list

        binding.paletteRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.paletteRecycler.adapter = adapter


//        TODO replace bundle by safe args
        val listener = arguments?.getParcelable<DialogClickListener>("clickListener")

        binding.CancelButton.setOnClickListener {
            findNavController().popBackStack()
            Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
        }

        binding.SaveButton.setOnClickListener {
            if(!binding.nameEt.text.equals("") && !binding.descriptionEt.equals("") && selectedColor != null){
                lifecycleScope.launch {
                    val workout = viewModel.returnWorkoutByName(requireContext(), binding.nameEt.text.toString()).await()
                    if (workout == null){
                        listener?.onSaveClickListener(binding.nameEt.text.toString(), binding.descriptionEt.text.toString(),
                            selectedColor!!)
                        findNavController().popBackStack()
                    }
                    else{
                        binding.nameEt.setError("Workout is already exist")
                    }
                }
            }
        }

        //TODO add custom color button
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    interface DialogClickListener : Parcelable{

        fun onSaveClickListener(title: String, description: String, color: Int)

    }

    private fun createPalettes(context: Context): List<PaletteCircle>{
        var paletteList = mutableListOf<PaletteCircle>()
        val colors = context.resources.getIntArray(R.array.palette_colors)

        for ((index, color) in colors.withIndex()){
            paletteList.add(PaletteCircle(color, false))
        }

        Log.d("Colors", paletteList.toString())

        return paletteList
    }

    private fun searchExistsWorkout(name: String){

    }

}