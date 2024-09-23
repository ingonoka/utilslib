/*
 * Copyright (c) 2024. Ingo Noka
 * This file belongs to project hexutils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.dokkaGradlePlugin)
}

android {

//    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//    sourceSets["main"].res.srcDirs("src/androidMain/res")
//    sourceSets["main"].resources.srcDirs("src/androidMain/resources")
//    sourceSets["main"].java.srcDirs("src/androidMain/java")

    namespace = "${rootProject.group}.${rootProject.name}"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    version = rootProject.version

    compileOptions {
        targetCompatibility = JavaVersion.valueOf(libs.versions.android.target.compatibility.get())
        sourceCompatibility = JavaVersion.valueOf(libs.versions.android.source.compatibility.get())
    }

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-android-optimize.txt")
            testProguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-android-optimize.txt")
        }
        debug {
            isMinifyEnabled = false
        }
    }
}

kotlin {

    jvm().compilations.all {
        compileTaskProvider.configure{
            compilerOptions {
                jvmTarget.set(JvmTarget.valueOf(libs.versions.jvm.target.get()))
            }
        }
    }

    macosX64()

    androidTarget {
        publishLibraryVariants("release", "debug")

        compilations.all {
            compileTaskProvider.configure{
                compilerOptions {
                    jvmTarget.set(JvmTarget.valueOf(libs.versions.jvm.target.get()))
                }
            }
        }
    }

    sourceSets {

        commonMain {
            dependencies{
                implementation(libs.kotlin.coroutines)
                implementation(libs.kotlinx.datetime)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.coroutines.test)
                implementation(libs.ingonoka.hexutils)
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.jvm.logback.classic)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.android.logback)
            }
        }
    }
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        pom {
            // Properties from the root project gradle.properties
            name = providers.gradleProperty("com.ingonoka.pomName").get()
            description = providers.gradleProperty("com.ingonoka.pomDescription").get()
            licenses {
                license {
                    // Properties from the ~/.gradle/gradle.properties
                    name = providers.gradleProperty("com.ingonoka.pomLicenseName").get()
                    url = providers.gradleProperty("com.ingonoka.pomLicenseUrl").get()
                }
            }
            developers {
                developer {
                    // Properties from the ~/.gradle/gradle.properties
                    name = providers.gradleProperty("com.ingonoka.pomDeveloper")
                }
            }
        }
    }
}

tasks.dokkaHtml.configure {
    outputDirectory.set(layout.buildDirectory.get().dir("dokka"))
    // Properties from the root project gradle.properties
    moduleName.set(rootProject.name)

    dokkaSourceSets {
        named("jvmMain") {
            displayName = "JVM"
            platform.set(org.jetbrains.dokka.Platform.jvm)
        }

        named("androidMain") {
            displayName = "Android"
        }

        named("commonMain") {
            platform.set(org.jetbrains.dokka.Platform.common)
            displayName = "Common"
        }

    }
}

repositories {
    mavenCentral()
    google()
    mavenLocal()
}
