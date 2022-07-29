import Lib.Android.ACCOMPANIST_SYSTEM_UI_CONTROLLER

plugins {
    id(BuildPlugins.ANDROID_APPLICATION_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
    id(BuildPlugins.KOTLIN_PARCELABLE_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.DAGGER_HILT)
}

android {
    compileSdk = (ProjectProperties.COMPILE_SDK)

    defaultConfig {
        applicationId = (ProjectProperties.APPLICATION_ID)
        minSdk = (ProjectProperties.MIN_SDK)
        targetSdk = (ProjectProperties.TARGET_SDK)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {

        getByName("debug") {
            keyAlias = "praxis-debug"
            keyPassword = "utherNiC"
            storeFile = file("keystore/praxis-debug.jks")
            storePassword = "uRgeSCIt"
        }

        create("release") {
            keyAlias = "praxis-release"
            keyPassword = "ITHOmptI"
            storeFile = file("keystore/praxis-release.jks")
            storePassword = "PoTHatHR"
        }
    }
    buildTypes {
        getByName("release") {
            isDebuggable = false
            versionNameSuffix = "-release"

            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-common.txt",
                "proguard-specific.txt"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            isDebuggable = true
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
        }
      create("benchmark") {
        signingConfig = signingConfigs.getByName("debug")
        matchingFallbacks += listOf("release")
        isDebuggable = false
      }
    }

    buildFeatures {
        dataBinding = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Lib.Android.COMPOSE_COMPILER_VERSION
    }
    packagingOptions {
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("LICENSE.txt")
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// Required for annotation processing plugins like Dagger
kapt {
    generateStubs = true
    correctErrorTypes = true
}

dependencies {
    implementation(project(":navigator"))
    implementation(project(":data"))
    implementation(project(":domain"))

    /* Android Designing and layout */
    implementation(Lib.Android.COMPOSE_LIVEDATA)
    implementation(Lib.Android.COMPOSE_NAVIGATION)
    implementation(Lib.Kotlin.KT_STD)
    implementation(Lib.Android.MATERIAL_DESIGN)
    implementation(Lib.Android.CONSTRAINT_LAYOUT_COMPOSE)
    implementation(Lib.Android.ACCOMPANIST_INSETS)
    implementation(Lib.Android.ACCOMPANIST_INSETS_UI)
    implementation(ACCOMPANIST_SYSTEM_UI_CONTROLLER)
    implementation(Lib.Android.ACCOMPANIST_FLOW_LAYOUTS)
    implementation(Lib.Paging.PAGING_3)
    implementation(Lib.Paging.PAGING_COMPOSE)

    implementation(Lib.Android.APP_COMPAT)

    implementation(Lib.Kotlin.KTX_CORE)

    /* Image Loading */
    implementation(Lib.Android.COIL_COMPOSE)
    implementation(Lib.Android.ACCOMPANIST_COIL)

    /*DI*/
    implementation(Lib.Di.hiltAndroid)
    implementation(Lib.Di.hiltNavigationCompose)
    implementation(Lib.Android.COMPOSE_TOOLING_PREVIEW)
    debugImplementation(Lib.Android.COMPOSE_TOOLING)

    implementation(Lib.Android.PROFILE_INSTALLER)

    kapt(Lib.Di.hiltCompiler)
    kapt(Lib.Di.hiltAndroidCompiler)

    /* Logger */
    implementation(Lib.Logger.TIMBER)
    /* Async */
    implementation(Lib.Async.COROUTINES)
    implementation(Lib.Async.COROUTINES_ANDROID)

    /* Room */
    implementation(Lib.Room.roomRuntime)
    kapt(Lib.Room.roomCompiler)
    implementation(Lib.Room.roomKtx)
    implementation(Lib.Room.roomPaging)

    implementation(Lib.Lottie.lottieCompose)

    /*Testing*/
    testImplementation(TestLib.JUNIT)
    testImplementation(TestLib.CORE_TEST)
    testImplementation(TestLib.ANDROID_JUNIT)
    testImplementation(TestLib.ARCH_CORE)
    testImplementation(TestLib.MOCK_WEB_SERVER)
    testImplementation(TestLib.ROBO_ELECTRIC)
    testImplementation(TestLib.COROUTINES)
    testImplementation(TestLib.MOCKK)
    androidTestImplementation(Lib.Android.COMPOSE_JUNIT)
    debugImplementation(Lib.Android.COMPOSE_TEST_MANIFEST)
}
