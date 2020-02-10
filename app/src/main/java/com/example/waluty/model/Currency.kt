package com.example.waluty.model
import java.io.Serializable


data class Currency(
    val currency:String,
    val code:String,
    val mid:Double):Serializable