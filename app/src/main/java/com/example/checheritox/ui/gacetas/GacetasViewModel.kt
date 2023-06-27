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

    private val _text = MutableLiveData<String>()
    val text : LiveData<String>
        get() = _text

    private val _gacetasLinks = MutableLiveData<List<GacetasFragment.GacetaLink>>()
    val gacetasLinks : LiveData<List<GacetasFragment.GacetaLink>>
        get() = _gacetasLinks


    val gacetasUrlsByNo : HashMap<String, String> = hashMapOf()
    fun getGacetasFromDate(date:Calendar) {
        uiScope.launch {
            val texto = withContext(Dispatchers.IO) {
                tryDownloadSuspend(date)
            }

            val newList = arrayListOf<GacetasFragment.GacetaLink>()
            gacetasUrlsByNo.forEach { nro, url ->
                newList.add(GacetasFragment.GacetaLink(nro, url))
            }
            _gacetasLinks.value = newList
        }
    }

    private fun tryDownloadSuspend(date:Calendar): String {
        // Paara poder pegarle a un dominito tiene q estar en network_security_config.xml
        try {

            var dayOfMouth = ("0" + date.get(Calendar.DAY_OF_MONTH).toString());
            if(date.get(Calendar.DAY_OF_MONTH) > 9){
                dayOfMouth = date.get(Calendar.DAY_OF_MONTH).toString()
            }
            var month = printLongWithFormat(date.timeInMillis, format = "MMM")
            var year = date.get(Calendar.YEAR).toString();


            val url = "https://www.google.com/search?q=panama+gaceta+${dayOfMouth}+de+${month}+de+${year}"
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

}