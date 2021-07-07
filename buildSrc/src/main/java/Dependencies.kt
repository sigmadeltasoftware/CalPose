object CalposeProps {
    const val applicationId = "be.sigmadelta.calpose"
    const val versionCode = 8
    const val versionName = "1.0.4"
    const val githubGroupId = "com.github.sigmadeltasoftware.calpose"
}

object Versions {
    const val gradleTools = "7.0.0-alpha15"
    const val compileSdk = 30
    const val targetSdk = compileSdk
    const val minSdk = 23
    const val buildTools = "$compileSdk.0.2"

    const val kotlin = "1.5.10"
    const val core = "1.5.0-rc02"
    const val compose = "1.0.0-beta09"
    const val maven_gradle_plugin = "2.1"
    const val desugar_jdk = "1.1.5"
    const val threeten = "1.5.1"
}

object Compose {
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val runtimeLiveData =  "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val compiler = "androidx.compose.compiler:compiler:${Versions.compose}"
}

object Android {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val desugar_jdk = "com.android.tools:desugar_jdk_libs:${Versions.desugar_jdk}"
    const val threeten = "org.threeten:threetenbp:${Versions.threeten}"
}

