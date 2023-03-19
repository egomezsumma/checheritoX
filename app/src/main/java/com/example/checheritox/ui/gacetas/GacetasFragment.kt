package com.example.checheritox.ui.gacetas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.checheritox.R
import com.example.checheritox.utils.printLongWithFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit

class GacetasFragment : Fragment() {

    private lateinit var slideshowViewModel: GacetasViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProviders.of(this).get(GacetasViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gacetas, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val calendarView = root.findViewById<CalendarView>(R.id.calendarView);
        calendarView
            .setOnDateChangeListener(
                OnDateChangeListener { view, year, month, dayOfMonth ->
                    // In this Listener we are getting values
                    // such as year, month and day of month
                    // on below line we are creating a variable
                    // in which we are adding all the variables in it.
                    val Date = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)

                    val date = Calendar.getInstance()
                    date.set(year, month, dayOfMonth)
                    onDateSelected(date)
                    // set this date in TextView for Display
                    Toast.makeText(context, Date, Toast.LENGTH_LONG).show()
                })
        calendarView.maxDate = System.currentTimeMillis();

        return root
    }

    fun getUrl(gacetaNo:String, yyyyMMddDateStr:String, appendix:String = "" ) : String
    {
        val gacetaAnApendixMayus = gacetaNo + if(appendix.isNotEmpty()) "_${appendix.toUpperCase()}" else ""
        var urlTemplate = "https://www.gacetaoficial.gob.pa/pdfTemp/$gacetaAnApendixMayus/GacetaNo_${gacetaNo + appendix.toLowerCase()}_${yyyyMMddDateStr}.pdf"
        return urlTemplate
    }
    fun onDateSelected(date: Calendar) {
        val dayRef = Calendar.getInstance()
        dayRef.set(2023, 2, 8)
        val refGacetaNo = 29735

        /*val monDayRef = Calendar.getInstance()
        monDayRef.set(2023, 3, 8)
        val monRefGacetaNo = ""*/

        var sign = 1
        var diffMs = date.timeInMillis - dayRef.timeInMillis;
        var diffDays = TimeUnit.DAYS.convert(diffMs, TimeUnit.MILLISECONDS)
        if(date.timeInMillis < dayRef.timeInMillis) {
            sign = -1;
            diffMs = dayRef.timeInMillis-date.timeInMillis;
            diffDays = TimeUnit.DAYS.convert(diffMs, TimeUnit.MILLISECONDS)
        }

        val gacetaNo = refGacetaNo + (sign * diffDays) -  ( sign * ( (diffDays/7)*2 ) );
        val url1 = getUrl(gacetaNo.toString(), printLongWithFormat(date.timeInMillis), "")
        val url2 = getUrl(gacetaNo.toString(), printLongWithFormat(date.timeInMillis), "a")
        Log.d("ALALa", url1)
        Log.d("ALALa", url2)

    }
}