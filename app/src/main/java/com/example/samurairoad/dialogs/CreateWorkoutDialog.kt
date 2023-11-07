package com.example.samurairoad.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.samurairoad.R
import com.example.samurairoad.databinding.AddWorkoutDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateWorkoutDialog: BottomSheetDialogFragment() {

    private lateinit var _binding: AddWorkoutDialogBinding
    private val binding get() = _binding
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
//        TODO replace bundle by safe args
        val listener = arguments?.getParcelable<DialogClickListener>("clickListener")

        binding.CancelButton.setOnClickListener {
            findNavController().popBackStack()
            Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
        }

        binding.SaveButton.setOnClickListener {
            if(!binding.nameEt.text.equals("") && !binding.descriptionEt.equals("") && selectedColor != null){
                listener?.onSaveClickListener(binding.nameEt.text.toString(), binding.descriptionEt.text.toString(),
                    selectedColor!!)
                findNavController().popBackStack()
            }
        }

        //TODO add custom color button

        binding.chooseColor.blueFl.setOnClickListener {
            binding.chooseColor.emptyBox.setImageResource(R.drawable.color_checkbox_blue)
            selectedColor = R.color.blue
        }

        binding.chooseColor.redFl.setOnClickListener {
            binding.chooseColor.emptyBox.setImageResource(R.drawable.color_checkbox_red)
            binding.chooseColor.emptyBox
            selectedColor = R.color.red
        }

        binding.chooseColor.greenFl.setOnClickListener {
            binding.chooseColor.emptyBox.setImageResource(R.drawable.color_checkbox_green)
            selectedColor = R.color.green
        }

        binding.chooseColor.yellowFl.setOnClickListener {
            binding.chooseColor.emptyBox.setImageResource(R.drawable.color_checkbox_yellow)
            selectedColor = R.color.yellow
        }

        binding.chooseColor.purpleFl.setOnClickListener {
            binding.chooseColor.emptyBox.setImageResource(R.drawable.color_checkbox_purple)
            selectedColor = R.color.purple_200
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    interface DialogClickListener : Parcelable{

        fun onSaveClickListener(title: String, description: String, color: Int)

    }

}