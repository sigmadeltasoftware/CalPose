package be.sigmadelta.calpose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.sigmadelta.calpose.R
import be.sigmadelta.calpose.model.CalposeActions
import java.time.YearMonth

@Composable
fun MaterialHeader(
    month: YearMonth,
    todayMonth: YearMonth,
    actions: CalposeActions,
    backgroundColor: Color
) {
    val isCurrentMonth = todayMonth == month
    Row(
        modifier = Modifier.background(color = backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { actions.onClickedPreviousMonth() },
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(imageVector = vectorResource(id = R.drawable.ic_left), tint = Color.White)
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "${month.month.name.toLowerCase().capitalize()} ${month.year}",
            modifier = Modifier.padding(vertical = 16.dp),
            style = TextStyle(
                color = Color.White,
                fontWeight = if (isCurrentMonth) FontWeight.Bold else FontWeight.SemiBold,
                fontSize = 22.sp,
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { actions.onClickedNextMonth() },
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Icon(imageVector = vectorResource(id = R.drawable.ic_right), tint = Color.White)
        }
    }
}