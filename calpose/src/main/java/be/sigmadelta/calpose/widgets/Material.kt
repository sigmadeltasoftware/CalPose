package be.sigmadelta.calpose.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.R
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.model.styles.CalposeHeaderStyle
import org.threeten.bp.YearMonth

@Composable
fun MaterialHeader(
    modifier: Modifier = Modifier,
    month: YearMonth,
    todayMonth: YearMonth,
    actions: CalposeActions,
    style: CalposeHeaderStyle = CalposeHeaderStyle()
) {
    val isCurrentMonth = todayMonth == month
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DefaultIconButton(
            modifier = Modifier.padding(start = 16.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_left),
            onClick = actions::onClickedPreviousMonth.get(),
            contentDescription = "Left",
            tint = style.iconsTint
        )

        Spacer(modifier = Modifier.weight(1f))

        style.titleStyle.container {
            DefaultMonthTitle(
                month = month,
                isCurrentMonth = isCurrentMonth,
                calposeTitleStyle = style.titleStyle
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        DefaultIconButton(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_right),
            onClick = actions::onClickedNextMonth.get(),
            contentDescription = "Right",
            tint = style.iconsTint
        )
    }
}