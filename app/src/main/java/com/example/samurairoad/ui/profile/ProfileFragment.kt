package com.example.samurairoad.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.samurairoad.R
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

        binding.weekTv.setOnClickListener {
            updateChartButtons(it, binding.monthTv, binding.allTimeTv)
            lineStyle.styleChart(chart, createChartList(ChartType.WEEK), 0f, 6f)
            setChardData()
        }

        binding.monthTv.setOnClickListener {
            updateChartButtons(it, binding.weekTv, binding.allTimeTv)
            lineStyle.styleChart(chart, createChartList(ChartType.MOTHS), 0f, 30f)
            setChardData()
        }

        binding.allTimeTv.setOnClickListener {
            updateChartButtons(it, binding.monthTv, binding.weekTv)
        }

        binding.activityPopupIv.setOnClickListener{
            val popup = PopupMenu(requireContext(), it)
            popup.inflate(R.menu.profile_chart_menu)
            popup.show()
        }

        chart = binding.chartActivity

        lineStyle = StatisticLineStyle(requireContext())

        lineStyle.styleChart(chart, createChartList(ChartType.WEEK), 0f, 6f)

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
                for (i in 1..30){
                    xLabel.add(i.toString())
                }
            }

            ChartType.ALL_TIME -> {
            // TODO
            }
        }


        return xLabel
    }

    private fun updateChartButtons(checkedView: View, uncheckedView1: View, uncheckedView2: View,){
        checkedView.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_date_button_checked)
        uncheckedView1.background = ContextCompat.getDrawable(requireContext(), R.drawable.bd_date_button_unchecked)
        uncheckedView2.background = ContextCompat.getDrawable(requireContext(), R.drawable.bd_date_button_unchecked)
    }

}