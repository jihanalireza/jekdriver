package com.udacoding.drivergitfire.utama.history.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.udacoding.drivergitfire.R
import com.udacoding.drivergitfire.utama.home.model.Booking


import kotlinx.android.synthetic.main.booking_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class Historydapter(
    private val mValues: List<Booking>,
    val click: itemCLick
) : RecyclerView.Adapter<Historydapter.ViewHolder>() {

    interface itemCLick {
        fun ketikaKlik(item: Booking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.booking_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mAwal.text = item.lokasiAwal
        holder.mTanggal.text = item.tanggal
        holder.mTujuan.text = item.lokasiTujuan
        when (item.status) {
            1 -> holder.itemStatus.text = "Process"
            2 -> holder.itemStatus.text = "Taking"
            3 -> holder.itemStatus.text = "Cancel"
            4 -> holder.itemStatus.text = "Complete"
        }

        holder.itemView.onClick {
            click.ketikaKlik(item)
        }


    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        var mAwal: TextView = mView.itemAwal
        val mTujuan: TextView = mView.itemTujuan
        val mTanggal: TextView = mView.itemTanggal
        val itemStatus: TextView = mView.itemStatus

    }
}
