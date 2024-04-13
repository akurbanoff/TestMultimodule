plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlinAndroid)
    with(libs.plugins) {
        id(kotlinSerialization.get().pluginId)
        id(devtools.get().pluginId)
    }
}

android {
    namespace = "ru.axas.auction"
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
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.get()
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":api"))
    with(libs){
        //    #   Navigation
        implementation(navigation.compose)
        implementation(navigation.common)

//    #   Androidx runtime-ktx
        implementation(androidx.lifecycle)
        implementation(androidx.lifecycle.viewmodel.compose)
        implementation(androidx.lifecycle.compose)
        //    #   Retrofit
        implementation(retrofit.core)
        implementation(converter.gson)
        implementation(converter.scalars)

//    #   OkHttp3
        implementation(okhttp.core)
        implementation(okhttp.logging.interceptor)
        //    #   Dagger2
        implementation(dagger.core)
        implementation(dagger.android)
        implementation(dagger.android.support)
        implementation(javax.inject)
        ksp(dagger.compiler)
        ksp(dagger.android.processor)

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