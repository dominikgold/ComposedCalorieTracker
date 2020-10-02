package com.dominikgold.calorietracker.entities

enum class MacroSplit(val carbohydrates: Percentage, val protein: Percentage, val fat: Percentage) {
    HIGH_CARB(0.5, 0.3, 0.2),
    BALANCED(0.4, 0.3, 0.3),
    HIGH_FAT(0.3, 0.3, 0.4),
    VERY_HIGH_FAT(0.2, 0.3, 0.5),
}