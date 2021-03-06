plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.2"

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
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