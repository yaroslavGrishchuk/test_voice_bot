package com.justai.jaicf.template

import java.net.HttpURLConnection
import java.net.URL

fun main() {
    getWeather("Санкт-Петербург")
}

fun getWeather(city: String) {
    val url = URL("http://api.openweathermap.org/data/2.5/find?q=${city},RU&type=like&appid=0274f694c257e63163d10804df84e456")

    with(url.openConnection() as HttpURLConnection) {
        requestMethod = "GET"

        println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")
        inputStream.bufferedReader().use {
            it.lines().forEach { line ->
                println(line)
            }
        }
    }
}