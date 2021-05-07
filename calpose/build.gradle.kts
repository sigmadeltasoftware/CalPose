plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig{
        minSdkVersion(Versions.minSdk)
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    buildFeatures {
        compose = true
    }

    // Needed to enforce androidx.core:core version (1.5.0-alpha3) for DisplayInsets.kt
    configurations.all {
        resolutionStrategy.force(Android.core)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false // IMPORTANT BIT else you release aar will have no classes
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}


val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.getByName("main").kotlin.srcDirs)
}

afterEvaluate {
    publishing {
        publications {
            val release by publications.registering(MavenPublication::class) {
                from(components["release"])
                artifact(sourcesJar.get())
                artifactId = "calpose"
                groupId = CalposeProps.githubGroupId
                version = CalposeProps.versionName
            }
        }
    }
}

dependencies {
    coreLibraryDesugaring(Android.desugar_jdk)
    api(Compose.ui)
    api(Compose.uiGraphics)
    api(Compose.uiTooling)
    api(Compose.foundationLayout)
    api(Compose.material)
    api(Compose.runtimeLiveData)
    api(Compose.compiler)
}
