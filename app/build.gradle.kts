plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.vinovista"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vinovista"
        minSdk = 27
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Thư viện Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")

    // Các thư viện bên ngoài khác
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.squareup.picasso:picasso:2.71828")

    // Thư viện dành cho việc kiểm thử
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Thu vien chuyen anh
    implementation("me.relex:circleindicator:2.1.6")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //Thu vien apache
    implementation("org.apache.commons:commons-compress:1.21")
    implementation("org.apache.poi:poi:4.1.2")
    implementation("org.apache.poi:poi-ooxml:4.1.2")
    implementation("javax.xml.stream:stax-api:1.0")

    implementation("org.apache.xmlbeans:xmlbeans:3.1.0")

    //thông báo
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.android.volley:volley:1.2.1")
}