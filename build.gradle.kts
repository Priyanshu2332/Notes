buildscript {
    repositories {
        google()
    }

    dependencies {

        classpath (libs.google.services)
        classpath (libs.androidx.navigation.navigation.safe.args.gradle.plugin)
    }
}


plugins {
    id ("com.android.application") version "8.1.4" apply false
    id ("com.android.library") version "8.1.4" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

