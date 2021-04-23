plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        applicationId = CalposeProps.applicationId
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        vectorDrawables.useSupportLibrary = true

        versionCode = CalposeProps.versionCode
        versionName = CalposeProps.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
}


dependencies {
    implementation(project(":calpose"))
    // For terrible animated GIFs
    implementation("com.github.bumptech.glide:glide:4.12.0")
}
