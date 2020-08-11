package com.example.checheritox.ui.home.ArticulosListAdapter

import com.example.checheritox.ui.home.HomeViewModel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.checheritox.databinding.ArticuloItemBinding
import com.example.checheritox.databinding.FragmentHomeBinding
import com.example.checheritox.models.articulo.Articulo

class ArticulosListAdapter(
    val mContext: Context,
    val vm: HomeViewModel
) : ListAdapter<Articulo, ArticulosListAdapter.MyViewHolder>(ArticulosListDiffCallback()) {

    class MyViewHolder(val bindings: ArticuloItemBinding, private val context: Context) : RecyclerView.ViewHolder(bindings.root) {
        init {
            //this.itemView.setOnClickListener(this)
        }

        fun bind(listening: Articulo, position: Int) {
            bindItem(listening, position)
            /*if(listening is ListeningDBEntityLastItemMark){
                bindFooter(listening, position)
            }
            else{
                bindItem(listening, position)
            }*/
        }
        fun bindItem(articulo: Articulo, position: Int) {
            // Actualizo el template
            this.bindings.articulo = articulo
            this.bindings.numero.setText("Articulo ${articulo.numero}")
            this.bindings.titulo.setText("TITULO I")
            this.bindings.contenido.setText(articulo.texto)

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

class ArticulosListDiffCallback : DiffUtil.ItemCallback<Articulo>() {
    override fun areItemsTheSame(oldItem: Articulo, newItem: Articulo): Boolean {
        return oldItem.numero == newItem.numero
    }
    override fun areContentsTheSame(oldItem: Articulo, newItem: Articulo): Boolean {
        return oldItem.numero == newItem.numero
    }
}

