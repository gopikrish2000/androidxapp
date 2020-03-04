package com.mlrecommendation.gopi.androidxsamplearchitectureapp.recyclerview.diffUtil

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R

/**
 * Created by Gopi Krishna on 2020-01-22.
 */
class DiffUtilAdapter(
    val itemClickListener: (item: DiffUtilItem, position: Int) -> Unit
) : RecyclerView.Adapter<DiffUtilViewHolder>() {

    var itemList: MutableList<DiffUtilItem> = mutableListOf()

    override fun onCreateViewHolder(container: ViewGroup, position: Int): DiffUtilViewHolder {
        if (getItemViewType(position) == 0) return DiffUtilViewHolder(
            LayoutInflater.from(container.context).inflate(R.layout.diff_util_item,container,false)
        )
        return DiffUtilViewHolder(
            LayoutInflater.from(container.context).inflate(R.layout.diff_util_item,container,false)
        )
    }

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int) = 0

    override fun onBindViewHolder(viewHolder: DiffUtilViewHolder, position: Int) {
        val item = itemList[position]
        viewHolder.itemView.setOnClickListener { itemClickListener(item, position) }
        viewHolder.itemTv.text = item.name
    }

    fun updateData(modifiedList: MutableList<DiffUtilItem>){
        val diffUtilImplementor = DiffUtilImplementor(itemList, modifiedList)
        val diffResult = DiffUtil.calculateDiff(diffUtilImplementor)
        itemList.clear()
        itemList.addAll(modifiedList)
//        notifyDataSetChanged()
        diffResult.dispatchUpdatesTo(this)
    }
}

data class DiffUtilItem(var name:String, var age:Int, var id:String) {}

class DiffUtilViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val itemTv = view.findViewById<TextView>(R.id.diffTv)
}