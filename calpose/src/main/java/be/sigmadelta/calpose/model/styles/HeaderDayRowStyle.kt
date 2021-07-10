package be.sigmadelta.calpose.model.styles

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.WEIGHT_7DAY_WEEK
import org.threeten.bp.DayOfWeek

data class HeaderDayRowStyle(
    val textProvider: (DayOfWeek) -> String = { it.name.first().toString() },
    val modifier: RowScope.() -> Modifier = {
        Modifier.weight(WEIGHT_7DAY_WEEK)
    },
    val textStyle: TextStyle = TextStyle(color = Color.Gray),
    val container: @Composable (@Composable () -> Unit) -> Unit = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            it()
        }
    }
)
