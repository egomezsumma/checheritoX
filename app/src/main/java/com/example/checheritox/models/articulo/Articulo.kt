package com.example.checheritox.models.articulo

import com.example.checheritox.models.titulo.Titulo

class Articulo(val titulo : Titulo, val texto : String) {
    var numero : String = "?"

    companion object {
        fun fromText(titulo : Titulo, text:String): Articulo {
            val res = Articulo(titulo, text)
            res.numero = text.split(" ")[0].trim()
            return res
        }
    }


    override fun toString(): String {
        return "Art#$numero ${texto.substring(0, 20)}"
    }
}
