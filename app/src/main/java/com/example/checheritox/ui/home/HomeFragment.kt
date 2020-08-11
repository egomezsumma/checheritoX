package com.example.checheritox.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.checheritox.R
import com.example.checheritox.databinding.FragmentHomeBinding
import com.example.checheritox.models.constitucion.ConstitucionPanama
import com.example.checheritox.models.titulo.Titulo

class HomeFragment : Fragment() {

    private var constitucion: ConstitucionPanama? = null
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding : FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)

        binding.vm = homeViewModel
        //val root = inflater.inflate(R.layout.fragment_home, container, false)

        val textView: TextView = binding.title//root.findViewById(R.id.title)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        var search : SearchView = binding.search//root.findViewById(R.id.search)
        this.initSearchView(search)

        initFile()

        return binding.root
    }

    private fun initFile() {
        val file_name = "constitucion-panama.txt"
        val constitucionTxt = this.context!!.assets.open(file_name).bufferedReader().use{
            it.readText()
        }

        this.constitucion = ConstitucionPanama.fromText(constitucionTxt)

        Log.d("HF", "${this.constitucion!!.titulos.size}")
    }

    fun initSearchView(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //TODO(buscar en spotify si no hay resultados https://developer.spotify.com/documentation/web-api/reference/search/search/)
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("HistoryActivity", "onQueryTextSubmit: $query")
                // false if the SearchView should perform the default action of showing any suggestions if available,
                // true if the action was handled by the listener.

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("HistoryActivity", "onQueryTextChange: $newText")

                // false if the SearchView should perform the default action of showing any suggestions if available,
                // true if the action was handled by the listener.
                return false
            }
        })
    }


}