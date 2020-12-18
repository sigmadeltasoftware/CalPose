package be.sigmadelta.calpose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.model.CalposeWidgets
import be.sigmadelta.calpose.util.lightGrey
import be.sigmadelta.calpose.util.primaryAccent
import be.sigmadelta.calpose.widgets.DefaultDay
import be.sigmadelta.calpose.widgets.DefaultHeader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.YearMonth

@ExperimentalCoroutinesApi
@Preview
@Composable
fun defaultPreview() {

    val monthFlow = MutableStateFlow(YearMonth.now())

    Calpose(
        monthFlow = monthFlow,

        actions = CalposeActions(
            onClickedPreviousMonth = { monthFlow.value = monthFlow.value.minusMonths(1) },
            onClickedNextMonth = { monthFlow.value = monthFlow.value.plusMonths(1) }
        ),

        widgets = CalposeWidgets(
            header = { month, todayMonth, actions ->
                DefaultHeader(month, todayMonth, actions)
            },
            headerDay = { dayOfWeek ->
                DefaultDay(
                    text = dayOfWeek.name.first().toString(),
                    modifier = Modifier.weight(WEIGHT_7DAY_WEEK),
                    style = TextStyle(color = Color.Gray)
                )
            },
            day = { day, today ->
                val isToday = day == today
                val dayHasPassed = day.day < today.day
                val isCurrentMonth = day.month == today.month

                val widget: @Composable () -> Unit = {
                    val weight = if (isToday) 1f else WEIGHT_7DAY_WEEK
                    DefaultDay(
                        text = day.day.toString(),
                        modifier = Modifier.padding(4.dp).weight(weight).fillMaxWidth(),
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
                    Column(
                        modifier = Modifier.weight(WEIGHT_7DAY_WEEK),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.preferredSize(28.dp).clip(CircleShape)
                                .background(Color(primaryAccent))
                        ) {
                            widget()
                        }
                    }
                } else widget()
            },
            priorMonthDay = { day ->
                DefaultDay(
                    text = day,
                    style = TextStyle(color = Color(lightGrey)),
                    modifier = Modifier.padding(4.dp).fillMaxWidth().weight(WEIGHT_7DAY_WEEK)
                )
            },
            headerContainer = { header ->
                Card {
                    header()
                }
            },
        )
    )
}