package com.cts.avin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.cts.avin.R
import com.cts.avin.data.main.Rows

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

class AboutListAdapter(private val itemSelectedListener: AboutListItemSelectedListener) : RecyclerView.Adapter<AboutListAdapter.ItemViewHolder>() {
    var data: List<Rows> = ArrayList()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.aboutlist_itemview, parent, false)
        return ItemViewHolder(view, itemSelectedListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

     inner class ItemViewHolder(itemView: View, itemSelectedListener: AboutListItemSelectedListener) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.tv_name)
        var tvName: TextView? = null
        @BindView(R.id.tv_desc)
        var tvDesc: TextView? = null
        @BindView(R.id.iv_image)
        var ivImage: ImageView? = null
        private var data: Rows? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { v ->
                if (data != null) {
                    itemSelectedListener.onItemSelected(data!!)
                }
            }
        }

        fun bind(data: Rows) {
            this.data = data
            tvName!!.text = data.title
            tvDesc!!.text = data.description
            Glide.with(ivImage!!.context).load(data.imageHref).centerInside()
                    .placeholder(R.drawable.ic_launcher_background).into(ivImage!!)

        }
    }

}
