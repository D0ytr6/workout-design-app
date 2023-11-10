package com.example.samurairoad.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samurairoad.R
import com.example.samurairoad.adapters.models.PaletteCircle
import com.example.samurairoad.databinding.ColorPickerItemBinding
import com.example.samurairoad.repository.WorkoutRepository

class ColorPaletteAdapter(private val listener: OnColorClickListener) : RecyclerView.Adapter<ColorPaletteAdapter.PaletteViewHolder>() {

    var paletteList = emptyList<PaletteCircle>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var previousColor: Int? = null

    class PaletteViewHolder(
        val binding: ColorPickerItemBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaletteViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ColorPickerItemBinding.inflate(inflater, parent, false)
        return PaletteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaletteViewHolder, position: Int) {
        with(holder){
            val background = binding.circleIv.background
            background.setTint(paletteList[position].color)
            if(paletteList[position].isChecked){
                binding.isCheckedIv.background = binding.root.context.resources.getDrawable(R.drawable.ic_baseline_check_24)
            }
            else{
                binding.isCheckedIv.background = null
            }
            //binding.isCheckedIv.background = null
            binding.circleFl.setOnClickListener{
                Log.d(WorkoutRepository.MyTag, "palette click")
                if (previousColor != null){
                    paletteList[previousColor!!].isChecked = false
                    paletteList[position].isChecked = true
                    previousColor = holder.adapterPosition
                    notifyDataSetChanged()
                    listener.onColorClick(paletteList[position].color)
                }
                else{
                    paletteList[position].isChecked = true
                    previousColor = holder.adapterPosition
                    notifyDataSetChanged()
                    listener.onColorClick(paletteList[position].color)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return paletteList.size
    }

    interface OnColorClickListener{
        fun onColorClick(color: Int)
    }

}