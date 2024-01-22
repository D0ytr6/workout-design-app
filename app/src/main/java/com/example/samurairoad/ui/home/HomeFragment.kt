package com.example.samurairoad.ui.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.samurairoad.R
import com.example.samurairoad.adapters.HomeCarouselAdapter
import com.example.samurairoad.adapters.models.HomeCarouselModel
import com.example.samurairoad.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val carouselItemList = mutableListOf<HomeCarouselModel>()
    private val ItemView = mutableListOf<View>()

    init {
        carouselItemList.add(HomeCarouselModel("Run", "Test"))
        carouselItemList.add(HomeCarouselModel("Run", "Test"))
        carouselItemList.add(HomeCarouselModel("Run", "Test"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        ItemView.add(binding.firstItem)
        ItemView.add(binding.secondItem)
        ItemView.add(binding.thirdItem)

        val adapter = HomeCarouselAdapter()

        adapter.carousel_items = carouselItemList

        binding.carouselRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false )

        binding.carouselRV.addOnScrollListener(object: OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    val position: Int = getCurrentItem()
                    when(position){
                        0 -> setCheckedCarouselItem(binding.firstItem)
                        1 -> setCheckedCarouselItem(binding.secondItem)
                        2 -> setCheckedCarouselItem(binding.thirdItem)
                    }

                }
            }
        })


        binding.carouselRV.adapter = adapter

        binding.firstItem.background = getDrawable(requireContext(), R.drawable.home_carousel_item_checked)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.searchBar.setOnEditorActionListener { _: TextView, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                Toast.makeText(requireContext(), "Welcome to GFG", Toast.LENGTH_SHORT).show()
                return@setOnEditorActionListener true
            } else {
                Toast.makeText(requireContext(), "False", Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    private fun getCurrentItem(): Int {
        // cast to LinearLayoutManager
        return (binding.carouselRV.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCheckedCarouselItem(view: View){
        view.background = getDrawable(requireContext(), R.drawable.home_carousel_item_checked)
        for (v in ItemView){
            if (v != view){
                v.background = getDrawable(requireContext(), R.drawable.home_carousel_item_unchecked)
            }
        }
    }
}