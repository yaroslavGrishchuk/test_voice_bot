package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.CailaNLUSettings
import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import java.util.*

val mainScenario = Scenario {
    var weatherApiKey = ""
    state("start") {
        activators {
            regex("/start")
            intent("Hello")
        }
        action {
            weatherApiKey = Properties().run {
                load(CailaNLUSettings::class.java.getResourceAsStream("/jaicp.properties"))
                getProperty("weatherApiKey")
            }
            reactions.run {
                say(
                    "Привет, чем я могу помочь?"
                )
            }
        }
    }

    state(name = "weather") {
        activators {
            regex("/Weather")
            intent("Weather")
        }
        action {
            var city = ""
            activator.caila?.run { city = slots["city"].toString() }
            val temp = com.justai.jaicf.template.getWeather(city, weatherApiKey)
            if (temp == -300) {
                reactions.say("Я не знаю такого города $city")
            } else {
                val case = when {
                    temp % 100 in 11..19 -> {
                        "градусов"
                    }
                    temp % 10 == 1 -> {
                        "градус"
                    }
                    temp % 10 == 2 || temp % 10 == 3 || temp % 10 == 4 -> {
                        "градуса"
                    }
                    else -> {
                        "градусов"
                    }
                }
                when {
                    temp < 0 -> {
                        reactions.say("В городе $city ${-temp} $case мороза")
                    }
                    temp == 0 -> {
                        reactions.say("В городе $city ноль $case")
                    }
                    else -> {
                        reactions.say("В городе $city $temp $case тепла")
                    }
                }
            }

        }
    }


    fallback {
        reactions.sayRandom(
            "Боюсь, я тебя не понимаю",
            "Извини, не мог ли бы ты повторить еще раз?"
        )
    }
}