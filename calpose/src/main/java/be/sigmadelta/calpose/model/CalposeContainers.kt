package be.sigmadelta.calpose.model

import androidx.compose.runtime.Composable

/**
 *
 * @property titleContainer Widget which can define a container layout for title. Useful if you want to
 *                           add animation for title changing. Used only in widget-free Calpose.
 *
 * @property headerContainer Widget which can define a container layout for the *header* & *headerDayRow*.
 *                           Useful if you want to place the header inside of a *Card* f.e.
 *
 * @property monthContainer Widget which can define a container layout for the month overview.
 *                          Can be used to change the background color, change animation of the
 *                          month overview
 *
 * @property weekContainer Widget which can define a container layout for the week. Can be
 *                          used to change the background color of the week or make dividers between them.
 */

data class CalposeContainers(
    val titleContainer: @Composable (@Composable () -> Unit) -> Unit = { it() },
    val headerContainer: @Composable (@Composable () -> Unit) -> Unit = { it() },
    val monthContainer: @Composable (@Composable () -> Unit) -> Unit = { it() },
    val weekContainer: @Composable (@Composable () -> Unit) -> Unit = { it() }
)