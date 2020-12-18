package be.sigmadelta.calpose.model

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween

/**
 * Describes various properties used within Calpose
 *
 * @property changeMonthAnimation Set the animation type and properties when changing month
 *
 * @property changeMonthSwipeTriggerVelocity Define the velocity necessary to change month when swiping
 *
 */

data class CalposeProperties (
    val changeMonthAnimation: AnimationSpec<Float> = tween(durationMillis = 200),
    val changeMonthSwipeTriggerVelocity: Int = 300
)