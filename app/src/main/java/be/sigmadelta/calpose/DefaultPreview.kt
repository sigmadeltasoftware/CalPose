package be.sigmadelta.calpose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.model.*
import be.sigmadelta.calpose.model.styles.CalposeDayStyle
import be.sigmadelta.calpose.model.styles.CalposeStyle
import be.sigmadelta.calpose.util.lightGrey
import be.sigmadelta.calpose.util.primaryAccent
import be.sigmadelta.calpose.widgets.DefaultDay
import be.sigmadelta.calpose.widgets.DefaultHeader
import be.sigmadelta.calpose.widgets.DefaultMarkerContainer
import org.threeten.bp.YearMonth

@Preview("DefaultPreview")
@Composable
fun DefaultPreview() {
    var month by remember { mutableStateOf(YearMonth.now()) }
    DefaultCalendar(
        month = month,
        actions = CalposeActions(
            onClickedPreviousMonth = { month = month.minusMonths(1) },
            onClickedNextMonth = { month = month.plusMonths(1) }
        )
    )
}

@Composable
fun DefaultCalendar(
    month: YearMonth,
    actions: CalposeActions
) {
    Calpose(
        month = month,
        actions = actions,
        widgets = CalposeWidgets(
            header = { month, todayMonth, actions ->
                DefaultHeader(
                    month = month,
                    todayMonth = todayMonth,
                    actions = actions
                )
            },
            headerDayRow = { headerDayList ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(bottom = 16.dp),
                ) {
                    headerDayList.forEach {
                        DefaultDay(
                            text = it.name.first().toString(),
                            modifier = Modifier.weight(WEIGHT_7DAY_WEEK),
                            style = TextStyle(color = Color.Gray)
                        )
                    }
                }
            },
            day = { dayDate, todayDate ->
                val isToday = dayDate == todayDate
                val dayHasPassed = dayDate.day < todayDate.day
                val isCurrentMonth = dayDate.month == todayDate.month

                val widget: @Composable () -> Unit = {
                    val weight = if (isToday) 1f else WEIGHT_7DAY_WEEK
                    DefaultDay(
                        text = dayDate.day.toString(),
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(weight)
                            .fillMaxWidth(),
                        style = TextStyle(
                            color = when {
                                isCurrentMonth && dayHasPassed -> Color.Gray
                                isToday -> Color.White
                                else -> Color.Black
                            },
                            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }

                if (isToday) {
                    DefaultMarkerContainer(
                        modifier = Modifier.weight(WEIGHT_7DAY_WEEK),
                        markerColor = Color(primaryAccent)
                    ) {
                        widget()
                    }
                } else widget()
            },
            priorMonthDay = { dayDate ->
                DefaultDay(
                    text = dayDate.day.toString(),
                    style = TextStyle(color = Color(lightGrey)),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .weight(WEIGHT_7DAY_WEEK)
                )
            },
            containers = CalposeContainers(
                headerContainer = { header ->
                    Card {
                        header()
                    }
                },
            )
        )
    )
}

@Preview("Widget-free")
@Composable
fun WidgetFree() {
    var month by remember { mutableStateOf(YearMonth.now()) }
    CalposeStatic(
        month = month,
        actions = CalposeActions(
            onClickedPreviousMonth = { month = month.minusMonths(1) },
            onClickedNextMonth = { month = month.plusMonths(1) }
        ),
        containers = CalposeContainers(
            headerContainer = {
                Card {
                    it()
                }
            }
        )
    )
}

@Preview("Select month widget-free")
@Composable
fun SelectMonthWidgetFree() {
    var month by remember { mutableStateOf(YearMonth.now()) }
    var selected by remember { mutableStateOf<CalposeDate?>(null) }

    Calpose(
        style = CalposeStyle(
            dayStyleProvider = CalposeDayStyleProvider(
                day = { dayDate: CalposeDate, todayDate: CalposeDate ->
                    val dayHasPassed = dayDate.day < todayDate.day
                    val isCurrentMonth = dayDate.month == todayDate.month
                    val isSelected = dayDate == selected

                    CalposeDayStyle(
                        textStyle = TextStyle(
                            color = when {
                                isCurrentMonth && dayHasPassed -> Color.Gray
                                isSelected -> Color.White
                                else -> Color.Black
                            },
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        ),
                        container = {
                            SelectableDayContainer(modifier = Modifier.clickable {
                                selected = dayDate
                            }) {
                                if (isSelected) {
                                    DefaultMarkerContainer(markerColor = Color(primaryAccent)) {
                                        it()
                                    }
                                } else {
                                    it()
                                }
                            }
                        }
                    )
                }
            )
        ),
        month = month,
        actions = CalposeActions(
            onClickedPreviousMonth = { month = month.minusMonths(1) },
            onClickedNextMonth = { month = month.plusMonths(1) }
        ),
        containers = CalposeContainers(
            headerContainer = {
                Card {
                    it()
                }
            }
        ),
    )
}

@Composable
fun SelectableDayContainer(
    modifier: Modifier,
    composable: @Composable () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        composable()
    }
}