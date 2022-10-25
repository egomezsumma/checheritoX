package com.example.checheritox.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.checheritox.models.articulo.Articulo
import com.example.checheritox.models.articulo.ArticuloResult
import com.example.checheritox.models.constitucion.ConstitucionPanama

class HomeViewModel() : ViewModel() {

    private var constitucion: ConstitucionPanama? = null
    // El historial de listenings
    private val _articulos = MutableLiveData<ArrayList<ArticuloResult>>()
    val articulos: LiveData<ArrayList<ArticuloResult>>
        get() = _articulos

    private val _text = MutableLiveData<String>().apply {
        value = "Artículos de la Constitución"
    }
    val text: LiveData<String> = _text


    fun setConstitucion(constitucionPanama: ConstitucionPanama){
        this.constitucion = constitucionPanama;
        _articulos.value = this.constitucion!!.getArticulosFiltered(text="")
    }

    fun filterArticulos(query: String) {
        _articulos.value = this.constitucion!!.getArticulosFiltered(text=query)
    }


}