package com.example.myapp

import kotlinx.serialization.*

@Serializable
class Cell {
    private var name: String?
    private var imgUrl: String?

    constructor(name: String, imgUrl: String){
        this.name = name
        this.imgUrl = imgUrl
    }

    fun getName(): String? {
        return this.name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getImgUrl(): String? {
        return this.imgUrl
    }

    fun setImgUrl(imgUrl: String) {
        this.imgUrl = imgUrl
    }

}