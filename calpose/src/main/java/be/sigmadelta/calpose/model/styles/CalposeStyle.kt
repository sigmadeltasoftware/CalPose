package be.sigmadelta.calpose.model.styles

data class CalposeStyle(
    val headerStyle: CalposeHeaderStyle = CalposeHeaderStyle(),
    val headerDayListStyle: HeaderDayRowStyle = HeaderDayRowStyle()
)