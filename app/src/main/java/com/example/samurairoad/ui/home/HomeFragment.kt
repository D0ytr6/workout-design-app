package com.example.samurairoad.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samurairoad.R
import com.example.samurairoad.adapters.*
import com.example.samurairoad.adapters.extensions.ViewPagerExtensions.addCarouselEffect
import com.example.samurairoad.adapters.models.FavouriteWorkoutModel
import com.example.samurairoad.adapters.models.HistoryItem
import com.example.samurairoad.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val ItemView = mutableListOf<View>()

    private var favouritesWorkout = mutableListOf<FavouriteWorkoutModel>()

    init {
        favouritesWorkout.add(FavouriteWorkoutModel("Running", R.drawable.ic_run))
        favouritesWorkout.add(FavouriteWorkoutModel("Running", R.drawable.ic_run))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val viewPagerFraments = listOf<Fragment>(MainViewPagerFragment1(), MainViewPagerFragment2())
        val adapter = ViewPagerAdapter(viewPagerFraments, requireActivity()) // TODO add fragment manager
        binding.viewPager.adapter = adapter
        binding.viewPager.addCarouselEffect(enableZoom = true)
        initDotsIndicator()

        val historyAdapter = HomeWorkoutHistoryAdapter()
        val listHistoryWorkouts = mutableListOf<HistoryItem>()
        listHistoryWorkouts.add(HistoryItem("Morning jorking", "From the Central Park to the top of mountain", R.drawable.ic_run, "06:30", "07:00"))
        listHistoryWorkouts.add(HistoryItem("Morning jorking", "From the Central Park to the top of mountain", R.drawable.ic_run, "06:30", "07:00"))

        historyAdapter.workouts = listHistoryWorkouts

        binding.todayTrainingRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.todayTrainingRv.isNestedScrollingEnabled = false;

        binding.todayTrainingRv.adapter = historyAdapter

        val favouritesAdapter = FavouritesWorkoutAdapter()
        favouritesAdapter.workouts = favouritesWorkout

        binding.favouritesWorkoutRv.adapter = favouritesAdapter

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initDotsIndicator(){
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
        }.attach()
    }

}