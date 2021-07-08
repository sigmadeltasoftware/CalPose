package be.sigmadelta.calpose.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.R
import be.sigmadelta.calpose.model.CalposeActions
import org.threeten.bp.YearMonth

@Composable
fun DefaultHeader(
    month: YearMonth,
    todayMonth: YearMonth,
    actions: CalposeActions,
    titleContainer: @Composable (@Composable () -> Unit) -> Unit = { it() }
) {
    val isCurrentMonth = todayMonth == month
    Row {
        IconButton(
            onClick = { actions.onClickedPreviousMonth() },
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_left),
                contentDescription = "Left"
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        titleContainer {
            DefaultMonthTitle(month = month, isCurrentMonth = isCurrentMonth)
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { actions.onClickedNextMonth() },
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_right),
                contentDescription = "Right"
            )
        }
    }
}

@Composable
fun DefaultDay(
    text: String,
    modifier: Modifier = Modifier.padding(4.dp),
    style: TextStyle = TextStyle()
) {
    Text(
        text,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = style
    )
}