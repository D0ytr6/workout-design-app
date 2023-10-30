package com.example.samurairoad.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samurairoad.databinding.WorkoutItemBinding
import com.example.samurairoad.model.WorkoutState
import com.example.samurairoad.room.tables.WorkoutTableModel

class WorkoutAdapter : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>()  {

//    create view holder
    class WorkoutViewHolder(
        var binding: WorkoutItemBinding
    ) : RecyclerView.ViewHolder(binding.root){}

    var workouts = emptyList<WorkoutTableModel>()
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
        var current_workout = workouts[position]
        with(holder){
            binding.titleTv.text = current_workout.Title
            binding.descriptionTv.text = current_workout.Description
        }

    }

    override fun getItemCount(): Int {
        return workouts.size
    }
}