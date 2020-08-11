package com.example.checheritox.models.articulo

class Articulo(val texto : String) {
    var numero : String = "?"

    companion object {
        fun fromText(text:String): Articulo {
            val res = Articulo(text)
            res.numero = text.split(" ")[0].trim()
            return res
        }
    }


    override fun toString(): String {
        return "Art#$numero ${texto.substring(0, 20)}"
    }
}
