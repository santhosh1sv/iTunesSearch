package com.altimetrik.itunessearch.utilities

import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.Adapter<RecyclerView.ViewHolder>.addAll(list: ArrayList<Any>, List: List<Any>) {
    val iterator = List.iterator()
    val newList = ArrayList<Any>()
    while (iterator.hasNext()) {
        val item = iterator.next()
        item.let { newList.add(it) }
    }
    list.addAll(newList)
    notifyDataSetChanged()
}

fun RecyclerView.Adapter<RecyclerView.ViewHolder>.clear(list: ArrayList<Any>) {
    list.clear()
    notifyDataSetChanged()
}




