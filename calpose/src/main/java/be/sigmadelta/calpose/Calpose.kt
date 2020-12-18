package be.sigmadelta.calpose

import android.icu.util.Calendar
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.gesture.DragObserver
import androidx.compose.ui.gesture.dragGestureFilter
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.model.CalposeDate
import be.sigmadelta.calpose.model.CalposeProperties
import be.sigmadelta.calpose.model.CalposeWidgets
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.YearMonth

const val WEIGHT_7DAY_WEEK = 1 / 7f

@ExperimentalCoroutinesApi
@Composable
fun Calpose(
    monthFlow: StateFlow<YearMonth>,
    actions: CalposeActions,
    widgets: CalposeWidgets,
    properties: CalposeProperties = CalposeProperties()
) {
    val month by monthFlow.collectAsState()

    val todayMonth = YearMonth.now()

    Crossfade(
        current = month,
        animation = properties.changeMonthAnimation
    ) {
        val monthWidget: @Composable () -> Unit = {
            Column(
                modifier = Modifier.dragGestureFilter(object: DragObserver {
                    override fun onStop(velocity: Offset) {
                        super.onStop(velocity)
                        if (velocity.x > properties.changeMonthSwipeTriggerVelocity) {
                            actions.onSwipedPreviousMonth()
                        } else if (velocity.x < -properties.changeMonthSwipeTriggerVelocity) {
                            actions.onSwipedNextMonth()
                        }
                    }
                })
            ) {
                CalposeHeader(it, todayMonth, actions, widgets)
                CalposeMonth(it, todayMonth, actions, widgets)
            }
        }

        widgets.monthContainer?.let { it(monthWidget) } ?: monthWidget()
    }

}

@Composable
fun CalposeHeader(
    month: YearMonth,
    todayMonth: YearMonth,
    actions: CalposeActions,
    widgets: CalposeWidgets
) {
    val header: @Composable () -> Unit = {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            widgets.header(month, todayMonth, actions)
            widgets.headerDayRow(DayOfWeek.values().toSet())
        }
    }

    widgets.headerContainer?.let { it(header) } ?: header()
}

@Composable
fun CalposeMonth(month: YearMonth, todayMonth: YearMonth, actions: CalposeActions, widgets: CalposeWidgets) {

    val firstDayOffset = month.atDay(1).dayOfWeek.ordinal
    val monthLength = month.lengthOfMonth()
    val priorMonthLength = month.minusMonths(1).lengthOfMonth()
    val lastDayCount = (monthLength + firstDayOffset) % 7
    val weekCount = (firstDayOffset + monthLength) / 7
    val today = SimpleDateFormat("dd").format(Calendar.getInstance().time).toInt()

    for (i in 0..weekCount) {
        CalposeWeek(
            startDayOffSet = firstDayOffset,
            endDayCount = lastDayCount,
            monthWeekNumber = i,
            weekCount = weekCount,
            priorMonthLength = priorMonthLength,
            today = today,
            month = month,
            todayMonth = todayMonth,
            widgets = widgets,
            actions = actions
        )
    }
}

@Composable
fun CalposeWeek(
    startDayOffSet: Int,
    endDayCount: Int,
    monthWeekNumber: Int,
    weekCount: Int,
    priorMonthLength: Int,
    today: Int,
    month: YearMonth,
    todayMonth: YearMonth,
    widgets: CalposeWidgets,
    actions: CalposeActions
) {
    Row {
        if (monthWeekNumber == 0) {
            for (i in 0 until startDayOffSet) {
                widgets.priorMonthDay(
                    this,
                    (priorMonthLength - (startDayOffSet - i - 1)).toString()
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
                CalposeDate(day, month),
                CalposeDate(today, todayMonth),
                actions
            )
        }

        if (monthWeekNumber == weekCount && endDayCount > 0) {
            for (i in 0 until (7 - endDayCount)) {
                widgets.nextMonthDay(this, (i + 1).toString())
            }
        }
    }
}

