package com.example.myapp

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import coil.load
import coil.size.Precision
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.buildJsonArray
import org.json.JSONArray
import org.json.JSONObject


class RecyclerviewCellAdapter internal constructor(mCellList: MutableList<Cell>, context: Context?) :
    RecyclerView.Adapter<RecyclerviewCellAdapter.MyViewHolder>()
{
    private val cellList: MutableList<Cell>
    private val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cell: Cell = cellList[position]
        holder.text.setText(cell.getName())
        holder.img.load(cell.getImgUrl()){
            precision(Precision.EXACT)
            scale(Scale.FIT)
        }
    }

    override fun getItemCount(): Int {
        return cellList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView
        var img: ImageView
        var button: FloatingActionButton
        private val cellLayout: LinearLayout

        init {
            text = itemView.findViewById(R.id.cellText)
            img = itemView.findViewById<ImageView>(R.id.cellImgView)
            button = itemView.findViewById<FloatingActionButton>(R.id.delButton)

            button.setOnClickListener{
                val sharedPref = context?.getSharedPreferences("global", Context.MODE_PRIVATE)
                val stringArray = sharedPref?.getString("json","")
                val jsonArray: JSONArray = JSONArray(stringArray)
                var newJsonArray: JSONArray = JSONArray()
                for (i in 0 until jsonArray.length()) {
                    val cat: JSONObject = jsonArray.getJSONObject(i)
                    if(!(cat.get("name").toString() == text.text &&
                        cat.get("imgUrl").toString() == cellList[layoutPosition].getImgUrl())){
                        newJsonArray = newJsonArray.put(cat)
                    }
                }
                val newStringArray = newJsonArray.toString()
                val edit = sharedPref?.edit()
                edit?.putString("json" , newStringArray)
                edit?.apply()

                cellList.removeAt(layoutPosition)
                notifyItemRemoved(layoutPosition)

                val toast = Toast.makeText(
                    context?.applicationContext,
                    "Removed " + text.text + " from favorites :(",
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

            cellLayout = itemView.findViewById(R.id.cellLayout)
        }

    }

    init {
        cellList = mCellList
    }
}