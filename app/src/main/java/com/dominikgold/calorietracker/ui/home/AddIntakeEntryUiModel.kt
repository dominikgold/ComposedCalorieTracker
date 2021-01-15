package com.dominikgold.calorietracker.ui.home

import com.dominikgold.calorietracker.entities.Grams

data class AddIntakeEntryUiModel(
    val name: String,
    val calories: Int?,
    val carbohydrates: Grams?,
    val protein: Grams?,
    val fat: Grams?,
) {
    val isConfirmButtonEnabled: Boolean
        get() = name.isNotEmpty() && calories != null
}
