object Build {
    const val jvmTarget = "11"
    const val applicationId = "com.arysugiarto.dokutest"

    const val resAppName = "app_name"
    const val apkExtension = ".apk"

    const val stringType = "String"
    const val intType = "Int"

    const val stringResType = "string"
    const val integerResType = "integer"

}

object BuildType {
    const val flavorDimen = "default_flavor"
    const val debug = "debug"
    const val release = "release"

    object Default {
        const val compileSdkVersion = 31
        const val buildToolsVersion = "30.0.2"
        const val versionName = "1.0.0"

        const val minSdkVersion = 23
        const val targetSdkVersion = 32
        const val versionCode = 1
    }

    object Develop {
        const val targetSdkVersion = 32

        const val suffixId = ".dev"
        const val suffixName = "-dev"

        const val baseUrlName = "BASE_URL"
        const val baseUrl = "\"https://newsapi.org\""


        const val resBaseUrlName = "res_base_url"
        const val resBaseUrl = "newsapi.org"

        const val appNameKey = "app_name"
        const val appName = "Doku Test"
    }

}

object ProGuard {
    const val optimizeRule = "proguard-android-optimize.txt"
    const val optimizeFile = "proguard-rules.pro"
}

object Flavors {
    const val develop = "dev"
}

object AppProperties {
    const val ACCEPT_KEY = "ACCEPT_KEY"
    const val ACCEPT_KEY_VALUE = "\"Accept\""
    const val ACCEPT_VALUE = "ACCEPT_VALUE"
    const val ACCEPT_VALUE_VALUE = "\"application/json\""
    const val APPLICATION_KEY_NAME = "APPLICATION_KEY_NAME"
    const val APPLICATION_KEY= "\"Application-Key\""
    const val APPLICATION_KEY_VALUE_NAME = "APPLICATION_KEY_VALUE_NAME"
    const val APPLICATION_KEY_VALUE = "\"Q5nGL97wRBFlgQ2WOG3XDnHhV33jFIYWdHKArumKS78YaSQA3U\""
    const val COOKIE = "COOKIE_KEY"
    const val COOKIE_VALUE = "\"Cookie\""
}