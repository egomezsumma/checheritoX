package com.exeapp.checheritox.ui

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView



class ChecheritoXListAdapter<T>(
    private val manager: RecyclerViewManager<T>
) : ListAdapter<T, ChecheritoXListAdapter.MyViewHolder<T>>(MusiXItemAdapterDiffCallbac<T>(manager)) {

    class MyViewHolder<T>(val manager: RecyclerViewManager<T>, val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T, position: Int) {
            manager.bind(item, binding, position)
        }
    }

    // Este crea el template
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<T> {
        val binding : ViewDataBinding =  manager.onCreateViewHolder(parent, viewType)
        return MyViewHolder(manager, binding)
    }

    // Esta bindea el modelo a el template (delegandolo en el viewHolder
    override fun onBindViewHolder(holder: MyViewHolder<T>, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }
}

class MusiXItemAdapterDiffCallbac <T>(private val manager: RecyclerViewManager<T>) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return manager.areItemsTheSame(newItem, oldItem)
    }
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return manager.areContentsTheSame(newItem, oldItem)
    }
}

/**
 * Interfaz para encapsular el comportamiento especifico de una RecyclerView generica
 */
abstract class RecyclerViewManager<T> {
    open fun areItemsTheSame(newItem: T, oldItem: T): Boolean = newItem == oldItem
    abstract fun areContentsTheSame(newItem: T, oldItem: T): Boolean
    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewDataBinding
    abstract fun bind(item: T, binding: ViewDataBinding, position: Int)
    open fun onListSummited(rv: RecyclerView) {}

    /**
     * Metodos utiles
     */
    protected fun scrollTo(position: Int, recyclerView: RecyclerView) {
        recyclerView.postDelayed(Runnable {
            var scrollToPosition = position
            if(recyclerView.adapter!=null && recyclerView.adapter!!.getItemCount()>position+1){
                scrollToPosition = position+1
            }
            recyclerView.smoothScrollToPosition(scrollToPosition)
        }, 500)
    }
}
