import com.google.protobuf.gradle.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.protobuf") version "0.8.17"
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.2"

    defaultConfig {
        applicationId = "com.dominikgold.calorietracker"
        minSdk = 24
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        this.getByName("debug") {
            storeFile = file(property("debugKeyStore") as String)
            storePassword = property("debugStorePassword") as String
            keyAlias = property("debugKeyAlias") as String
            keyPassword = property("debugKeyPassword") as String
        }
        create("release") {
            storeFile = file(property("releaseKeyStore") as String)
            storePassword = property("releaseStorePassword") as String
            keyAlias = property("releaseKeyAlias") as String
            keyPassword = property("releaseKeyPassword") as String
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            isDebuggable = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.composeVersion
    }
    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {

    implementation(project(":compose-viewmodel"))
    implementation(Dependencies.kotlinSdk)

    implementation(Dependencies.composeLineChart)

    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)

    implementation(Dependencies.androidXCore)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.materialComponents)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.composeTooling)
    implementation(Dependencies.composeFoundation)
    implementation(Dependencies.lifecycleRuntime)
    implementation(Dependencies.constraintLayoutCompose)

    // Proto data store
    implementation(Dependencies.androidDataStore)
    // Protobuf
    implementation(Dependencies.protobuf)

    // Room
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    kapt(Dependencies.roomCompiler)

    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerCompiler)

    coreLibraryDesugaring(Dependencies.desugarJdkLibs)

    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.kluent)
    testImplementation(Dependencies.mockitoKotlin)
    testImplementation(Dependencies.coroutinesTest)

    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation(Dependencies.androidXTestJUnit)
    androidTestImplementation(Dependencies.androidXTestCore)
    androidTestImplementation(Dependencies.androidXTestRules)
    androidTestImplementation(Dependencies.androidXTestRunner)
    androidTestImplementation(Dependencies.androidArchCoreTesting)
    androidTestImplementation(Dependencies.roomTesting)
    androidTestImplementation(Dependencies.coroutinesTest)

    androidTestImplementation(Dependencies.kluentAndroid)

}

protobuf {
    protoc {
        artifact = Dependencies.protoC
    }

    generateProtoTasks {
        all().forEach { task ->
            task.plugins.create("java") {
                option("lite")
            }
        }
    }
}