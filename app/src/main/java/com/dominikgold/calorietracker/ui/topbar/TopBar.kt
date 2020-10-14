package com.dominikgold.calorietracker.ui.topbar

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.navigation.viewModel
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.util.inLightAndDarkTheme

@Composable
fun CalorieTrackerTopBar(
    title: String,
    navigationIcon: VectorAsset? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val viewModel: TopBarViewModel = viewModel()
    CalorieTrackerTopBarContent(
        title = title,
        navigationIcon = navigationIcon,
        onNavigationIconClick = viewModel::onNavigateBackClicked,
        actions = actions,
    )
}

@Composable
private fun CalorieTrackerTopBarContent(
    title: String,
    navigationIcon: VectorAsset? = null,
    onNavigationIconClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon?.let { navIcon ->
            { NavigationIcon(icon = navIcon, onClick = onNavigationIconClick) }
        },
        actions = actions,
    )
}

@Composable
private fun NavigationIcon(icon: VectorAsset, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(asset = icon)
    }
}

@Preview
@Composable
fun TopBarPreview() {
    inLightAndDarkTheme {
        Column {
            CalorieTrackerTopBarContent(title = "Chores")
            Spacer(Modifier.height(32.dp))
            CalorieTrackerTopBarContent(
                title = "Chores",
                navigationIcon = vectorResource(id = R.drawable.vec_icon_close),
            )
            Spacer(Modifier.height(32.dp))
            CalorieTrackerTopBarContent(
                title = "Chores",
                navigationIcon = vectorResource(id = R.drawable.vec_icon_close),
                actions = {
                    TopBarActionTextButton(text = "APPLY", enabled = true, onClick = {})
                },
            )
        }
    }
}