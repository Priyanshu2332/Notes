plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin") // Safe Args plugin for passing data between fragments
}


android {
    namespace = "com.example.internshalanotes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.internshalanotes"
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.auth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)

    //firebase
    implementation(libs.firebase.auth)
    implementation(platform(libs.firebase.bom))

    //use for Navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    //Room Database
    implementation (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)
    androidTestImplementation (libs.androidx.room.testing)


    // Lifecycle components and view mdoel
    implementation (libs.androidx.lifecycle.extensions)
    implementation (libs.androidx.lifecycle.common.java8)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.activity.ktx)

}