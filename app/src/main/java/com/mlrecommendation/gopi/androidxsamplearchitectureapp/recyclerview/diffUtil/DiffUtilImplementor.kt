package com.mlrecommendation.gopi.androidxsamplearchitectureapp.recyclerview.diffUtil

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Gopi Krishna on 2020-01-23.
 */
class DiffUtilImplementor(var oldList: MutableList<DiffUtilItem>, var newList: MutableList<DiffUtilItem>) :DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val result = oldList[oldItemPosition].name == newList[newItemPosition].name
        println("DiffUtilImplementor.areContentsTheSame old ${oldList[oldItemPosition]} new ${newList[newItemPosition]} is $result")
        return result
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        println("DiffUtilImplementor.getChangePayload with ${oldList[oldItemPosition]} - ${newList[newItemPosition]}") // this is crashing with indexOutOfBounds
        println("DiffUtilImplementor.getChangePayload with ${oldItemPosition} - ${newItemPosition}")
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}