plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.prog7313_starsucks"
    compileSdk = 35
    viewBinding.enable = true

    defaultConfig {
        applicationId = "com.example.prog7313_starsucks"
        minSdk = 26
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
}

// defining a read-only variable with CameraX libraries to version '1.1.0'
val camerax_version = "1.4.2"

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.activity:activity-ktx:1.10.1")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-firestore:25.1.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    // Camera2 allows developers to access and control the camera hardware
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    // Simplifies the management of the camera's lifecycle (properly opened,
    // closed, and released based on the lifecycle of the associated LifecycleOwner (such as an Activity)
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    // Provides a set of UI components that can be used to create camera-related UI's to the
    // app. Such as PreviewView
    implementation("androidx.camera:camera-view:${camerax_version}")
        
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1") // for kotlin support
    ksp("androidx.room:room-compiler:2.6.1") // for kotlin annotation processing
}