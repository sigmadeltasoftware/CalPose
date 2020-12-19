object CalposeProps {
    const val applicationId = "be.sigmadelta.calpose"
    const val versionCode = 3
    const val versionName = "0.2.1"
    const val githubGroupId = "com.github.sigmadeltasoftware.calpose"
}

object Versions {
    const val gradleTools = "4.2.0-alpha16"
    const val compileSdk = 30
    const val targetSdk = compileSdk
    const val minSdk = 24
    const val buildTools = "$compileSdk.0.2"

    const val kotlin = "1.4.21"
    const val core = "1.5.0-alpha05"
    const val core_ktx = core
    const val compose = "1.0.0-alpha09"
    const val composeUiTooling = "1.0.0-alpha07"
    const val maven_gradle_plugin = "2.1"
}

object Compose {
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val uiTooling = "androidx.ui:ui-tooling:${Versions.composeUiTooling}"
    const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val runtimeLiveData =  "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val compiler = "androidx.compose.compiler:compiler:${Versions.compose}"
}

object Android {
    const val core = "androidx.core:core:${Versions.core_ktx}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
}
