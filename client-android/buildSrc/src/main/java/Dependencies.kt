object Version {
    object ClassPath {
        const val navVersion = "2.3.5"
        const val hiltVersion = "2.28-alpha"
        const val gradleVersion = "7.0.2"
        const val gmsVersion = "4.3.8"
        const val crashlyticsVersion = "2.7.1"
    }

    const val fragmentVersion = "1.3.0-beta01"
    const val activityVersion = "1.2.0-beta01"

    const val kotlinVersion = "1.5.0"
    const val coreKtxVersion = "1.3.2"
    const val playCoreKtxVersion = "1.8.1"
    const val appCompatVersion = "1.2.0"
    const val materialDesignVersion = "1.3.0-beta01"
    const val constraintVersion = "2.0.4"

    const val jUnitVersion = "4.13.1"
    const val jUnitTestVersion = "1.1.2"
    const val espressoVersion = "3.3.0"

    const val lifecycleVersion = "2.2.0"
    const val navigationVersion = "2.3.0"
    const val pagingVersion = "3.1.0-rc01"
    const val roomVersion = "2.2.5"
    const val dataStoreVersion = "1.0.0"

    const val coroutinesVersion = "1.3.9"
    const val firebaseVersion = "28.1.0"

    const val facebookLoginVersion = "6.5.0"
    const val facebookShimmer = "0.5.0"

    const val retrofitVersion = "2.9.0"
    const val okHttpVersion = "4.6.0"

    const val hiltVersion = "2.36"
    const val hiltViewModelVersion = "1.0.0-alpha03"

    const val glideVersion = "4.11.0"
    const val timberVersion = "4.7.1"

    const val intuitVersion = "1.0.6"
}

object Dependencies {
    const val coreKtx = "androidx.core:core-ktx:${Version.coreKtxVersion}"
    const val playCoreKtx = "com.google.android.play:core-ktx:${Version.playCoreKtxVersion}"

    object ClassPath {
        const val gradle = "com.android.tools.build:gradle:${Version.ClassPath.gradleVersion}"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}"
        const val navArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.ClassPath.navVersion}"
        const val hiltAndroid = "com.google.dagger:hilt-android-gradle-plugin:${Version.ClassPath.hiltVersion}"
        const val googleGms = "com.google.gms:google-services:${Version.ClassPath.gmsVersion}"
        const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-gradle:${Version.ClassPath.crashlyticsVersion}"
    }

    object UI {
        const val activityKtx = "androidx.activity:activity-ktx:${Version.activityVersion}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Version.fragmentVersion}"
        const val appCompat = "androidx.appcompat:appcompat:${Version.appCompatVersion}"
        const val materialDesign = "com.google.android.material:material:${Version.materialDesignVersion}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.constraintVersion}"
        const val intuitSdp = "com.intuit.sdp:sdp-android:${Version.intuitVersion}"
        const val intuitSsp = "com.intuit.ssp:ssp-android:${Version.intuitVersion}"

        val uiDependencies = mutableListOf(
            activityKtx, fragmentKtx, appCompat, materialDesign, constraintLayout, intuitSdp, intuitSsp,
        )
    }

    object Logging {
        const val timber = "com.jakewharton.timber:timber:${Version.timberVersion}"
    }

    object Jetpack {
        const val viewModelKtx =  "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycleVersion}"
        const val viewModelRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleVersion}"
        const val lifeCycle = "androidx.lifecycle:lifecycle-common-java8:${Version.lifecycleVersion}"
        const val liveDataKtx =  "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycleVersion}"
        const val paging = "androidx.paging:paging-runtime:${Version.pagingVersion}"
        const val roomKtx = "androidx.room:room-ktx:${Version.roomVersion}"
        const val roomRuntime = "androidx.room:room-runtime:${Version.roomVersion}"
        const val roomKapt = "androidx.room:room-compiler:${Version.roomVersion}"
        const val navigation = "androidx.navigation:navigation-fragment-ktx:${Version.lifecycleVersion}"
        const val navigationRuntime = "androidx.navigation:navigation-runtime:${Version.lifecycleVersion}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Version.lifecycleVersion}"
        const val dataStore = "androidx.datastore:datastore-preferences:${Version.dataStoreVersion}"

        val jetpackComponentDependencies = mutableListOf(
            viewModelKtx, viewModelRuntime, lifeCycle, liveDataKtx, paging, roomKtx, roomRuntime,
            navigation, navigationRuntime, navigationUi, dataStore
        )
    }

    object Kotlin {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlinVersion}"

        object Coroutines {
            const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutinesVersion}"
            const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutinesVersion}"
            const val coroutinesPlayService = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Version.coroutinesVersion}"

            val coroutinesDependencies = mutableListOf(coroutinesCore, coroutinesAndroid, coroutinesPlayService)
        }
    }

    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofitVersion}"
        const val gson = "com.squareup.retrofit2:converter-gson:${Version.retrofitVersion}"
        const val okHttp = "com.squareup.okhttp3:logging-interceptor:${Version.okHttpVersion}"

        val networkDependencies = mutableListOf(
            retrofit, gson, okHttp
        )
    }

    object Media {
        const val glide = "com.github.bumptech.glide:glide:${Version.glideVersion}"
        const val glideCompilerKapt = "com.github.bumptech.glide:compiler:${Version.glideVersion}"
    }

    object Injection {
        const val dagger = "com.google.dagger:hilt-android:${Version.hiltVersion}"
        const val fragmentNavigationInject = "androidx.hilt:hilt-navigation-fragment:${Version.hiltViewModelVersion}"
        const val androidCompiler = "com.google.dagger:hilt-android-compiler:${Version.hiltVersion}"

        val injectionDependencies = mutableListOf(
            dagger, fragmentNavigationInject
        )
    }

    object Facebook {
        const val login = "com.facebook.android:facebook-login:${Version.facebookLoginVersion}"
        const val shimmer = "com.facebook.shimmer:shimmer:${Version.facebookShimmer}"

        val facebookDependencies = mutableListOf(
            login, shimmer
        )
    }

    object Testing {
        const val jUnit = "junit:junit:${Version.jUnitVersion}"
        const val jUnitTest = "androidx.test.ext:junit:${Version.jUnitTestVersion}"
        const val espresso = "androidx.test.espresso:espresso-core:${Version.espressoVersion}"
    }

    object Firebase {
        const val firebaseBOM = "com.google.firebase:firebase-bom:${Version.firebaseVersion}"
        const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
        const val firebaseAnalyticsKtx = "com.google.firebase:firebase-analytics-ktx"
        const val firebaseCrashlyticsKtx = "com.google.firebase:firebase-crashlytics-ktx"
        const val firebaseFirestore = "com.google.firebase:firebase-firestore-ktx"
        const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
    }

    val kaptImp = mutableListOf(
            Jetpack.roomKapt,
            Injection.androidCompiler,
            Media.glideCompilerKapt
    )
}

object Config {
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object Plugin {
    const val application = "com.android.application"
    const val androidKotlin = "kotlin-android"
    const val kotlinParcelize = "kotlin-parcelize"
    const val firebaseCrashlytics = "com.google.firebase.crashlytics"
    const val kotlinKapt = "kotlin-kapt"
    const val navArgs = "androidx.navigation.safeargs.kotlin"
    const val hilt = "dagger.hilt.android.plugin"
    const val googleGms = "com.google.gms.google-services"
}

object Repositories {
    const val jitpackUrl = "https://jitpack.io"
}

object Task {
    const val clean = "clean"
}