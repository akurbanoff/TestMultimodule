@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlinAndroid)
    id(libs.plugins.kotlinSerialization.get().pluginId)
}

android {
    namespace = "ru.axas.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        compose  = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.get()
    }
}

dependencies {
    with(libs){
        //    #   Retrofit
        implementation(retrofit.core)
        implementation(converter.gson)
        implementation(converter.scalars)

//    #   OkHttp3
        implementation(okhttp.core)
        implementation(okhttp.logging.interceptor)
        //    #   Google play
        implementation(google.play.core)

//    #   Google gms
        implementation(google.gms.location)

//    #   Google Maps
        implementation(google.maps.compose)
        implementation(google.maps.compose.utils)
        implementation(google.maps.compose.widgets)
        implementation(play.services.maps)
        implementation(play.services.location)
        implementation(places)

        //    #   Coil
        implementation(coil.compose)
        implementation(coil.svg)
        //    #   Kotlin
        implementation(kotlin.serialization)

        //    #   Navigation
        implementation(navigation.compose)
        implementation(navigation.common)

//    #   Paging
        implementation(paging.compose)
        implementation(paging.rintime)

        //    #   Androidx core-ktx
        implementation(androidx.core.ktx)

        //    #   Androidx runtime-ktx
        implementation(androidx.lifecycle)
        implementation(androidx.lifecycle.viewmodel.compose)
        implementation(androidx.lifecycle.compose)

        //    #   Compose
        implementation(compose.activity)
        implementation(platform(compose.bom))
        implementation(compose.material)
        runtimeOnly(compose.animation)
        runtimeOnly(compose.foundation)
        implementation(compose.ui)
        implementation(compose.ui.util)
        implementation(compose.ui.graphics)
        implementation(compose.ui.tooling.preview)
        debugImplementation(compose.ui.tooling)
        debugImplementation(test.compose.manifest)

        testImplementation(test.junit)
        androidTestImplementation(test.androidx.junit)
        androidTestImplementation(test.espresso)

        androidTestImplementation(platform(compose.bom))
        androidTestImplementation(test.compose.junit4)
    }
}