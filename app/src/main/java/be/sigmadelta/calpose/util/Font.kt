package be.sigmadelta.calpose.util

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import be.sigmadelta.calpose.demo.R

val comicSans = fontFamily(
    font(R.font.comicsans),
)
val typography = Typography(defaultFontFamily = comicSans)