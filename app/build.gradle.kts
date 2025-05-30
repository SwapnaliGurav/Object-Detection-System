plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.smartshopassistant"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.smartshopassistant"
        minSdk = 21
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
        mlModelBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.android.gms:play-services-fitness:21.1.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.ai.client.generativeai:generativeai:0.2.2")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.jsoup:jsoup:1.14.3")

    implementation ("org.jetbrains.anko:anko-common:0.9")
    implementation ("com.github.bumptech.glide:glide:4.16.0")


    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
    implementation("com.airbnb.android:lottie:3.7.0")
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("com.etebarian:meow-bottom-navigation:1.2.0")





}