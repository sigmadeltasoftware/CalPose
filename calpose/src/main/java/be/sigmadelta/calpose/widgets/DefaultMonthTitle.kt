package be.sigmadelta.calpose.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.sigmadelta.calpose.model.styles.CalposeTitleStyle
import org.threeten.bp.YearMonth

@Composable
fun DefaultMonthTitle(
    month: YearMonth,
    isCurrentMonth: Boolean = false,
    calposeTitleStyle: CalposeTitleStyle = CalposeTitleStyle(),
) {
    val title = remember(month) { calposeTitleStyle.dateFormat(month) }

    Text(
        text = title,
        modifier = Modifier.padding(vertical = 8.dp),
        style = TextStyle(
            fontWeight = if (isCurrentMonth) FontWeight.Bold else FontWeight.SemiBold,
            fontSize = 22.sp,
        ).merge(calposeTitleStyle.style),
        color = calposeTitleStyle.color
    )
}

@Preview("NonCurrentMonth",widthDp = 200,heightDp = 40)
@Composable
fun NonCurrentMonthPreview(){
    DefaultMonthTitle(month = YearMonth.of(2020,10), isCurrentMonth = false)
}

@Preview("CurrentMonth",widthDp = 200,heightDp = 40)
@Composable
fun CurrentMonthPreview(){
    DefaultMonthTitle(month = YearMonth.of(2020,8), isCurrentMonth = true)
}