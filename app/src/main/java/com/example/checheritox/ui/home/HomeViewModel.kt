package com.example.checheritox.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.checheritox.models.articulo.Articulo
import com.example.checheritox.models.constitucion.ConstitucionPanama

class HomeViewModel() : ViewModel() {

    private var constitucion: ConstitucionPanama? = null
    // El historial de listenings
    private val _articulos = MutableLiveData<ArrayList<Articulo>>()
    val articulos: LiveData<ArrayList<Articulo>>
        get() = _articulos

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    fun setConstitucion(constitucionPanama: ConstitucionPanama){
        this.constitucion = constitucionPanama;
        _articulos.value = this.constitucion!!.getArticulos()
    }
}