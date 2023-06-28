package com.example.checheritox.ui.gacetas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.example.checheritox.R
import com.example.checheritox.databinding.FragmentGacetasBinding
import com.example.checheritox.databinding.GacetaLinkItemBinding
import com.example.checheritox.ui.ChecheritoXListAdapter
import com.example.checheritox.ui.RecyclerViewManager
import com.example.checheritox.utils.UIUtils
import com.example.checheritox.utils.printLongWithFormat
import kotlinx.android.synthetic.main.fragment_gacetas.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.net.URLEncoder
import java.util.Calendar

class GacetasFragment : Fragment() {

    private lateinit var binding: FragmentGacetasBinding
    private lateinit var gacetasViewModel: GacetasViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val viewModelFactory = GacetasViewModelFactory(this)
        gacetasViewModel = ViewModelProvider(this, viewModelFactory).get(GacetasViewModel::class.java)

        //val root = inflater.inflate(R.layout.fragment_gacetas, container, false)
        binding = DataBindingUtil.inflate<FragmentGacetasBinding>( inflater, R.layout.fragment_gacetas, container, false)
        val root = binding.root


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

                    // Oculto semejante calendario
                    calendarView.visibility = View.GONE
                    // set this date in TextView for Display
                    Toast.makeText(context, Date, Toast.LENGTH_LONG).show()
                })
        calendarView.maxDate = System.currentTimeMillis();

        // inicializo el boton
        binding.calendarView.visibility = View.GONE
        binding.showCalendarBtn.setOnClickListener {
            val oldVal = binding.calendarView.visibility
            binding.calendarView.visibility = if(View.VISIBLE == oldVal) View.GONE else View.VISIBLE
        }
        UIUtils.setRoundedBorders(binding.showCalendarBtn, activity!!, 23f)

        initRecyclerView(binding.list, listManager, gacetasViewModel.gacetasLinks)
        initSearchView(binding.search)

        return root
    }

    data class GacetaLink(val nrogaceta:String, val urlPdf:String){}

    private val listManager = object : RecyclerViewManager<GacetaLink>() {
        override fun areItemsTheSame(newItem: GacetaLink, oldItem: GacetaLink): Boolean {
            return newItem.nrogaceta.equals(oldItem.nrogaceta)
        }

        override fun areContentsTheSame(newItem: GacetaLink, oldItem: GacetaLink): Boolean {
            return newItem.nrogaceta.equals(oldItem.nrogaceta)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewDataBinding {
            val layoutInflater = LayoutInflater.from(parent.context)
            val _bindings = GacetaLinkItemBinding.inflate(layoutInflater, parent, false)
            return _bindings
        }

        override fun bind(item: GacetaLink, binding: ViewDataBinding, position: Int) {
            if(!(binding is GacetaLinkItemBinding)) {
                return ;
            }

            val _bindings = binding as GacetaLinkItemBinding
            _bindings.numero.text = "Gaceta ${item.nrogaceta}"

            _bindings.numero.setOnClickListener {
                Toast.makeText(context, "Abriendo link a gaceta ${item.nrogaceta}", Toast.LENGTH_LONG).show()
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.urlPdf)
                startActivity(intent)
            }
        }


    }


    fun onDateSelected(date: Calendar) {
        var dayOfMouth = ("0" + date.get(Calendar.DAY_OF_MONTH).toString());
        if (date.get(Calendar.DAY_OF_MONTH) > 9) {
            dayOfMouth = date.get(Calendar.DAY_OF_MONTH).toString()
        }
        var month = printLongWithFormat(date.timeInMillis, format = "MMM")
        var year = date.get(Calendar.YEAR).toString();
        val text = "${dayOfMouth}+de+${month}+de+${year}"

        binding!!.search.setQuery(text.replace("+", " "), false)
        binding!!.search.clearFocus()
        gacetasViewModel.getGacetasFromText(text);
    }

    fun initSearchView(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //TODO(buscar en spotify si no hay resultados https://developer.spotify.com/documentation/web-api/reference/search/search/)
            override fun onQueryTextSubmit(query: String?): Boolean {
                // false if the SearchView should perform the default action of showing any suggestions if available,
                // true if the action was handled by the listener.
                if(query!=null){
                    val texto = URLEncoder.encode("\"$query\"", "UTF-8")
                    gacetasViewModel.getGacetasFromText(texto)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // false if the SearchView should perform the default action of showing any suggestions if available,
                // true if the action was handled by the listener.
                if(newText!=null && newText.isEmpty()){
                    gacetasViewModel.clearList()
                }
                return false
            }
        })
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Recycler View Initialization protocol
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /***
     * CODIGO GENERICO PARA INICALIZAR LISTAS
     * PASAR AL FRAGMENTO COMUN
     */
    fun <T> initRecyclerView(rv: RecyclerView, manager: RecyclerViewManager<T>, list: LiveData<List<T>>) {
        val adapter = ChecheritoXListAdapter<T>(manager)
        list.observe(this, Observer { list ->
            list?.let {
                rv.setAdapter(adapter)
                adapter.submitList(list)
                adapter.notifyDataSetChanged()
                manager.onListSummited(rv);
            }
        })
    }


}


class GacetasViewModelFactory(private val act: GacetasFragment)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GacetasViewModel::class.java)) {
            return GacetasViewModel(act) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
