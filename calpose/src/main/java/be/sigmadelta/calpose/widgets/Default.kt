package be.sigmadelta.calpose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import be.sigmadelta.calpose.R
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.model.styles.CalposeHeaderStyle
import org.threeten.bp.YearMonth

@Composable
fun DefaultHeader(
    modifier: Modifier = Modifier,
    month: YearMonth,
    todayMonth: YearMonth,
    actions: CalposeActions,
    headerStyle: CalposeHeaderStyle = CalposeHeaderStyle()
) {
    Row(modifier = modifier) {
        DefaultIconButton(
            modifier = Modifier.padding(start = 16.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_left),
            onClick = actions::onClickedPreviousMonth.get(),
            contentDescription = "Left",
            tint = headerStyle.iconsTint
        )

        Spacer(modifier = Modifier.weight(1f))

        headerStyle.titleStyle.container {
            DefaultMonthTitle(month = month, isCurrentMonth = todayMonth == month)
        }

        Spacer(modifier = Modifier.weight(1f))

        DefaultIconButton(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_right),
            onClick = actions::onClickedNextMonth.get(),
            contentDescription = "Right",
            tint = headerStyle.iconsTint
        )
    }
}

@Composable
fun DefaultMarkerContainer(
    modifier: Modifier = Modifier,
    markerColor: Color,
    block: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(markerColor)
        ) {
            block()
        }
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
