package com.example.checheritox.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MusiXListAdapter<T>(
    private val manager: MusiXActivity.RecyclerViewManager<T>
) : ListAdapter<T, MusiXListAdapter.MyViewHolder<T>>(MusiXItemAdapterDiffCallbac<T>(manager)) {

    class MyViewHolder<T>(val manager: MusiXActivity.RecyclerViewManager<T>, val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
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

class MusiXItemAdapterDiffCallbac <T>(private val manager: MusiXActivity.RecyclerViewManager<T>) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return manager.areItemsTheSame(newItem, oldItem)
    }
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return manager.areContentsTheSame(newItem, oldItem)
    }

}
