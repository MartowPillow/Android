package com.example.myapp

import android.widget.LinearLayout

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso


class RecyclerviewCellAdapter internal constructor(mCellList: List<Cell>) :
    RecyclerView.Adapter<RecyclerviewCellAdapter.MyViewHolder>()
{
    private val CellList: List<Cell>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cell: Cell = CellList[position]
        holder.text.setText(cell.getName())
        Picasso.with(holder.img.context)
            .load(cell.getImgUrl())
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return CellList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView
        var img: ImageView
        private val cellLayout: LinearLayout

        init {
            text = itemView.findViewById(R.id.cellText)
            img = itemView.findViewById<ImageView>(R.id.cellImgView)
            cellLayout = itemView.findViewById(R.id.cellLayout)
        }
    }

    init {
        CellList = mCellList
    }
}