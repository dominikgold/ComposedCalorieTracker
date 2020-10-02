package com.dominikgold.calorietracker.ui.caloriegoal

import com.dominikgold.calorietracker.entities.MacroSplit
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class MacroSplitUiModelTest {

    @Test
    fun `protein amount is calculated and formatted correctly`() {
        MacroSplitUiModel(MacroSplit.HIGH_CARB, 2000).formattedProteinAmount shouldBeEqualTo "150 g"
        MacroSplitUiModel(MacroSplit.BALANCED, 2000).formattedProteinAmount shouldBeEqualTo "150 g"
        MacroSplitUiModel(MacroSplit.VERY_HIGH_FAT, 2000).formattedProteinAmount shouldBeEqualTo "150 g"
    }

    @Test
    fun `carbohydrate amount is calculated and formatted correctly`() {
        MacroSplitUiModel(MacroSplit.HIGH_CARB, 2000).formattedCarbohydratesAmount shouldBeEqualTo "250 g"
        MacroSplitUiModel(MacroSplit.BALANCED, 2000).formattedCarbohydratesAmount shouldBeEqualTo "200 g"
        MacroSplitUiModel(MacroSplit.VERY_HIGH_FAT, 2000).formattedCarbohydratesAmount shouldBeEqualTo "100 g"
    }

    @Test
    fun `fat amount is calculated and formatted correctly`() {
        MacroSplitUiModel(MacroSplit.HIGH_CARB, 2000).formattedFatAmount shouldBeEqualTo "44 g"
        MacroSplitUiModel(MacroSplit.BALANCED, 2000).formattedFatAmount shouldBeEqualTo "67 g"
        MacroSplitUiModel(MacroSplit.VERY_HIGH_FAT, 2000).formattedFatAmount shouldBeEqualTo "111 g"
    }

}