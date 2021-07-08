package be.sigmadelta.calpose.model.styles

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.model.CalposeDate

data class CalposeDayStyle(
    val modifier: RowScope.() -> Modifier = { defaultDayModifier() },
    val textStyle: TextStyle,
    val textProvider: (CalposeDate) -> String = { it.day.toString() },
    val container: @Composable (@Composable () -> Unit, CalposeDate) -> Unit =
        { composable, _ -> composable() }
)

fun RowScope.defaultDayModifier() = Modifier
    .padding(4.dp)
    .weight(1f)
    .fillMaxWidth()