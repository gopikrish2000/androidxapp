package com.mlrecommendation.gopi.androidxsamplearchitectureapp.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils
import java.util.*

/**
 * Created by Gopi Krishna on 2020-01-17.
 */
class GopiRecyclerviewAdapter( var itemList: MutableList<GopiItem>, val itemClickListener: (item: GopiItem, position: Int) -> Unit) : RecyclerView.Adapter<GopiViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return itemList[position].id.hashCode().toLong() // always pass a uniqueId here never position which can change
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): GopiViewHolder {
        println("GopiRecyclerview.onCreateViewHolder with viewType $viewType")
        if (viewType == 0) return GopiViewHolder(
            LayoutInflater.from(container.context).inflate(
                R.layout.gopi_recycle_item,
                container,
                false
            )
        )
        return GopiViewHolder(LayoutInflater.from(container.context).inflate(R.layout.gopi_recycle_item, container, false))
    }

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int) = 0

    override fun onBindViewHolder(viewHolder: GopiViewHolder, position: Int) {
        println("GopiRecyclerview.onBindViewHolder with position $position")
        val item = itemList[position]
        viewHolder.itemView.setOnClickListener { itemClickListener(item, viewHolder.adapterPosition) }
        viewHolder.itemTv.text = item.name + "-" + item.id
        viewHolder.itemView.tag = position
//        viewHolder.imageView
        Glide.with(viewHolder.imageView.context).load("https://dummyimage.com/30x30/f00/fff&text=ss").into(viewHolder.imageView)
//        Glide.with(viewHolder.imageView.context).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbk0YDyfWSiZJNMehf3KvJrbLPvVyUTyM2nt0TGwCGjJB06-WDNg").into(viewHolder.imageView)
//        Glide.with(viewHolder.imageView.context).load("http://goo.gl/gEgYUd").into(viewHolder.imageView)
//        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(viewHolder.imageView.context, R.drawable.computer))
    }

    override fun onBindViewHolder(holder: GopiViewHolder, position: Int, payloads: MutableList<Any>) {
        println("GopiRecyclerview.onBindViewHolder with Payload with position $position")
        /*if(CommonUtils.isPortraitMode()){
            holder.itemView.layoutParams = ConstraintLayout.LayoutParams(deviceWidth()/2 , deviceHeight()/2)
        }
        if(CommonUtils.isLandscapeMode()){
            holder.itemView.layoutParams = ConstraintLayout.LayoutParams(deviceWidth()/3 , deviceHeight()/3)
        }*/
        if(payloads.isNotEmpty() && payloads[0] is String && payloads[0] == "gopipayload"){
            holder.itemTv.text = itemList[position].name
//            + "GG"
            return
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onViewRecycled(holder: GopiViewHolder) {
        super.onViewRecycled(holder)
        println("GopiRecyclerview.onViewRecycled position ${holder.itemView.tag} adapterPosition ${holder.adapterPosition}")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        println("GopiRecyclerview.onAttachedToRecyclerView with rv instance")
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        println("GopiRecyclerview.onDetachedFromRecyclerView with rv instance")
    }

    override fun onViewAttachedToWindow(holder: GopiViewHolder) {
        super.onViewAttachedToWindow(holder)
        println("GopiRecyclerview.onViewAttachedToWindow position ${holder.itemView.tag} adapterPosition ${holder.adapterPosition}")
    }

    override fun onViewDetachedFromWindow(holder: GopiViewHolder) {
        super.onViewDetachedFromWindow(holder)
        println("GopiRecyclerview.onViewDetachedFromWindow ${holder.itemView.tag} adapterPosition ${holder.adapterPosition}")
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
    }


}

data class GopiItem(var name:String, var age:Int = ThreadUtils.randomNumberInRange(1,99), var id: String = UUID.randomUUID().toString()) {}

class GopiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val itemTv = view.findViewById<TextView>(R.id.itemTv)
    val imageView = view.findViewById<ImageView>(R.id.itemIv)
}