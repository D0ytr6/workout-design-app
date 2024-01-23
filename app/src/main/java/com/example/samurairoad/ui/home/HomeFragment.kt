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
import com.example.samurairoad.adapters.MainViewPagerFragment1
import com.example.samurairoad.adapters.MainViewPagerFragment2
import com.example.samurairoad.adapters.ViewPagerAdapter
import com.example.samurairoad.adapters.extensions.ViewPagerExtensions.addCarouselEffect
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

        val viewPagerFraments = listOf<Fragment>(MainViewPagerFragment1(), MainViewPagerFragment2())
        val adapter = ViewPagerAdapter(viewPagerFraments, requireActivity()) // TODO add fragment manager
        binding.viewPager.adapter = adapter
        binding.viewPager.addCarouselEffect(enableZoom = true)


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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}