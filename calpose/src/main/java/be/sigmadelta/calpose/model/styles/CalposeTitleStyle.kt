package be.sigmadelta.calpose.model.styles

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter

data class CalposeTitleStyle(
    val color: Color = Color.Black,
    val style: TextStyle = TextStyle.Default,
    val container: @Composable (@Composable () -> Unit) -> Unit = { it() },
    val dateFormat: (YearMonth) -> String = { month ->
        val formatter = DateTimeFormatter.ofPattern("MMMM  yyyy")
        month.format(formatter)
    }
)
