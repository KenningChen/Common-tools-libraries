package com.kenning.kcutil.utils.recyclerviewextend

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *Description :
 *@author : KenningChen
 *Date : 2022/10/9
 */
abstract class BaseRecyclerWithLayoutAdapter(var layoutID: Int) :
    RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        _BindViewHolder(holder, position)
        holder.apply {
            itemView.setOnClickListener {
                listener?.itemClick(holder.adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return BaseRecyclerViewHolder.getHolder(parent.context, parent, layoutID)
    }

    abstract fun _BindViewHolder(holder: BaseRecyclerViewHolder, position: Int)

    interface AdapterItemClickListener {
        fun itemClick(position: Int)
    }

    var listener: AdapterItemClickListener? = null
    fun setOnItemClickListener(listener: AdapterItemClickListener) {
        this.listener = listener
    }
}