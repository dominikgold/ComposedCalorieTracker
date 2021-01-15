package com.dominikgold.calorietracker.entities

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class MacroGoalsTest {

    @Test
    fun `protein amount is calculated correctly`() {
        MacroGoals.createWithMacroSplit(2000, MacroSplit.HIGH_CARB).protein shouldBeEqualTo 150
        MacroGoals.createWithMacroSplit(2000, MacroSplit.BALANCED).protein shouldBeEqualTo 150
        MacroGoals.createWithMacroSplit(2000, MacroSplit.VERY_HIGH_FAT).protein shouldBeEqualTo 150
    }

    @Test
    fun `carbohydrate amount is calculated correctly`() {
        MacroGoals.createWithMacroSplit(2000, MacroSplit.HIGH_CARB).carbohydrates shouldBeEqualTo 250
        MacroGoals.createWithMacroSplit(2000, MacroSplit.BALANCED).carbohydrates shouldBeEqualTo 200
        MacroGoals.createWithMacroSplit(2000, MacroSplit.VERY_HIGH_FAT).carbohydrates shouldBeEqualTo 100
    }

    @Test
    fun `fat amount is calculated correctly`() {
        MacroGoals.createWithMacroSplit(2000, MacroSplit.HIGH_CARB).fat shouldBeEqualTo 44
        MacroGoals.createWithMacroSplit(2000, MacroSplit.BALANCED).fat shouldBeEqualTo 67
        MacroGoals.createWithMacroSplit(2000, MacroSplit.VERY_HIGH_FAT).fat shouldBeEqualTo 111
    }

}