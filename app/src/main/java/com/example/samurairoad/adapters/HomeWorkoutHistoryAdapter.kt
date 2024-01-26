package com.example.samurairoad.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samurairoad.adapters.models.HistoryItem
import com.example.samurairoad.adapters.models.Workout
import com.example.samurairoad.databinding.TodayTrainingItemBinding
import com.example.samurairoad.databinding.WorkoutItemBinding

class HomeWorkoutHistoryAdapter : RecyclerView.Adapter<HomeWorkoutHistoryAdapter.WorkoutItem>() {

    var workouts = emptyList<HistoryItem>()
        set(newValue){
            field = newValue
//             update visible list
            notifyDataSetChanged()
        }

    class WorkoutItem(val binding: TodayTrainingItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutItem {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = TodayTrainingItemBinding.inflate(inflater, parent, false)
        return WorkoutItem(binding)
    }

    override fun onBindViewHolder(holder: WorkoutItem, position: Int) {
        val currentWorkout = workouts[position]
        with(holder){
            with(binding){
                workoutTypeLogo.setImageResource(currentWorkout.workoutTypeImg)
                workoutTitle.text = currentWorkout.title
                workoutDescription.text = currentWorkout.description
                startWorkout.text = currentWorkout.startTime
                endWorkout.text = currentWorkout.endTime
            }
        }
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

}
