package be.sigmadelta.calpose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.model.*
import be.sigmadelta.calpose.model.styles.CalposeStyle
import be.sigmadelta.calpose.widgets.DefaultDay
import be.sigmadelta.calpose.widgets.DefaultHeader
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
    containers: CalposeContainers = CalposeContainers(),
    conditions: Set<CalposeCondition> = setOf(
        PriorOrNextMonthDayCondition(),
        TodayCondition(markerColor = MaterialTheme.colors.primary),
        IsCurrentMonthAndDayHasPassedCondition(),
        ElseCondition(),
    ),
    style: CalposeStyle = CalposeStyle(),
    properties: CalposeProperties = CalposeProperties()
) {
    Crossfade(
        targetState = month,
        animationSpec = properties.changeMonthAnimation
    ) {
        CalposeStatic(
            modifier = modifier,
            month = it,
            actions = actions,
            containers = containers,
            conditions = conditions,
            style = style,
            properties = properties
        )
    }
}

@Composable
fun CalposeStatic(
    modifier: Modifier = Modifier,
    month: YearMonth,
    actions: CalposeActions,
    containers: CalposeContainers = CalposeContainers(),
    conditions: Set<CalposeCondition> = setOf(
        PriorOrNextMonthDayCondition(),
        TodayCondition(markerColor = MaterialTheme.colors.primary),
        IsCurrentMonthAndDayHasPassedCondition(),
        ElseCondition(),

        ),
    style: CalposeStyle = CalposeStyle(),
    properties: CalposeProperties = CalposeProperties()
) {
    CalposeStatic(
        modifier = modifier,
        month = month,
        actions = actions,
        properties = properties,
        widgets = CalposeWidgets(
            header = { month, todayMonth, actions ->
                DefaultHeader(
                    month = month,
                    todayMonth = todayMonth,
                    actions = actions,
                    headerStyle = style.headerStyle
                )
            },
            headerDayRow = { headerDayList ->
                style.headerDayListStyle.container {
                    Row {
                        headerDayList.forEach {
                            DefaultDay(
                                text = style.headerDayListStyle.textProvider(it),
                                modifier = style.headerDayListStyle.modifier(this),
                                style = style.headerDayListStyle.textStyle
                            )
                        }
                    }
                }
            },
            day = { dayDate, todayDate ->
                DayWeightContainer {
                    conditionsCheck(conditions, this, dayDate, todayDate).invoke()
                }
            },
            priorMonthDay = { dayDate ->
                DayWeightContainer {
                    conditionsCheck(conditions, this, dayDate, null).invoke()
                }
            },
            containers = containers
        )
    )
}

@Composable
internal fun RowScope.DayWeightContainer(
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.weight(WEIGHT_7DAY_WEEK),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
        ) {
            content()
        }
    }

}


private fun conditionsCheck(
    conditions: Set<CalposeCondition>,
    rowScope: RowScope,
    dayDate: CalposeDate,
    todayDate: CalposeDate?
): @Composable () -> Unit {
    conditions.forEach {
        if (it.block(dayDate, todayDate)) {
            it.dayDate = dayDate
            return {
                it.style.container(
                    {
                        DefaultDay(
                            text = it.style.textProvider(dayDate),
                            modifier = it.style.modifier(rowScope),
                            style = it.style.textStyle
                        )
                    },
                    dayDate
                )
            }
        }
    }
    return {}
}

@Composable
fun Calpose(
    modifier: Modifier = Modifier,
    month: YearMonth,
    actions: CalposeActions,
    widgets: CalposeWidgets,
    properties: CalposeProperties = CalposeProperties()
) {
    Crossfade(
        targetState = month,
        animationSpec = properties.changeMonthAnimation
    ) {
        CalposeStatic(
            modifier = modifier,
            month = it,
            actions = actions,
            widgets = widgets
        )
    }

}

@Composable
fun CalposeStatic(
    modifier: Modifier = Modifier,
    month: YearMonth,
    actions: CalposeActions,
    widgets: CalposeWidgets,
    properties: CalposeProperties = CalposeProperties()
) {
    val todayMonth = remember { YearMonth.now() }

    Column(
        modifier = modifier.draggable(
            orientation = Orientation.Horizontal,
            state = rememberDraggableState {},
            onDragStopped = { velocity ->
                if (velocity > properties.changeMonthSwipeTriggerVelocity) {
                    actions.onSwipedPreviousMonth()
                } else if (velocity < -properties.changeMonthSwipeTriggerVelocity) {
                    actions.onSwipedNextMonth()
                }
            })
    ) {
        CalposeHeader(month, todayMonth, actions, widgets)
        widgets.containers.monthContainer { CalposeMonth(month, todayMonth, widgets) }
    }
}

@Composable
fun CalposeHeader(
    month: YearMonth,
    todayMonth: YearMonth,
    actions: CalposeActions,
    widgets: CalposeWidgets
) {
    widgets.containers.headerContainer {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            widgets.header(month, todayMonth, actions)
            widgets.headerDayRow(DayOfWeek.values().toSet())
        }
    }
}

@Composable
fun CalposeMonth(month: YearMonth, todayMonth: YearMonth, widgets: CalposeWidgets) {

    val firstDayOffset = month.atDay(1).dayOfWeek.ordinal
    val monthLength = month.lengthOfMonth()
    val priorMonthLength = month.minusMonths(1).lengthOfMonth()
    val lastDayCount = (monthLength + firstDayOffset) % 7
    val weekCount = (firstDayOffset + monthLength) / 7
    val today =
        remember { SimpleDateFormat("dd").format(Date(System.currentTimeMillis())).toInt() }

    for (i in 0..weekCount) {
        widgets.containers.weekContainer {
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
                widgets = widgets
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
    widgets: CalposeWidgets
) {
    Row {
        if (monthWeekNumber == 0) {
            for (i in 0 until startDayOffSet) {
                val priorDay = (priorMonthLength - (startDayOffSet - i - 1))
                widgets.priorMonthDay(
                    this,
                    CalposeDate(
                        priorDay,
                        month.minusMonths(1).atDay(priorDay).dayOfWeek,
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
            val day =
                if (monthWeekNumber == 0) i else (i + (7 * monthWeekNumber) - startDayOffSet)
            widgets.day(
                this,
                CalposeDate(day, DayOfWeek.of(i), month),
                today
            )
        }

        if (monthWeekNumber == weekCount && endDayCount > 0) {
            for (i in 0 until (7 - endDayCount)) {
                val nextMonthDay = i + 1
                widgets.nextMonthDay(
                    this, CalposeDate(
                        nextMonthDay,
                        month.plusMonths(1).atDay(nextMonthDay).dayOfWeek,
                        month.plusMonths(1)
                    )
                )
            }
        }
    }
}


