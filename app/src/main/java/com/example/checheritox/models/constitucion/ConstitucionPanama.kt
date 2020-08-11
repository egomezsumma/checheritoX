package com.example.checheritox.models.constitucion

import com.example.checheritox.models.articulo.Articulo
import com.example.checheritox.models.titulo.Titulo

class ConstitucionPanama {
    val titulos : ArrayList<Titulo> = arrayListOf()
    var preambulo : String = ""

    companion object {
        fun fromText(text:String) : ConstitucionPanama {
            val res: ConstitucionPanama = ConstitucionPanama()
            val titulostxt = text.split("TITULO")
            var index = 0
            res.preambulo = titulostxt[0].trim()
            // Salteo el primero porque es preliminares
            for (index in 1 until titulostxt.size) {
                res.titulos.add(Titulo.fromText(titulostxt[index]))
            }
            return res
        }
    }

    fun getArticulos(): ArrayList<Articulo> {
        val articulos = arrayListOf<Articulo>()
        for(tit in titulos){
            articulos.addAll(tit.articulos)
        }
        return articulos
    }
}
