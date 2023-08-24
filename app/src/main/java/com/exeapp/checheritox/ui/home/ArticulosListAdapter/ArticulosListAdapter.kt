package com.exeapp.checheritox.ui.home.ArticulosListAdapter

import com.exeapp.checheritox.ui.home.HomeViewModel

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.exeapp.checheritox.databinding.ArticuloItemBinding
import com.exeapp.checheritox.models.articulo.ArticuloResult

class ArticulosListAdapter(
    val mContext: Context,
    val vm: HomeViewModel
) : ListAdapter<ArticuloResult, ArticulosListAdapter.MyViewHolder>(ArticulosListDiffCallback()) {

    class MyViewHolder(val bindings: ArticuloItemBinding, private val context: Context) : RecyclerView.ViewHolder(bindings.root) {
        init {
            //this.itemView.setOnClickListener(this)
        }

        fun bind(listening: ArticuloResult, position: Int) {
            bindItem(listening, position)
            /*if(listening is ListeningDBEntityLastItemMark){
                bindFooter(listening, position)
            }
            else{
                bindItem(listening, position)
            }*/
        }
        fun bindItem(articuloResult: ArticuloResult, position: Int) {
            val articulo = articuloResult.articulo
            // Actualizo el template
            this.bindings.articulo = articulo
            this.bindings.numero.setText("Articulo ${articulo.numero}")
            this.bindings.titulo.setText("TITULO ${articulo.titulo.numero}")


            this.bindings.contenido.setText(Html.fromHtml(articuloResult.getFormatedHtmText()))
            /*val lct = articulo.texto.split("constituye");

            if(lct.size>1)
            {
                var str = lct[0]
                for (index in 1 until lct.size) {
                    str = str + "<b><font color='#EE0000'>constituye</font></b>" + lct[index]
                }
                this.bindings.contenido.setText(Html.fromHtml(str))
            }
            else {
                this.bindings.contenido.setText(articulo.texto)
            }*/



            // Esto es por ahora en el historial que muestro canciones si no encuentro listenings
            /*if (listening is FakeListening) {
                this.bindings.percentLabel.setText("")
                this.bindings.whenLabel.setText("")
                return;
            }

            // El render de un listening real
            if(position == 0 && listening.startListening.equals(listening.endListening)){
                this.bindings.percentLabel.setText("-- en curso --")
                this.bindings.whenLabel.setText("")
            }
            else{
                this.bindings.percentLabel.setText("Escuchado ${listening.percentage.toInt()}% sID:${listening.sessionId}")
                this.bindings.whenLabel.setText(listening.getStartListeningStr())
            }*/

        }
        /*fun bindFooter(listening: ListeningDBEntity, position: Int) {
            this.bindings.listening = listening
            this.bindings.searchOnSpotify.visibility = View.VISIBLE
            this.bindings.whenLabel.visibility = View.GONE
            this.bindings.img.visibility = View.GONE
            this.bindings.title.visibility = View.GONE
            this.bindings.percentLabel.visibility = View.GONE
        }*/

    }

    // Este crea el template
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val bindings = ArticuloItemBinding.inflate(layoutInflater, parent, false)
        bindings.mainViewModel = vm
        return MyViewHolder(bindings, mContext)
    }

    // Esta bindea el modelo a el template (delegandolo en el viewHolder
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)

        // El hasNextPage lo setea el vm.loadPage
        /*if(position+1==itemCount){
            vm.endReached()
            Log.d("HistoryListAdapter", "Fin del historial cargado alcanzado posicion:$position len: $itemCount")
        }*/

    }

}

class ArticulosListDiffCallback : DiffUtil.ItemCallback<ArticuloResult>() {
    override fun areItemsTheSame(oldItem: ArticuloResult, newItem: ArticuloResult): Boolean {
        return oldItem.articulo.numero == newItem.articulo.numero && oldItem.query.equals(newItem.query)
    }
    override fun areContentsTheSame(oldItem: ArticuloResult, newItem: ArticuloResult): Boolean {
        return oldItem.articulo.numero == newItem.articulo.numero && oldItem.query.equals(newItem.query)
    }
}

