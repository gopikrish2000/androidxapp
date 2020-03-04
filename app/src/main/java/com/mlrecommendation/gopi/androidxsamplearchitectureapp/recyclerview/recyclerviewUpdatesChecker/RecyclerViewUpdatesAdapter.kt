package com.mlrecommendation.gopi.androidxsamplearchitectureapp.recyclerview.recyclerviewUpdatesChecker

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R

/**
 * Created by Gopi Krishna on 02/03/20.
 */
class RecyclerViewUpdatesAdapter(
    var itemList: MutableList<GopiRecycleItem>,
    val itemClickListener: (item: GopiRecycleItem, position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerViewUpdatesViewHolder>() {

    override fun onCreateViewHolder(
        container: ViewGroup,
        position: Int
    ): RecyclerViewUpdatesViewHolder {
        println("RecyclerViewUpdatesAdapter.onCreateViewHolder with position $position")
        if (getItemViewType(position) == 0) return RecyclerViewUpdatesViewHolder(
            LayoutInflater.from(
                container.context
            ).inflate(R.layout.recyclerview_updates, container, false)
        )
        return RecyclerViewUpdatesViewHolder(
            LayoutInflater.from(container.context).inflate(
                R.layout.recyclerview_updates,
                container,
                false
            )
        )
    }

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int) = 0

    override fun onBindViewHolder(viewHolder: RecyclerViewUpdatesViewHolder, position: Int) {
        println("RecyclerViewUpdatesAdapter.onBindViewHolder with position $position")
        val item = itemList[position]
        viewHolder.itemView.setOnClickListener { itemClickListener(item, position) }
        viewHolder.itemTv.text = "name is ${item.name} age is ${item.age}"
    }
}

data class GopiRecycleItem(var name:String = "gopi", var age:Int = 20) {}

class RecyclerViewUpdatesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val itemTv = view.findViewById<TextView>(R.id.sampleTv)
//    val itemTvOther = view.findViewById<TextView>(R.id.diffTv)
//    val itemTvMore = view.findViewById<TextView>(R.id.diffTv)
}