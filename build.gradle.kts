// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.application) apply(false)
    alias(libs.plugins.kotlinAndroid) apply(false)
    alias(libs.plugins.kotlinSerialization) apply(false)
    alias(libs.plugins.devtools) apply(false)
    alias(libs.plugins.kotlinJvm) apply(false)
    alias(libs.plugins.library) apply(false)
//    alias(libs.plugins.googleServicePlugin) apply(false)
}