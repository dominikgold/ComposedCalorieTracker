package com.dominikgold.calorietracker.ui.bodyweight

import androidx.compose.runtime.Composable
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.entities.TimeInterval
import com.dominikgold.calorietracker.util.translated

@Composable
fun TimeInterval.getTimeAgoText(timePeriodIndex: Int) = when (timePeriodIndex) {
    0 -> translated(resourceId = when (this) {
        TimeInterval.Monthly -> R.string.body_weight_period_current_month
        TimeInterval.Weekly -> R.string.body_weight_period_current_week
    })
    1 -> translated(resourceId = when (this) {
        TimeInterval.Monthly -> R.string.body_weight_period_last_month
        TimeInterval.Weekly -> R.string.body_weight_period_last_week
    })
    else -> {
        val formatString = when (this) {
            TimeInterval.Monthly -> R.string.past_body_weight_period_monthly_time_interval_format
            TimeInterval.Weekly -> R.string.past_body_weight_period_weekly_time_interval_format
        }
        translated(resourceId = formatString, formatArgs = listOf(timePeriodIndex))
    }
}