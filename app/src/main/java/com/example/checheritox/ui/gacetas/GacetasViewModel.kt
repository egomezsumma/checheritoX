package com.example.checheritox.ui.gacetas

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.checheritox.ui.ChecheritoViewModel
import com.example.checheritox.utils.printLongWithFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.nodes.Document
import org.jsoup.Jsoup
import org.jsoup.select.Elements

import java.util.Calendar

class GacetasViewModel(actOrFrag:GacetasFragment) : ChecheritoViewModel<GacetasFragment>(actOrFrag) {

    private val _gacetasLinks = MutableLiveData<List<GacetasFragment.GacetaLink>>()
    val gacetasLinks : LiveData<List<GacetasFragment.GacetaLink>>
        get() = _gacetasLinks


    val gacetasUrlsByNo : HashMap<String, String> = hashMapOf()


    fun getGacetasFromText(text:String) {
        uiScope.launch {
            val result = withContext(Dispatchers.IO) {
                tryDownloadSuspend(text)
            }
            val newList = arrayListOf<GacetasFragment.GacetaLink>()
            gacetasUrlsByNo.forEach { nro, url ->
                newList.add(GacetasFragment.GacetaLink(nro, url))
            }
            _gacetasLinks.value = newList
        }
    }


    private fun tryDownloadSuspend(textToAppend:String): String {
        // Paara poder pegarle a un dominito tiene q estar en network_security_config.xml
        try {
            gacetasUrlsByNo.clear()

            val url = "https://www.google.com/search?q=panama+gaceta+$textToAppend"
            // Cargo la busqueda de google del sku con la palabra coto
            val doc: Document = Jsoup.connect(url).get()

            // Hasta el momento en el resultado viene un <span> ARS $121.45</span>
            val links = doc.select("a[href*=gacetaoficial]");

            var gacetaNum = 1
            links.forEach { link ->
                val href = link.attr("href")
                val lala = link.text()
                if(href.contains("gacetaoficial.gob.pa/pdfTemp/")){
                    val urlPdf = href;
                    val splited = href.split("/")
                    val i = splited.indexOf("pdfTemp");
                    val gacetaNoAndApendix = splited.get(i+1)
                    var strNo = gacetaNoAndApendix
                    if(strNo.contains("_")){
                        strNo = strNo.split("_")[0]
                    }
                    if(strNo.isDigitsOnly()){
                        gacetaNum = strNo.toInt()
                    }
                    gacetasUrlsByNo.put(gacetaNoAndApendix, urlPdf)
                }
                val lolo = 1+1
            }

            return gacetasUrlsByNo.keys.toString()
        } catch (e: Exception) {
            e.printStackTrace();
            return ""
        }

    }

    fun clearList() {
        _gacetasLinks.value = arrayListOf()
    }

}