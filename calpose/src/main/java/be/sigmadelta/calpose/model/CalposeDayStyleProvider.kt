package be.sigmadelta.calpose.model

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import be.sigmadelta.calpose.model.styles.CalposeDayStyle
import be.sigmadelta.calpose.widgets.DefaultMarkerContainer

data class CalposeDayStyleProvider(
    val day: (dayDate: CalposeDate, todayData: CalposeDate) -> CalposeDayStyle = { dayDate, todayDate ->
        val isToday = dayDate == todayDate
        val dayHasPassed = dayDate.day < todayDate.day
        val isCurrentMonth = dayDate.month == todayDate.month

        CalposeDayStyle(
            textStyle = TextStyle(
                color = when {
                    isCurrentMonth && dayHasPassed -> Color.Gray
                    isToday -> Color.White
                    else -> Color.Black
                },
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
            ),
            container = {
                if (isToday) {
                    DefaultMarkerContainer(markerColor = MaterialTheme.colors.primary) {
                        it()
                    }
                } else {
                    it()
                }
            }
        )
    },
    val priorMonthDay: (dayDate: CalposeDate) -> CalposeDayStyle = {
        CalposeDayStyle(
            textStyle = TextStyle(
                color = Color.Gray
            )
        )
    },
    val nextMonthDay: (dayDate: CalposeDate) -> CalposeDayStyle = priorMonthDay
)