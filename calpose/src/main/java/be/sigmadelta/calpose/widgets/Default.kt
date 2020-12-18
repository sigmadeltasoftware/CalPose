package be.sigmadelta.calpose.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.R
import java.time.YearMonth

@Composable
fun DefaultHeader(
    month: YearMonth,
    todayMonth: YearMonth,
    actions: CalposeActions
) {
    val isCurrentMonth = todayMonth == month
    Row {
        IconButton(
            onClick = { actions.onClickedPreviousMonth() },
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(imageVector = vectorResource(id = R.drawable.ic_left))
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "${month.month.name.toLowerCase().capitalize()} ${month.year}",
            modifier = Modifier.padding(vertical = 8.dp),
            style = TextStyle(
                fontWeight = if (isCurrentMonth) FontWeight.Bold else FontWeight.SemiBold,
                fontSize = 22.sp,
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { actions.onClickedNextMonth() },
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Icon(imageVector = vectorResource(id = R.drawable.ic_right))
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