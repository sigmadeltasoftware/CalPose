package be.sigmadelta.calpose.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.R
import be.sigmadelta.calpose.model.CalposeActions
import org.threeten.bp.YearMonth

@Composable
fun DefaultHeader(
    month: YearMonth,
    todayMonth: YearMonth,
    actions: CalposeActions,
    titleContainer: @Composable (@Composable () -> Unit) -> Unit = { it() }
) {
    Row {
        DefaultIconButton(
            modifier = Modifier.padding(start = 16.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_left),
            onClick = actions::onClickedPreviousMonth.get(),
            contentDescription = "Left"
        )

        Spacer(modifier = Modifier.weight(1f))

        titleContainer {
            DefaultMonthTitle(month = month, isCurrentMonth = todayMonth == month)
        }

        Spacer(modifier = Modifier.weight(1f))

        DefaultIconButton(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_right),
            onClick = actions::onClickedNextMonth.get(),
            contentDescription = "Right"
        )
    }
}

@Composable
fun DefaultDay(
    text: String,
    modifier: Modifier = Modifier.padding(4.dp),
    style: TextStyle = TextStyle()
) {
    Text(
        text,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = style
    )
}

@Composable
internal fun DefaultIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    contentDescription: String,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}
