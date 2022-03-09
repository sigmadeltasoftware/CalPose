package be.sigmadelta.calpose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.model.CalposeDate
import be.sigmadelta.calpose.model.CalposeProperties
import be.sigmadelta.calpose.model.CalposeWidgets
import org.threeten.bp.DayOfWeek
import org.threeten.bp.YearMonth
import java.text.SimpleDateFormat
import java.util.*

const val WEIGHT_7DAY_WEEK = 1 / 7f

@Composable
fun Calpose(
    modifier: Modifier = Modifier,
    month: YearMonth,
    actions: CalposeActions,
    widgets: CalposeWidgets,
    properties: CalposeProperties = CalposeProperties(),
    firstDayOfWeek: DayOfWeek = DayOfWeek.SATURDAY
) {
    Crossfade(
        targetState = month,
        animationSpec = properties.changeMonthAnimation
    ) {
        CalposeStatic(
            modifier = modifier,
            month = it,
            actions = actions,
            widgets = widgets,
            firstDayOfWeek = firstDayOfWeek
        )
    }

}

@Composable
fun CalposeStatic(
    modifier: Modifier = Modifier,
    month: YearMonth,
    actions: CalposeActions,
    widgets: CalposeWidgets,
    properties: CalposeProperties = CalposeProperties(),
    firstDayOfWeek: DayOfWeek = DayOfWeek.MONDAY
) {
    val todayMonth = remember { YearMonth.now() }
    val weekStartOffset = firstDayOfWeek.value - 1
    Column(
        modifier = modifier.draggable(
            orientation = Orientation.Horizontal,
            state = DraggableState {},
            onDragStopped = { velocity ->
                if (velocity > properties.changeMonthSwipeTriggerVelocity) {
                    actions.onSwipedPreviousMonth()
                } else if (velocity < -properties.changeMonthSwipeTriggerVelocity) {
                    actions.onSwipedNextMonth()
                }
            })
    ) {
        CalposeHeader(month, todayMonth, actions, widgets, weekStartOffset)
        widgets.monthContainer { CalposeMonth(month, todayMonth, widgets, weekStartOffset) }
    }
}

@Composable
fun CalposeHeader(
    month: YearMonth,
    todayMonth: YearMonth,
    actions: CalposeActions,
    widgets: CalposeWidgets,
    weekStartOffset: Int
) {
    widgets.headerContainer {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            widgets.header(month, todayMonth, actions)
            widgets.headerDayRow((0..6).map { DayOfWeek.of((it + weekStartOffset) % 7 + 1)}.toSet())
        }
    }
}

@Composable
fun CalposeMonth(month: YearMonth, todayMonth: YearMonth, widgets: CalposeWidgets, weekStartOffset: Int) {
    var firstDayOffset = month.atDay(1).dayOfWeek.ordinal - weekStartOffset
    if(firstDayOffset < 1)  firstDayOffset += 7
    if(firstDayOffset > 7)  firstDayOffset -= 7
    if(firstDayOffset == 7) firstDayOffset = 0
    val monthLength = month.lengthOfMonth()
    val priorMonthLength = month.minusMonths(1).lengthOfMonth()
    val lastDayCount = (monthLength + firstDayOffset) % 7
    val weekCount = (firstDayOffset + monthLength) / 7
    val today = SimpleDateFormat("dd").format(Date(System.currentTimeMillis())).toInt()

    for (i in 0..weekCount) {
        widgets.weekContainer {
            CalposeWeek(
                startDayOffSet = firstDayOffset,
                endDayCount = lastDayCount,
                monthWeekNumber = i,
                weekCount = weekCount,
                priorMonthLength = priorMonthLength,
                today = CalposeDate(
                    day = today,
                    dayOfWeek = todayMonth.atDay(today).dayOfWeek,
                    month = todayMonth
                ),
                month = month,
                widgets = widgets,
                weekStartOffset = weekStartOffset
            )
        }
    }
}
@Composable
fun CalposeWeek(
    startDayOffSet: Int,
    endDayCount: Int,
    monthWeekNumber: Int,
    weekCount: Int,
    priorMonthLength: Int,
    today: CalposeDate,
    month: YearMonth,
    widgets: CalposeWidgets,
    weekStartOffset: Int
) {
    Row {
        var dayOfWeekOrdinal = weekStartOffset + 1
        if (monthWeekNumber == 0) {
            for (i in 1 .. startDayOffSet) {
                val priorDay = (priorMonthLength - (startDayOffSet - i))
                widgets.priorMonthDay(
                    this,
                    CalposeDate(
                        priorDay,
                        DayOfWeek.of(dayOfWeekOrdinal++ % 7 + 1),
                        month.minusMonths(1)
                    )
                )
            }
        }

        val endDay = when (monthWeekNumber) {
            0 -> 7 - startDayOffSet
            weekCount -> endDayCount
            else -> 7
        }

        for (i in 1..endDay) {
            val day = if (monthWeekNumber == 0) i else (i + (7 * monthWeekNumber) - startDayOffSet)
            widgets.day(
                this,
                CalposeDate(day, DayOfWeek.of(dayOfWeekOrdinal++ % 7 + 1), month),
                today
            )
        }

        if (monthWeekNumber == weekCount && endDayCount > 0) {
            for (i in 0 until (7 - endDayCount)) {
                val nextMonthDay = i + 1
                widgets.nextMonthDay(
                    this, CalposeDate(
                        nextMonthDay,
                        DayOfWeek.of(dayOfWeekOrdinal++ % 7 + 1),
                        month.plusMonths(1)
                    )
                )
            }
        }
    }
}

