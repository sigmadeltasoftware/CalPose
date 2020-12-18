package be.sigmadelta.calpose.model

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import java.time.DayOfWeek
import java.time.YearMonth

data class CalposeWidgets(
    val header: @Composable (YearMonth, YearMonth, CalposeActions) -> Unit,
    val headerDay: @Composable RowScope.(DayOfWeek) -> Unit,
    val day: @Composable RowScope.(day: CalposeDate, today: CalposeDate) -> Unit,
    val priorMonthDay: @Composable RowScope.(day: String) -> Unit,
    val nextMonthDay: @Composable RowScope.(day: String) -> Unit = priorMonthDay,
    val headerContainer: @Composable ((@Composable () -> Unit) -> Unit)? = null,
    val monthContainer: @Composable ((@Composable () -> Unit) -> Unit)? = null,
)