package com.dominikgold.calorietracker.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.util.translated

enum class SettingsListItem { ChangeCalorieGoal,
    ChangeMeasurementSystem,
    SetReminders,
}

val SettingsListItem.icon
    @Composable get() = ImageVector.vectorResource(id = when (this) {
        SettingsListItem.ChangeCalorieGoal -> R.drawable.vec_icon_change_calorie_goal
        SettingsListItem.ChangeMeasurementSystem -> R.drawable.vec_icon_change_measurement_system
        SettingsListItem.SetReminders -> R.drawable.vec_icon_set_reminders
    })

val SettingsListItem.title
    @Composable get() = translated(resourceId = when (this) {
        SettingsListItem.ChangeCalorieGoal -> R.string.settings_item_change_calorie_goal
        SettingsListItem.ChangeMeasurementSystem -> R.string.settings_item_change_measurement_system
        SettingsListItem.SetReminders -> R.string.settings_item_set_reminders
    })