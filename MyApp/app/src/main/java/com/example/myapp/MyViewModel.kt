package com.example.myapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONArray
import org.json.JSONObject
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
        if(initialCats != null) {
            val jsonCatArray = JSONArray(initialCats!!)

            for (i in 0 until jsonCatArray.length()) {
                val cat: JSONObject = jsonCatArray.getJSONObject(i)
                val cell = Cell(cat.get("name").toString(), cat.get("imgUrl").toString())
                rawCells.add(cell)
            }
        }

        return rawCells
    }

    fun addCell(cell: Cell){
        val rawCells = cells.value
        rawCells?.add(cell)
        cells.postValue(rawCells)
    }
}