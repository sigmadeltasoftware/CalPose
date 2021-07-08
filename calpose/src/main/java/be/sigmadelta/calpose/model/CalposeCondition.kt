package be.sigmadelta.calpose.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import be.sigmadelta.calpose.model.styles.CalposeDayStyle
import be.sigmadelta.calpose.widgets.DefaultMarkerContainer

open class CalposeCondition(
    val block: (dayDate: CalposeDate, todayDate: CalposeDate?) -> Boolean,
    val style: CalposeDayStyle
) {
    internal lateinit var dayDate: CalposeDate
    fun getCurrentDate() = dayDate
}

class TodayCondition(
    style: CalposeDayStyle = CalposeDayStyle(
        textStyle = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold
        ),
        container = { it, _ -> DefaultMarkerContainer(markerColor = markerColor, block = it) }
    ),
    private val markerColor: Color
) : CalposeCondition(
    block = { date: CalposeDate, todayDate: CalposeDate? -> date == todayDate },
    style = style
)

class IsCurrentMonthAndDayHasPassedCondition(
    style: CalposeDayStyle = CalposeDayStyle(
        textStyle = TextStyle(
            color = Color.Gray,
        )
    )
) : CalposeCondition(
    block = { dayDate: CalposeDate, todayDate: CalposeDate? ->
        val dayHasPassed = dayDate.day < todayDate?.day ?: -1
        val isCurrentMonth = dayDate.month == todayDate?.month
        if (todayDate == null) {
            false
        } else {
            dayHasPassed && isCurrentMonth
        }
    },
    style = style
)

class PriorOrNextMonthDayCondition(
    style: CalposeDayStyle = CalposeDayStyle(
        textStyle = TextStyle(
            color = Color(0xa0bdbdbd)
        )
    )
) : CalposeCondition(
    block = { _, todayDate -> todayDate == null },
    style = style
)

class ElseCondition(
    style: CalposeDayStyle = CalposeDayStyle(
        textStyle = TextStyle(
            color = Color.Black
        )
    )
) : CalposeCondition(
    block = { _, _ -> true },
    style = style
)
