plugins {
    with(libs.plugins) {
        id(application.get().pluginId)
        id(kotlinAndroid.get().pluginId)
        id(kotlinSerialization.get().pluginId)
//        id(kotlinSerializationPlugin.get().pluginId)
//        id(kotlinKapt.get().pluginId)
//        id(kotlinParcelize.get().pluginId)
        id(devtools.get().pluginId)
//        id(googleServicePlugin.get().pluginId)
    }
}

android {
    namespace = "ru.axas.testmultimodule"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.axas.testmultimodule"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:auction"))
    implementation(project(":core"))
    implementation(project(":api"))
    with(libs){
//    #   Kotlin
        implementation(kotlin.serialization)

//    #   Androidx core-ktx
        implementation(androidx.core.ktx)

//    #   Androidx runtime-ktx
        implementation(androidx.lifecycle)
        implementation(androidx.lifecycle.viewmodel.compose)
        implementation(androidx.lifecycle.compose)

//    #   Datastore
        implementation(datastore.preferences)

//    #   Coroutines
        implementation(kotlin.android.coroutines)
        implementation(kotlin.coroutines)

//    #   AndroidxWork runtime
        implementation(androidxWork.runtime.ktx)

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

//    #   Paging
        implementation(paging.compose)
        implementation(paging.rintime)

//    #   Room
        implementation(room.ktx)
        implementation(room.runtime)
        ksp(room.compiler)

//    #   Dagger2
        implementation(dagger.core)
        implementation(dagger.android)
        implementation(dagger.android.support)
        implementation(javax.inject)
        ksp(dagger.compiler)
        ksp(dagger.android.processor)

//    #   Navigation
        implementation(navigation.compose)
        implementation(navigation.common)

//    #   Retrofit
        implementation(retrofit.core)
        implementation(converter.gson)
        implementation(converter.scalars)

//    #   OkHttp3
        implementation(okhttp.core)
        implementation(okhttp.logging.interceptor)

//    #   Coil
        implementation(coil.compose)
        implementation(coil.svg)

//    #   Zelory
        implementation(zelory.compressor)

//    #   Firebase
        implementation(firebase.messaging)

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