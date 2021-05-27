package com.justai.jaicf.template

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import java.net.URL
import kotlin.math.roundToInt


const val kelvinToCelConst = 273.15

fun getWeather(city: String, weatherApiKey: String): Int {
    val text =
        URL("https://api.openweathermap.org/data/2.5/find?q=$city&type=like&appid=$weatherApiKey").readText()
    val json = Json.parseToJsonElement(text).jsonObject
    return if (json["count"].toString().toInt() > 0) {
        (json["list"]?.jsonArray?.get(0)?.jsonObject?.get("main")?.jsonObject?.get("temp").toString()
            .toDouble() - kelvinToCelConst).roundToInt()
    } else {
        -300
    }

}