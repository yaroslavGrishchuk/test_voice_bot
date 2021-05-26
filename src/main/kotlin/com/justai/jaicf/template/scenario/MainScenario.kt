package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario

val mainScenario = Scenario {
    state("start") {
        activators {
            regex("/start")
            intent("Hello")
        }
        action {
            reactions.run {
                sayRandom(
                    "Hello! How can I help?",
                    "Hi there! How can I help you?"
                )
            }
        }
    }

    state(name = "weather") {
        activators {
            intent("/Weather")
        }
        action {
            reactions.say("В каком городе?")
            reactions.go("/Weather/chooseCity")
        }
    }

    state("chooseCity") {
        activators {
            intent("/Weather/chooseCity")
        }
        action {
            val city = activator.caila?.slots?.get("city") ?: error("Missing city")
            com.justai.jaicf.template.getWeather(city)
        }
    }

    state("smalltalk", noContext = true) {
        activators {
            anyIntent()
        }

        action(caila) {
            activator.topIntent.answer?.let { reactions.say(it) } ?: reactions.go("/fallback")
        }
    }

    fallback {
        reactions.sayRandom(
            "Sorry, I didn't get that...",
            "Sorry, could you repeat please?"
        )
    }
}