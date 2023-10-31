package com.example.samurairoad.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samurairoad.databinding.WorkoutItemBinding

class ParentWorkoutAdapter : RecyclerView.Adapter<ParentWorkoutAdapter.WorkoutViewHolder>()  {

//    create view holder class
    class WorkoutViewHolder(
        var binding: WorkoutItemBinding
    ) : RecyclerView.ViewHolder(binding.root){}

    // workouts list for showing
    var workouts = emptyList<Workout>()
        set(newValue){
            field = newValue
            newValue.forEach {Log.d("MyTag", "Adapter $it")  }
//             update visible list
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = WorkoutItemBinding.inflate(inflater, parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val currentWorkout = workouts[position]
        with(holder){
            binding.titleTv.text = currentWorkout.title
            binding.descriptionTv.text = currentWorkout.description

            // setup child recycler
            val childAdapter = ChildExerciseAdapter()
            childAdapter.exercises = currentWorkout.exercises_list
            // TODO how i get here a context???
            binding.childRecycler.layoutManager = LinearLayoutManager(binding.childRecycler.context, LinearLayoutManager.VERTICAL, false)
            binding.childRecycler.adapter = childAdapter
        }

    }

    override fun getItemCount(): Int {
        return workouts.size
    }
}