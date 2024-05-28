import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform) // same as declaring kotlin("multiplatform"). not sure what it does and how the kotlin("multiplatform") works
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" // for mockative https://github.com/mockative/mockative
    kotlin("plugin.allopen") version "1.9.23" // for mockative https://github.com/mockative/mockative. All-open compiler plugin https://kotlinlang.org/docs/all-open-plugin.html
    id("io.realm.kotlin") version "1.13.0"
}

// for mockative https://github.com/mockative/mockative
val taskIsRunningTest = gradle.startParameter.taskNames.any {
    it == "check" || it.startsWith("test") || it.contains("Test")
}
if (taskIsRunningTest) {
    allOpen {
        annotation("io.github.Mockable")
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                // TODO: figure out is this the jvm bytecode version? or does this compile JDK 17
                //  code but can be interpreted by a lower/higher JRE as well? How does the whole shit work?
                // updated because the https://developer.android.com/jetpack/androidx/releases/compose-kotlin
                // mentioned jvmTarget 19, but with 19 it didn't work because android sdk seems to not support 19
                // however why tf it says 19 in the link then?
                jvmTarget = "17"
            }
        }
    }


//    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
//    val a = if (onPhone) {
//        iosArm64("ios")
//    } else {
//        iosX64("ios")
//    }
    listOf(
//        a,
        iosX64(), // intel
        iosArm64(),
        iosSimulatorArm64() // ARM on M1 mac
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        iosMain.dependencies {
            // i don't think this is needed
            implementation("io.insert-koin:koin-core")
//            implementation("io.insert-koin:koin-core") for some reason works even uncommented

        }
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            // android Context library https://proandroiddev.com/how-to-avoid-asking-for-android-context-in-kotlin-multiplatform-libraries-api-d280a4adebd2
            implementation("androidx.startup:startup-runtime:1.1.0")
            implementation("io.insert-koin:koin-android")
        }
        getByName("androidUnitTest").dependencies {
//            implementation("org.jetbrains.kotlin:kotlin-test:1.9.22") // version should be same as Kotlin version of the project. this dep adds @BeforeTest annotations and such
            //            implementation("androidx.test.ext:junit-ktx:1.1.5") // not sure why this might be needed. just keeping it commented to not copy it again from TOML of KampIt project

        }
        commonMain.dependencies {
            //          check these links
//            https://github.com/JetBrains/compose-multiplatform/issues/3437#issuecomment-1872079239
//            https://github.com/dima-avdeev-jb/issue-3437-sql-delight-compilation
//            instead of `implementation(compose.runtime)` we might need to use `api(compose.runtime)`.
//            But according to the message
//            it works with xcode command line tools build command and not ./gradlew build
//            the xcode command line tool command is
//            /Applications/Xcode.app/Contents/Developer/usr/bin/xcodebuild -project "/Users/hakobharutyunyan/projects/Financial Hke/iosApp/iosApp.xcodeproj" -scheme iosApp -configuration Debug "OBJROOT=/Users/hakobharutyunyan/projects/Financial Hke/build/ios" "SYMROOT=/Users/hakobharutyunyan/projects/Financial Hke/build/ios" -destination id=57384CD6-391F-4D61-B1C4-8A67F026F7DF -allowProvisioningDeviceRegistration -allowProvisioningUpdates
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            // don't need this. if adding it, gradle build errors out in GHA. locally running on iOS works in both cases (commented
            // or uncommented). But ./gradlew build fails in case of uncommented, with the same exception as in GHA. Locally ./gradlew build
            // fails if commented, because of -> look at the (1) issue in documentation/issues

            implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.3"))
            implementation("io.insert-koin:koin-core")
            implementation("io.insert-koin:koin-compose")

            implementation("io.realm.kotlin:library-base:1.13.0")
//            implementation("io.realm.kotlin:library-sync:1.13.0") // If using Device Sync
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0") // If using coroutines with the SDK
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0-RC.2")
            // Koin Test features
        }
        commonTest.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-test:1.9.22") // version should be same as Kotlin version of the project. this dep adds @BeforeTest annotations and such. also @Test annotation
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
            implementation("io.mockative:mockative:2.1.0") // for mocking https://github.com/mockative/mockative
            implementation("io.insert-koin:koin-test")

        }
    }
}

android {
    namespace = "org.hakob.financialhke"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    // needed for @Preview annotation to work with Android Studio
    buildFeatures {
        compose = true
    }
    // needed for @Preview annotation to work with Android Studio: more specifically for JetPack Compose compatibility with Kotlin
    // https://developer.android.com/jetpack/androidx/releases/compose-kotlin
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }


    defaultConfig {
        applicationId = "org.hakob.financialhke"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        // updated because the https://developer.android.com/jetpack/androidx/releases/compose-kotlin mentioned jvmTarget 19, but with 19 it didn't work because android sdk seems to not support 19
        // however why tf it says 19 in the link then?
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        // todo: maybe it needs to be here, not below
//        testImplementation("org.testng:testng:6.9.6")
    }
}
dependencies {
    // not sure if needed for @Preview to work. not sure if it needs to be in androidMain
    // copied from the original Android Studio project template when you crate a KMP project
    implementation(libs.compose.material3)
    // below 3 libs needed for @Preview to work
    implementation(libs.compose.ui.tooling.preview)
    implementation(platform("androidx.compose:compose-bom:2024.04.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    testImplementation("org.testng:testng:6.9.6")
    // koin
    implementation(platform("io.insert-koin:koin-bom:3.5.3"))
    implementation("io.insert-koin:koin-android")
    implementation("io.insert-koin:koin-core")

    // Koin Test features
    testImplementation("io.insert-koin:koin-test")
    // Koin for JUnit 4
    testImplementation("io.insert-koin:koin-test-junit4")
    // Koin for JUnit 5
    testImplementation("io.insert-koin:koin-test-junit5")
    testImplementation("org.testng:testng:6.9.6")

    // for Mockative https://github.com/mockative/mockative
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, "io.mockative:mockative-processor:2.1.0")
        }

}

