package be.sigmadelta.calpose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.sigmadelta.calpose.R
import be.sigmadelta.calpose.model.CalposeActions
import org.threeten.bp.YearMonth

@Composable
fun MaterialHeader(
    month: YearMonth,
    todayMonth: YearMonth,
    actions: CalposeActions,
    backgroundColor: Color,
    titleContainer: @Composable (@Composable () -> Unit) -> Unit = { it() }
) {
    val isCurrentMonth = todayMonth == month
    Row(
        modifier = Modifier.background(color = backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DefaultIconButton(
            modifier = Modifier.padding(start = 16.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_left),
            onClick = actions::onClickedPreviousMonth.get(),
            contentDescription = "Left",
            tint = Color.White
        )

        Spacer(modifier = Modifier.weight(1f))

        titleContainer {
            DefaultMonthTitle(
                month = month,
                isCurrentMonth = isCurrentMonth,
                textStyle = TextStyle(fontSize = 22.sp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        DefaultIconButton(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_right),
            onClick = actions::onClickedNextMonth.get(),
            contentDescription = "Right"
        )
    }
}