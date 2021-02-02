plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        minSdkVersion(21)
        versionCode(1)
        versionName("1.0")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = Dependencies.kotlinVersion
        kotlinCompilerExtensionVersion = Dependencies.composeVersion
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
}

dependencies {
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.composeUi)
}