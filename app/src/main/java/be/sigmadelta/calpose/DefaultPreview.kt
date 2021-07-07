package be.sigmadelta.calpose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.model.CalposeWidgets
import be.sigmadelta.calpose.util.lightGrey
import be.sigmadelta.calpose.util.primaryAccent
import be.sigmadelta.calpose.widgets.DefaultDay
import be.sigmadelta.calpose.widgets.DefaultHeader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.threeten.bp.YearMonth

@SuppressLint("NewApi")
@ExperimentalCoroutinesApi
@Preview("DefaultPreview")
@Composable
fun DefaultPreview() {

    val monthFlow = MutableStateFlow(YearMonth.now())
    DefaultCalendar(monthFlow)
}

@SuppressLint("NewApi")
@ExperimentalCoroutinesApi
@Composable
fun DefaultCalendar(monthFlow: MutableStateFlow<YearMonth>) {

    Calpose(
        month = monthFlow.collectAsState().value,

        actions = CalposeActions(
            onClickedPreviousMonth = { monthFlow.value = monthFlow.value.minusMonths(1) },
            onClickedNextMonth = { monthFlow.value = monthFlow.value.plusMonths(1) }
        ),

        widgets = CalposeWidgets(
            header = { month, todayMonth, actions ->
                DefaultHeader(month, todayMonth, actions)
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
                    Column(
                        modifier = Modifier.weight(WEIGHT_7DAY_WEEK),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color(primaryAccent))
                        ) {
                            widget()
                        }
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
            headerContainer = { header ->
                Card {
                    header()
                }
            },
        )
    )
}