package be.sigmadelta.calpose

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.model.CalposeDate
import be.sigmadelta.calpose.model.CalposeWidgets
import be.sigmadelta.calpose.util.lightGrey
import be.sigmadelta.calpose.util.primaryAccent
import be.sigmadelta.calpose.widgets.DefaultDay
import be.sigmadelta.calpose.widgets.MaterialHeader
import org.threeten.bp.DayOfWeek
import org.threeten.bp.YearMonth
import soup.compose.material.motion.Axis
import soup.compose.material.motion.MaterialSharedAxis

@Preview("MaterialPreview")
@Composable
fun MaterialPreview() {
    var month by remember { mutableStateOf(YearMonth.now()) }
    var selectionSet by remember { mutableStateOf(setOf<CalposeDate>()) }

    MaterialCalendar(
        month = month,
        selectionSet = selectionSet,
        actions = CalposeActions(
            onClickedPreviousMonth = { month = month.minusMonths(1) },
            onClickedNextMonth = { month = month.plusMonths(1) },
        ),
        onSelected = { selectionSet = mutableSetOf(it).apply { addAll(selectionSet) } }
    )
}

@Composable
fun MaterialCalendar(
    month: YearMonth,
    selectionSet: Set<CalposeDate>,
    actions: CalposeActions,
    onSelected: (CalposeDate) -> Unit,
) {
    Calpose(
        month = month,
        actions = actions,

        widgets = CalposeWidgets(
            header = { month, todayMonth, actions ->
                MaterialHeader(month, todayMonth, actions, Color(primaryAccent))
            },
            headerDayRow = { headerDayList -> HeaderDayRow(headerDayList = headerDayList) },
            day = { dayDate, todayDate ->
                Day(
                    dayDate = dayDate,
                    selectionSet = selectionSet,
                    onSelected = onSelected
                )
            },
            priorMonthDay = { dayDate -> PriorMonthDay(dayDate = dayDate) },
        )
    )
}

@Preview("MaterialMotionPreview")
@Composable
fun MaterialMotionPreview() {
    var month by remember { mutableStateOf(YearMonth.now()) }
    var selectionSet by remember { mutableStateOf(setOf<CalposeDate>()) }
    var forward by remember { mutableStateOf(true) }

    MaterialCalendarMotion(
        month = month,
        selectionSet = selectionSet,
        forward = forward,
        actions = CalposeActions(
            onClickedPreviousMonth = {
                forward = false
                month = month.minusMonths(1)
            },
            onClickedNextMonth = {
                forward = true
                month = month.plusMonths(1)
            },
        ),
        onSelected = { selectionSet = mutableSetOf(it).apply { addAll(selectionSet) } },
    )
}

@Composable
fun MaterialCalendarMotion(
    month: YearMonth,
    selectionSet: Set<CalposeDate>,
    actions: CalposeActions,
    onSelected: (CalposeDate) -> Unit,
    forward: Boolean
) {
    CalposeStatic(
        modifier = Modifier.animateContentSize(),
        month = month,
        actions = actions,
        widgets = CalposeWidgets(
            header = { month, todayMonth, actions ->
                MaterialHeader(
                    month = month,
                    todayMonth = todayMonth,
                    actions = actions,
                    backgroundColor = Color(primaryAccent),
                    titleContainer = {
                        MaterialSharedAxis(
                            targetState = month,
                            axis = Axis.X,
                            forward = forward
                        ) { _ ->
                            it()
                        }
                    }
                )
            },
            headerDayRow = { headerDayList -> HeaderDayRow(headerDayList = headerDayList) },
            day = { dayDate, _ ->
                Day(
                    dayDate = dayDate,
                    selectionSet = selectionSet,
                    onSelected = onSelected
                )
            },
            priorMonthDay = { dayDate -> PriorMonthDay(dayDate = dayDate) },
            monthContainer = {
                MaterialSharedAxis(
                    targetState = month,
                    axis = Axis.X,
                    forward = forward
                ) {
                    Column { it() }
                }
            }
        )
    )
}

@Composable
fun HeaderDayRow(
    headerDayList: Set<DayOfWeek>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(vertical = 8.dp),
    ) {
        headerDayList.forEach {
            DefaultDay(
                text = it.name.first().toString(),
                modifier = Modifier
                    .weight(WEIGHT_7DAY_WEEK)
                    .alpha(.6f),
                style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun RowScope.Day(
    dayDate: CalposeDate,
    selectionSet: Set<CalposeDate>,
    onSelected: (CalposeDate) -> Unit
) {
    val isSelected = selectionSet.contains(dayDate)
    val weight = if (isSelected) 1f else WEIGHT_7DAY_WEEK
    val bgColor = if (isSelected) Color(primaryAccent) else Color.Transparent

    val widget: @Composable () -> Unit = {
        DefaultDay(
            text = dayDate.day.toString(),
            modifier = Modifier
                .padding(4.dp)
                .weight(weight)
                .fillMaxWidth(),
            style = TextStyle(
                color = when {
                    isSelected -> Color.White
                    else -> Color.Black
                },
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        )
    }

    Column(
        modifier = Modifier.weight(WEIGHT_7DAY_WEEK),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Crossfade(targetState = bgColor) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .clickable(onClick = { onSelected(dayDate) })
                    .background(it)
            ) {
                widget()
            }
        }

    }
}

@Composable
fun RowScope.PriorMonthDay(
    dayDate: CalposeDate
) {
    DefaultDay(
        text = dayDate.day.toString(),
        style = TextStyle(color = Color(lightGrey)),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .weight(WEIGHT_7DAY_WEEK)
    )
}