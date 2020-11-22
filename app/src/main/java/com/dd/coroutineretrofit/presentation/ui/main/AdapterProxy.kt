package com.dd.coroutineretrofit.presentation.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dd.coroutineretrofit.R
import com.dd.coroutineretrofit.data.db.ProxyAddress
import kotlinx.android.synthetic.main.content_item.view.*

class AdapterProxy(private var list: List<ProxyAddress>) :
    RecyclerView.Adapter<AdapterProxy.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvId.text = list[position].id.toString()
        holder.itemView.tvIpAddress.text = list[position].ip
        holder.itemView.tvPort.text = list[position].port.toString()
        holder.itemView.tvRequestFailureDescription.text = list[position].requestFailureDescription
        holder.itemView.tvIsUsed.text = if (list[position].isUsed) "Old" else "New"



        holder.itemView.rootView.setBackgroundColor(

            ContextCompat.getColor(
                context,
                if (list[position].isSuccessfullySent) {
                    android.R.color.holo_green_light
                }else if (!list[position].isUsed){
                    android.R.color.white
                }
                else {
                    android.R.color.holo_red_light
                }
            )
        )


    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(it: List<ProxyAddress>) {
        this.list = it
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}