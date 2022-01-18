package com.example.myapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class MyViewModel : ViewModel() {
    private val cells: MutableLiveData<MutableList<Cell>> = MutableLiveData(prepareCells())

    fun getCells(): LiveData<MutableList<Cell>> {
        return cells
    }

    private fun prepareCells(): ArrayList<Cell> {
        val rawCells = ArrayList<Cell>()
        for (i in 0..10) {
            val cell = Cell("Chat$i", "https://cdn2.thecatapi.com/images/bcb.jpg")
            rawCells.add(cell)
        }
        //TODO: Deserialize initial cats


        return rawCells
    }

    fun addCell(cell: Cell){
        val rawCells = cells.value
        rawCells?.add(cell)
        cells.postValue(rawCells)
    }
}