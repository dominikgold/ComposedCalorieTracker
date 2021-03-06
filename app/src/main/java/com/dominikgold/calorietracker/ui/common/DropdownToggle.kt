package com.dominikgold.calorietracker.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.theming.TextStyles
import com.dominikgold.calorietracker.theming.textColorDefault
import com.dominikgold.calorietracker.util.InLightAndDarkTheme

@Composable
fun TextDropdownToggle(text: String?, placeholder: String?, isExpanded: Boolean, modifier: Modifier = Modifier) {
    val iconResource = if (isExpanded) R.drawable.vec_icon_arrow_drop_up else R.drawable.vec_icon_arrow_drop_down
    Row(modifier) {
        Text(
            text = text ?: placeholder ?: "",
            style = TextStyles.Subtitle,
        )
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = ImageVector.vectorResource(id = iconResource),
            contentDescription = null,
            tint = textColorDefault,
        )
    }
}

@Preview
@Composable
fun TextDropdownTogglePreview() {
    InLightAndDarkTheme {
        Column(Modifier.background(MaterialTheme.colors.background)) {
            TextDropdownToggle(
                text = "Example",
                placeholder = null,
                isExpanded = false,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextDropdownToggle(
                text = null,
                placeholder = "placeholder",
                isExpanded = false,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
