package be.sigmadelta.calpose.model.styles

import be.sigmadelta.calpose.model.CalposeDayStyleProvider

data class CalposeStyle(
    val headerStyle: CalposeHeaderStyle = CalposeHeaderStyle(),
    val headerDayListStyle: HeaderDayRowStyle = HeaderDayRowStyle(),
    val dayStyleProvider: CalposeDayStyleProvider = CalposeDayStyleProvider()
)