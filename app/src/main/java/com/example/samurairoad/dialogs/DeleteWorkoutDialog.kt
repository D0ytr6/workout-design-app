package com.example.samurairoad.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.samurairoad.databinding.AddWorkoutDialogBinding
import com.example.samurairoad.databinding.DeleteDialogBinding

class DeleteWorkoutDialog: DialogFragment(){

    private lateinit var _binding: DeleteDialogBinding
    private val binding get() = _binding

    private var currentId: Long? = null

    //TODO create layout view
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//
//        currentId = arguments?.getLong("id")
//        val listener = arguments?.getParcelable<OnDeleteClickListener>("deleteListener")
//
//        var builder = AlertDialog.Builder(requireContext())
//            .setMessage("Delete selected Workout?")
//            .setNegativeButton("Cancel"){ _, id ->
//                findNavController().popBackStack()
//            }
//            .setPositiveButton("Delete"){dialog, id ->
//                if(currentId != null){
//                    if (listener != null) {
//                        listener.onDeleteClick(currentId!!)
//                        findNavController().popBackStack()
//                    }
//                }
//            }
//        return builder.create()
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        currentId = arguments?.getLong("id")
//        TODO fix deprecated
        val listener = arguments?.getParcelable<OnDeleteClickListener>("deleteListener")

        _binding = DeleteDialogBinding.inflate(inflater, container, false)

        binding.CancelButton.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.SaveButton.setOnClickListener{
            if(currentId != null){
                if (listener != null) {
                    listener.onDeleteClick(currentId!!, binding.checkbox.isChecked)
                    findNavController().popBackStack()
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
    }

    interface OnDeleteClickListener: Parcelable{
        fun onDeleteClick(id: Long, cascade: Boolean)
    }
}