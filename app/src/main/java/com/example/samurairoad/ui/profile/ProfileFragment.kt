package com.example.samurairoad.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.samurairoad.databinding.FragmentProfileBinding
import com.example.samurairoad.ui.auth.TokenViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val tokenViewModel: TokenViewModel by activityViewModels()

    private lateinit var chart: LineChart

    lateinit var lineStyle: StatisticLineStyle

    enum class ChartType {
        WEEK, MOTHS, ALL_TIME
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

//        TODO how logout happened blyat
        binding.logoutBtn.setOnClickListener{
            tokenViewModel.deleteRefreshToken()
        }

        chart = binding.chartActivity

        lineStyle = StatisticLineStyle(requireContext(), createChartList(ChartType.WEEK))

        lineStyle.styleChart(chart)

        setChardData()

        chart.extraBottomOffset = 5f

        chart.invalidate()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setChardData(){

        // create entries
        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 5f))
        entries.add(Entry(1f, 2f))
        entries.add(Entry(2f, 3f))
        entries.add(Entry(3f, 0f))
        entries.add(Entry(4f, 10f))
        entries.add(Entry(5f, 1f))
        entries.add(Entry(6f, 1f))

    // На основании массива точек создадим первую линию с названием
        val dataset = LineDataSet(entries, "Numbers")
        dataset.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineStyle.styleLineDataSet(dataset)

    // Создадим переменную данных для графика
        val data = LineData(dataset)

    // Передадим данные для графика в сам график
        chart.setData(data)

    // Не забудем отправить команду на перерисовку кадра, иначе график не отобразится
        chart.invalidate()

    }

    private fun createChartList(chartType: ChartType): List<String> {

        val xLabel = mutableListOf<String>()

        when(chartType){
            ChartType.WEEK -> {
                xLabel.add("MON")
                xLabel.add("TUE")
                xLabel.add("WED")
                xLabel.add("Thur")
                xLabel.add("Fri")
                xLabel.add("Sat")
                xLabel.add("Sun")
            }

            ChartType.MOTHS -> {
            // TODO
            }

            ChartType.ALL_TIME -> {
            // TODO
            }
        }


        return xLabel
    }

}