package com.example.samurairoad.adapters

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samurairoad.R
import com.example.samurairoad.adapters.models.Exercise
import com.example.samurairoad.adapters.models.Workout
import com.example.samurairoad.databinding.WorkoutItemBinding

// TODO Dependency injection pass context
class ParentWorkoutAdapter(
    private val listener: OnItemWorkoutClickListener) : RecyclerView.Adapter<ParentWorkoutAdapter.WorkoutViewHolder>()  {

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

            binding.root.setOnClickListener{
                listener.onClick(workouts[position].workoutID)
            }

            binding.deleteIv.setOnClickListener{
                listener.onDeleteClick(workouts[position].workoutID)
            }

            binding.editIv.setOnClickListener{
                binding.titleTv.visibility = View.INVISIBLE
                binding.changeTitleEt.setText(binding.titleTv.text)
                binding.changeTitleEt.visibility = View.VISIBLE
                binding.changeTitleEt.requestFocus()
                listener.onEditClick(binding.changeTitleEt, binding.titleTv)
            }

            binding.expandIv.setOnClickListener{
                if(currentWorkout.expanded){
//                    currentWorkout.expanded = false
                    listener.onExpandClick(false, workouts[position].title)
                    binding.expandIv.setImageResource(R.drawable.ic_baseline_expand_more_24)
                }
                else{
//                    currentWorkout.expanded = true
                    listener.onExpandClick(true, workouts[position].title)
                    binding.expandIv.setImageResource(R.drawable.ic_baseline_expand_less_24)
                }
                notifyDataSetChanged()

            }

            // TODO binding.workoutCardView.setCardBackgroundColor(Color.parseColor()) fix deprecated
            //binding.workoutCardView.background.setTint(currentWorkout.color)
            binding.workoutCardView.setCardBackgroundColor(currentWorkout.color)
        // setup child recycler
            val childAdapter = ChildExerciseAdapter()

            if (isFullList && currentWorkout.expanded){
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

    interface OnItemWorkoutClickListener{
        fun onClick(workoutId: Long)
        fun onDeleteClick(workoutId: Long)
        fun onEditClick(editText: EditText, textView: TextView)
        fun onExpandClick(isExpand: Boolean, title: String)
    }
}