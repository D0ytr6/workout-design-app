package com.example.samurairoad.ui.profile

import android.R.attr
import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.samurairoad.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import javax.inject.Inject


class StatisticLineStyle (

    private val context: Context,
    private val xLabel: List<String>

) {

    fun styleChart(lineChart: LineChart) = lineChart.apply {

        // remove the axis
        axisRight.isEnabled = false

        axisLeft.apply {
//            isEnabled = false
            //min/max value for this axis
            axisMinimum = 0f
            axisMaximum = 12f

            textSize = 12f
            textColor = Color.WHITE

            setDrawGridLines(false)
            setDrawAxisLine(true)

            valueFormatter = object : ValueFormatter(){

                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    // Перевірка на виходження за межі значень
                    if (value == 0f) {
                        return ""
                    }
                    return value.toInt().toString() + " H"
                }
            }

        }

        xAxis.apply {
//            set range of x axis
            axisMinimum = 0f
            axisMaximum = 6f

            textSize = 12f
            textColor = Color.WHITE

            isGranularityEnabled = true
            granularity = 1f

            setDrawGridLines(false)
            setDrawAxisLine(true)

            position = XAxis.XAxisPosition.BOTTOM

            valueFormatter = object : ValueFormatter(){

                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    // Перевірка на виходження за межі значень
                    if (value >= 0 && value < xLabel.size) {
                        return xLabel[value.toInt()]
                    }
                    return ""
                }
            }

//            valueFormatter = TimeValueFormatter()
//            typeface = ResourcesCompat.getFont(context, R.font.barlow_semi_condensed_regular)
//            textColor = ContextCompat.getColor(context, R.color.black_75)
        }

        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(false)
        setPinchZoom(false)
//
        description = null
        legend.isEnabled = false
    }

    fun styleLineDataSet(lineDataSet: LineDataSet) = lineDataSet.apply {
        color = ContextCompat.getColor(context, R.color.white)
        valueTextColor = ContextCompat.getColor(context, R.color.black)
        setDrawValues(false)
        lineWidth = 1f
        isHighlightEnabled = true
        setDrawHighlightIndicators(false)
        setDrawCircles(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER

        setDrawFilled(true)
        fillDrawable = ContextCompat.getDrawable(context, R.drawable.bg_spark_line)
    }

}