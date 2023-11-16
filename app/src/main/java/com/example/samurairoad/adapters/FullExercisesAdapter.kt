package com.example.samurairoad.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samurairoad.adapters.models.FullExercise
import com.example.samurairoad.databinding.FullWorkoutExerciseItemBinding

class FullExercisesAdapter : RecyclerView.Adapter<FullExercisesAdapter.ExerciseViewHolder>() {

    var list = emptyList<FullExercise>()
        set(newValue){
            Log.d("MyTag", "Set new value to adapter")
            field = newValue
            Log.d("MyTag", newValue.toString())
            notifyDataSetChanged()
        }

    class ExerciseViewHolder(
        var binding: FullWorkoutExerciseItemBinding
    ) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = FullWorkoutExerciseItemBinding.inflate(inflater, parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val title = list[position].title
        val description = list[position].description
        val reps = list[position].RepsCount
        val sets = list[position].SetsCount
        val weight = list[position].weightValue
        val img = list[position].exercisePhoto

        val repsStr = "$reps $sets $weight"

        with(holder){
            binding.exerciseTitle.text = title
            binding.repsTv.text = repsStr
            binding.exerciseImg.setImageBitmap(img)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}