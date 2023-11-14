package plugins

import dependencies.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = AppConfig.compileSdkVersion
    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        testInstrumentationRunner = AppConfig.testRunner
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = false
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtensionVersion
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    addDiModule()
    addDomainModule()
    addNavigationModule()
    addCommonModule()
//    addUiComponentModule()
//
//    //addAndroidComposeDependencies()
    addAndroidUiDependencies()
    addAndroLifeCycleDependencies()
    addCoroutinesAndroidDependencies()
//    //addCoilImageLoadingDependencies()
    addHiltDependencies()
    addAndroidTestsDependencies()
    addNavigationDependencies()
    addGlideDependencies()
}

kapt {
    correctErrorTypes = true
}