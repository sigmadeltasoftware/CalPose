package be.sigmadelta.calpose.model

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween

class CalposeProperties {
    val changeMonthAnimation: AnimationSpec<Float> = tween(durationMillis = 200)
    val changeMonthSwipeTriggerVelocity = 300
}