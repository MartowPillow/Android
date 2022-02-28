package com.example.myapp

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MyViewModel : ViewModel() {

    private var cells: MutableLiveData<MutableList<Cell>> = MutableLiveData(prepareCells())
    private var initialCats: String? = null

    fun getCells(): LiveData<MutableList<Cell>> {
        return cells
    }

    fun setInitialCats(Cats: String?){
        initialCats = Cats
        cells = MutableLiveData(prepareCells())
    }

    private fun prepareCells(): ArrayList<Cell> {
        val rawCells = ArrayList<Cell>()

        val jsonCatArray: JSONArray = JSONArray(initialCats!!)

        for (i in 0 until jsonCatArray.length()) {
            val cat: JSONObject = jsonCatArray.getJSONObject(i)
            val cell = Cell(cat.get("name").toString(), cat.get("imgUrl").toString())
            rawCells.add(cell)
        }

        return rawCells
    }

    fun addCell(cell: Cell){
        val rawCells = cells.value
        rawCells?.add(cell)
        cells.postValue(rawCells)
    }
}