package com.example.samurairoad.adapters

import android.graphics.Color
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
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
import com.example.samurairoad.databinding.WorkoutItemMaterialBinding
import kotlin.random.Random

// TODO Dependency injection pass context
class ParentWorkoutAdapter(
    private val listener: OnItemWorkoutClickListener) : RecyclerView.Adapter<ParentWorkoutAdapter.WorkoutViewHolder>()  {

//    create view holder class
    class WorkoutViewHolder(
        var binding: WorkoutItemMaterialBinding
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
        val binding = WorkoutItemMaterialBinding.inflate(inflater, parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val currentWorkout = workouts[position]
        with(holder){
            binding.titleTv.text = currentWorkout.title
//            binding.descriptionTv.text = currentWorkout.description

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

            binding.expandWorkoutsIv.setOnClickListener{
                if(currentWorkout.expanded){
//                    currentWorkout.expanded = false
                    listener.onExpandClick(false, workouts[position].title)
                    binding.expandWorkoutsIv.setImageResource(R.drawable.ic_baseline_expand_more_24)
                }
                else{
//                    currentWorkout.expanded = true
                    listener.onExpandClick(true, workouts[position].title)
                    binding.expandWorkoutsIv.setImageResource(R.drawable.ic_baseline_expand_less_24)
                }
                notifyDataSetChanged()

            }
            binding.addWorkout.setOnClickListener{

            }

            // TODO binding.workoutCardView.setCardBackgroundColor(Color.parseColor()) fix deprecated
            //binding.workoutCardView.background.setTint(currentWorkout.color)

            val gradient = GradientDrawable(
                GradientDrawable.Orientation.BL_TR,
                intArrayOf(
                    currentWorkout.color, // startColor
                    Color.parseColor("#23253C"),  // centerColor
                    Color.parseColor("#181818")   // endColor
                )
            )

            val gradientRadius = Random.nextInt(500, 1000).toFloat()
            val gradientCenter = List(2){Random.nextDouble(0.0, 1.0).toFloat()}

            gradient.gradientType = GradientDrawable.RADIAL_GRADIENT
            gradient.gradientRadius = gradientRadius

            gradient.setGradientCenter(gradientCenter[0], gradientCenter[1])

            binding.colorBackground.background = gradient

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