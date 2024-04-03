plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
//    id ("com.google.gms.google-services")


}

android {
    namespace = "com.example.solveitproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.solveitproject"
        minSdk = 25
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

dependencies {

    implementation ("com.google.firebase:firebase-storage-ktx:20.3.0")
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

//    implementation ("com.google.firebase:firebase-firestore-ktx:24.11.0")




    val navVersion = "2.7.7"
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

//    // Firebase BOM
//    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
//
//// Firebase Analytics
//    implementation("com.google.firebase:firebase-analytics")
//
//// Firebase Firestore
//    implementation("com.google.firebase:firebase-firestore")
//
//// Firebase Realtime Database
//    implementation("com.google.firebase:firebase-database:20.3.1")
//
//// SwipeRefreshLayout
//    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
//
//// Retrofit
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//
//// Firebase Authentication
//    implementation("com.google.firebase:firebase-auth-ktx")
//
//// Picasso for image loading
//    implementation("com.squareup.picasso:picasso:2.71828")
//
//// Glide
//    implementation("com.github.bumptech.glide:glide:4.12.0")
//    kapt("com.github.bumptech.glide:compiler:4.12.0")
//
//// Google Services plugin
//    apply(plugin = "com.google.gms.google-services")

}