package com.exeapp.checheritox.models.articulo

import com.exeapp.checheritox.models.titulo.Titulo
import com.exeapp.checheritox.utils.slugify

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

class ArticuloResult(val articulo:Articulo, val query:String) {
    override fun toString(): String {
        return "ArtQuery query:$query $articulo"
    }

    fun getFormatedHtmText(): String {
        if(query.length <=0){
            return articulo.texto
        }
        val querySlug = slugify(query)
        val artSlug = slugify(articulo.texto)
        val x = artSlug.split(querySlug)
        if(x.size>0){
            val wordsLeft = x[0].split("-").size
            val wordsQuery = querySlug.split("-").size

            val palabras = articulo.texto.split(" ")
            var prefix = ""
            var center = ""
            var rest = ""
            for(i in 0 until palabras.size-1){
                val palabra = palabras[i]
                rest = palabras.subList(i+1, palabras.size).joinToString(" ")
                if(slugify(rest).contains(querySlug)){
                    prefix = prefix + " " + palabra
                }
                else{
                    center = center + " " + palabra
                    var centerSlug = slugify(center)
                    if (centerSlug.contains(querySlug)){
                        break
                    }
                }
            }

            var result =  "$prefix<b><font color='#EE0000'>$center</font></b> $rest"

            return result
        }

        /*
        val lct = articulo.texto.split(query);

        if(lct.size>1) {
            var str = lct[0]
            for (index in 1 until lct.size) {
                str = str + "<b><font color='#EE0000'>$query</font></b>" + lct[index]
            }
            return str
        }*/

        return articulo.texto

    }
}
