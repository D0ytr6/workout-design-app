package com.example.samurairoad.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samurairoad.adapters.models.FavouriteWorkoutModel
import com.example.samurairoad.databinding.FullWorkoutExerciseItemBinding
import com.example.samurairoad.databinding.HomeFavouriteWorkoutItemBinding

class FavouritesWorkoutAdapter : RecyclerView.Adapter<FavouritesWorkoutAdapter.FavouriteWorkoutViewHolder>(){

    class FavouriteWorkoutViewHolder(val binding: HomeFavouriteWorkoutItemBinding): RecyclerView.ViewHolder(binding.root)

    var workouts = mutableListOf<FavouriteWorkoutModel>()
        set(newValue){
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteWorkoutViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = HomeFavouriteWorkoutItemBinding.inflate(inflater, parent, false)
        return FavouriteWorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteWorkoutViewHolder, position: Int) {
        with(holder){

        }
    }

    override fun getItemCount(): Int {
        return workouts.size
    }
}