plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.thomasw.precision"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thomasw.precision"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material-icons-extended:1.7.4")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //folder dependencies
    implementation ("androidx.navigation:navigation-compose:2.5.3") // Navigation Compose
    implementation ("androidx.compose.ui:ui:1.5.0") // Compose UI
    implementation ("androidx.compose.material3:material3:1.3.1") // Material3
    implementation ("androidx.activity:activity-compose:1.6.1") // Activity Compose


    val nav_version = "2.8.3"
    val jlatexmath_version = "0.2.0"

    // Jetpack Compose integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // Be sure to add the following implenentations or you WILL get an ERROR!!!!
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("androidx.compose.foundation:foundation:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.compose.material:material:1.5.1")

    // Wraps folders to next line
    implementation ("com.google.accompanist:accompanist-flowlayout:0.36.0")

    // JLaTeXMath dependencies
    implementation("ru.noties:jlatexmath-android:$jlatexmath_version")
    implementation("ru.noties:jlatexmath-android-font-cyrillic:$jlatexmath_version")
    implementation("ru.noties:jlatexmath-android-font-greek:$jlatexmath_version")

    implementation ("androidx.compose.runtime:runtime:1.5.0")

    implementation ("androidx.appcompat:appcompat:1.6.1")







}