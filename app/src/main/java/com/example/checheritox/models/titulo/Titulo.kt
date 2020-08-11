package com.example.checheritox.models.titulo

import com.example.checheritox.models.articulo.Articulo

class Titulo(val texto : String) {
    var numero : String = "?"
    var subtitulo : String = "--"
    val articulos : ArrayList<Articulo> = arrayListOf()

    companion object {
        fun fromText(text:String) : Titulo{
            val articulosTxt = text.split("Artículo ")
            val res = Titulo(text)
            res.numero = text.split("\\n".toRegex())[0].trim()
            res.subtitulo = articulosTxt[0].trim()
            for (index in 1 until articulosTxt.size) {
                res.articulos.add(Articulo.fromText(articulosTxt[index]))
            }
            return res
        }
    }

    override fun toString(): String {
        return "Titulo#$numero (articulos:${articulos.size}) $subtitulo"
    }

}
