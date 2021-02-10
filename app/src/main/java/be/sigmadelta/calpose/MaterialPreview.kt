package be.sigmadelta.calpose

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
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
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.model.CalposeDate
import be.sigmadelta.calpose.model.CalposeWidgets
import be.sigmadelta.calpose.util.lightGrey
import be.sigmadelta.calpose.util.primaryAccent
import be.sigmadelta.calpose.widgets.DefaultDay
import be.sigmadelta.calpose.widgets.MaterialHeader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.time.YearMonth

@ExperimentalCoroutinesApi
@Preview
@Composable
fun MaterialPreview() {

    val monthFlow = MutableStateFlow(YearMonth.now())
    val selectionSet = MutableStateFlow(setOf<CalposeDate>())

    MaterialCalendar(monthFlow, selectionSet)
}

@ExperimentalCoroutinesApi
@Composable
fun MaterialCalendar(
    monthFlow: MutableStateFlow<YearMonth>,
    selectionSet: MutableStateFlow<Set<CalposeDate>>
) {

    val selections = selectionSet.collectAsState().value

    Column {
        Calpose(
            month = monthFlow.collectAsState().value,

            actions = CalposeActions(
                onClickedPreviousMonth = { monthFlow.value = monthFlow.value.minusMonths(1) },
                onClickedNextMonth = { monthFlow.value = monthFlow.value.plusMonths(1) },
            ),

            widgets = CalposeWidgets(
                header = { month, todayMonth, actions ->
                    MaterialHeader(month, todayMonth, actions, Color(primaryAccent))
                },
                headerDayRow = { headerDayList ->
                    Row(
                        modifier = Modifier.fillMaxWidth(1f)
                            .padding(vertical = 8.dp),
                    ) {
                        headerDayList.forEach {
                            DefaultDay(
                                text = it.name.first().toString(),
                                modifier = Modifier.weight(WEIGHT_7DAY_WEEK).alpha(.6f),
                                style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                },
                day = { dayDate, todayDate ->
                    val isSelected = selections.contains(dayDate)
                    val onSelected = {
                        selectionSet.value = mutableSetOf(dayDate).apply {
                            addAll(selectionSet.value)
                        }
                    }
                    val weight = if (isSelected) 1f else WEIGHT_7DAY_WEEK
                    val bgColor = if (isSelected) Color(primaryAccent) else Color.Transparent

                    val widget: @Composable () -> Unit = {
                        DefaultDay(
                            text = dayDate.day.toString(),
                            modifier = Modifier.padding(4.dp).weight(weight).fillMaxWidth(),
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

                        Crossfade(current = bgColor) {
                            Box(
                                modifier = Modifier.preferredSize(28.dp).clip(CircleShape)
                                    .clickable(onClick = onSelected)
                                    .background(it)
                            ) {
                                widget()
                            }
                        }

                    }
                },
                priorMonthDay = { dayDate ->
                    DefaultDay(
                        text = dayDate.day.toString(),
                        style = TextStyle(color = Color(lightGrey)),
                        modifier = Modifier.padding(4.dp).fillMaxWidth().weight(WEIGHT_7DAY_WEEK)
                    )
                },
            )
        )
    }
}