package com.dominikgold.calorietracker.entities

enum class TimeInterval(val numberOfDays: Int) {

    Monthly(numberOfDays = 30),
    Weekly(numberOfDays = 7),

}