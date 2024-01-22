package com.example.samurairoad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samurairoad.adapters.models.HomeCarouselModel
import com.example.samurairoad.databinding.HomeCarouselItemBinding
import com.example.samurairoad.databinding.WorkoutItemBinding

class HomeCarouselAdapter(
) : RecyclerView.Adapter<HomeCarouselAdapter.HomeCarouselViewHolder>() {

    var carousel_items = emptyList<HomeCarouselModel>()
        set(newValue){
            field = newValue
//             update visible list
            notifyDataSetChanged()
        }

    class HomeCarouselViewHolder(
        val binding: HomeCarouselItemBinding
    ) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCarouselViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = HomeCarouselItemBinding.inflate(inflater, parent, false)
        return HomeCarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeCarouselViewHolder, position: Int) {
        val current_item = carousel_items[position]
        // TODO setup holder


    }

    override fun getItemCount(): Int {
        return carousel_items.size
    }
}