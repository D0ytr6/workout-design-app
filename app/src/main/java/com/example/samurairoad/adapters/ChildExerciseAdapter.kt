package com.example.samurairoad.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samurairoad.databinding.ExerciseItemBinding


class ChildExerciseAdapter : RecyclerView.Adapter<ChildExerciseAdapter.ExerciseViewHolder>() {

    // workouts list for showing
    var exercises = emptyList<Exercise>()
        set(newValue){
            field = newValue
            newValue.forEach { Log.d("MyTag", "Adapter $it")  }
//             update visible list
            notifyDataSetChanged()
        }

    class ExerciseViewHolder(
        var binding: ExerciseItemBinding
    ) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ExerciseItemBinding.inflate(inflater, parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        var currentExercise = exercises[position]
        with(holder){
            val text = currentExercise.serialNumber.toString() + " " + currentExercise.title
            binding.exerciseNameTv.text = text
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

}