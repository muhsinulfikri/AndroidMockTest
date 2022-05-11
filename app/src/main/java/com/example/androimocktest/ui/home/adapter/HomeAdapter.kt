package com.example.androimocktest.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androimocktest.data.local.entity.Items
import com.example.androimocktest.databinding.ItemsLayoutBinding

class HomeAdapter(private val itemClick: (Items) -> Unit): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var items: MutableList<Items> = mutableListOf()

    fun setItems(items: List<Items>){
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    private fun addItems(items: List<Items>){
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    private fun clearItems(){
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    class HomeViewHolder(
        private val binding: ItemsLayoutBinding,
        val itemClick: (Items) -> Unit
    ): RecyclerView.ViewHolder(binding.root){

        fun bindView(item: Items){
            with(item){
                binding.tvTitle.text = item.itemsName
                itemView.setOnClickListener{
                    itemClick(this)
                }
            }
        }
    }
}