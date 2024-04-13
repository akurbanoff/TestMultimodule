@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlinAndroid)
    id(libs.plugins.devtools.get().pluginId)
}

android {
    namespace = "ru.axas.api"
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

        //    #   Dagger2
        implementation(dagger.core)
        implementation(dagger.android)
        implementation(dagger.android.support)
        implementation(javax.inject)
        ksp(dagger.compiler)
        ksp(dagger.android.processor)
    }
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.espresso)
}