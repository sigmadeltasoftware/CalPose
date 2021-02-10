package be.sigmadelta.calpose

import android.widget.ImageView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.ui.tooling.preview.Preview
import be.sigmadelta.calpose.model.CalposeActions
import be.sigmadelta.calpose.model.CalposeWidgets
import be.sigmadelta.calpose.util.comicSans
import be.sigmadelta.calpose.widgets.DefaultDay
import com.bumptech.glide.Glide
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.DayOfWeek
import java.time.Month
import java.time.YearMonth

@ExperimentalCoroutinesApi
@Preview
@Composable
fun GeoCitiesPreview() {

    val monthFlow = MutableStateFlow(YearMonth.now())
    GeoCitiesCalendar(monthFlow)
}

@ExperimentalCoroutinesApi
@Composable
fun GeoCitiesCalendar(
    monthFlow: MutableStateFlow<YearMonth>,
) {
    val ctx = AmbientContext.current
    val maxSize = 50.dp

    Column {
        Calpose(
            month = monthFlow.collectAsState().value,

            actions = CalposeActions(
                onClickedPreviousMonth = { monthFlow.value = monthFlow.value.minusMonths(1) },
                onClickedNextMonth = { monthFlow.value = monthFlow.value.plusMonths(1) },
            ),

            widgets = CalposeWidgets(
                header = { month, todayMonth, actions ->
                    Box(contentAlignment = Alignment.Center) {
                        val url = when (month.month) {
                            Month.JANUARY -> "https://web.archive.org/web/20020406061157/http://geocities.com:80/christmasisgospel/images/aff_468x060_016.gif"
                            Month.DECEMBER -> "https://web.archive.org/web/20091027183234/http://geocities.com/ladysylviaann/sylvia/xmas/christmas2001top.gif"
                            else -> "https://web.archive.org/web/20090820060518/http://geocities.com/SunsetStrip/Frontrow/8467/bigfire.gif"
                        }
                        AndroidView(viewBlock = {
                            val img = ImageView(it)
                            Glide.with(ctx)
                                .asGif()
                                .load(url)
                                .into(img)
                            img
                        }, Modifier.fillMaxWidth())

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { actions.onClickedPreviousMonth() },
                                modifier = Modifier.padding(start = 16.dp)
                            ) {
                                Icon(
                                    imageVector = vectorResource(id = R.drawable.ic_left),
                                    tint = Color.White,
                                    contentDescription = "Left"
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = "${
                                    month.month.name.toLowerCase().capitalize()
                                } ${month.year}",
                                modifier = Modifier.padding(vertical = 16.dp),
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 32.sp,
                                    fontFamily = comicSans
                                )
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            IconButton(
                                onClick = { actions.onClickedNextMonth() },
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                Icon(
                                    imageVector = vectorResource(id = R.drawable.ic_right),
                                    tint = Color.White,
                                    contentDescription = "Right"
                                )
                            }
                        }
                    }
                },
                headerDayRow = { headerDayList ->
                    Row(
                        modifier = Modifier.fillMaxWidth(1f).padding(bottom = 16.dp),
                    ) {
                        headerDayList.forEach {
                            val url = when (it) {
                                DayOfWeek.MONDAY -> "https://web.archive.org/web/20031025192545/http://www.geocities.com:80/losalexspirop/a/m-color.gif"
                                DayOfWeek.TUESDAY -> "https://web.archive.org/web/20091027054136/http://geocities.com/bean3904/letters/t.gif"
                                DayOfWeek.WEDNESDAY -> "https://web.archive.org/web/20090830010049/http://geocities.com/SouthBeach/Balcony/6072/stuff/w_blk.gif"
                                DayOfWeek.THURSDAY -> "https://web.archive.org/web/20091027001500/http://mx.geocities.com/grupo_horizontes/images/t.gif"
                                DayOfWeek.FRIDAY -> "https://web.archive.org/web/20091026202953/http://es.geocities.com/frikilandiagiga/F.gif"
                                DayOfWeek.SATURDAY -> "https://web.archive.org/web/20091027000657/http://br.geocities.com/raridadesvideo2/images/s_estrel.gif"
                                DayOfWeek.SUNDAY -> "https://web.archive.org/web/20091027001442/http://mx.geocities.com/grupo_horizontes/images/s.gif"
                            }
                            AndroidView(viewBlock = {
                                val img = ImageView(it)
                                Glide.with(ctx)
                                    .asGif()
                                    .load(url)
                                    .into(img)
                                img
                            }, Modifier.weight(WEIGHT_7DAY_WEEK).size(maxSize))
                        }
                    }
                },
                day = { dayDate, todayDate ->
                    val url = if (dayDate == todayDate) "https://web.archive.org/web/20091027085139im_/http://www.geocities.com/cmarin17/surfcpu.gif" else
                        when (dayDate.dayOfWeek) {
                        DayOfWeek.MONDAY -> "https://web.archive.org/web/20091027081313/http://geocities.com/thesupervixens/atworkanim.gif"
                        DayOfWeek.TUESDAY -> "https://web.archive.org/web/20090829024314im_/http://geocities.com/okitsugu/underconstruction.gif"
                        DayOfWeek.WEDNESDAY -> "https://web.archive.org/web/20090902172800/http://geocities.com/shadysrest/spider-man.gif"
                        DayOfWeek.THURSDAY -> "https://web.archive.org/web/20090726011555/http://it.geocities.com/eru_dei_silvani/images/stop.gif"
                        DayOfWeek.FRIDAY -> "https://web.archive.org/web/20091027011959/http://www.geocities.com/SloRAVEnija/Rest/Rave.gif"
                        DayOfWeek.SATURDAY -> "https://web.archive.org/web/20091023114223im_/http://geocities.com/Colosseum/Midfield/1874/dancing_baby.gif"
                        DayOfWeek.SUNDAY -> "https://web.archive.org/web/20091027064945/http://hk.geocities.com/yeahsleepyeah/aarest.gif"
                    }
                    AndroidView(viewBlock = {
                        val img = ImageView(it)
                        Glide.with(ctx)
                            .asGif()
                            .load(url)
                            .into(img)
                        img
                    }, Modifier.weight(WEIGHT_7DAY_WEEK).size(maxSize))
                },

                priorMonthDay = {
                    AndroidView(viewBlock = {
                        val img = ImageView(it)
                        Glide.with(ctx)
                            .asGif()
                            .load("https://web.archive.org/web/20070302160207/http://geocities.com/abatezero/images/stop.gif")
                            .into(img)
                        img
                    }, Modifier.weight(WEIGHT_7DAY_WEEK).size(maxSize))
                },
            )
        )
        AndroidView(viewBlock = {
            val img = ImageView(it)
            Glide.with(ctx)
                .asGif()
                .load("https://cdn.mainstreethost.com/wp-content/uploads/2019/06/3dText.gif")
                .into(img)
            img
        }, Modifier.fillMaxWidth().padding(top = 16.dp))
    }
}