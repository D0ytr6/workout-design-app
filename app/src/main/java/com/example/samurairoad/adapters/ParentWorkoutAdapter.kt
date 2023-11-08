package com.example.samurairoad.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samurairoad.adapters.models.Exercise
import com.example.samurairoad.adapters.models.Workout
import com.example.samurairoad.databinding.WorkoutItemBinding

class ParentWorkoutAdapter : RecyclerView.Adapter<ParentWorkoutAdapter.WorkoutViewHolder>()  {

//    create view holder class
    class WorkoutViewHolder(
        var binding: WorkoutItemBinding
    ) : RecyclerView.ViewHolder(binding.root){}

    // workouts list for showing
    var workouts = emptyList<Workout>()
        set(newValue){
            Log.d("MyTag", "Set new value to adapter")
            field = newValue
            Log.d("MyTag", newValue.toString())
//             update visible list
            notifyDataSetChanged()
        }

    var isFullList = true
        set(newValue) {
            field = newValue
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
            // TODO binding.workoutCardView.setCardBackgroundColor(Color.parseColor()) fix deprecated
            //binding.workoutCardView.background.setTint(currentWorkout.color)
            binding.workoutCardView.setCardBackgroundColor(currentWorkout.color)
        // setup child recycler
            val childAdapter = ChildExerciseAdapter()

            if (isFullList){
                childAdapter.exercises = currentWorkout.exercises_list
                // TODO how i get here a context???
            }
            else{
                childAdapter.exercises = emptyList<Exercise>()
            }
            binding.childRecycler.layoutManager = LinearLayoutManager(binding.childRecycler.context, LinearLayoutManager.VERTICAL, false)
            binding.childRecycler.adapter = childAdapter

        }

    }

    override fun getItemCount(): Int {
        return workouts.size
    }
}