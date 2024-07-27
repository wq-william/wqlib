import org.gradle.internal.component.model.Exclude
import org.jetbrains.kotlin.cli.jvm.main

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}
version = "0.2"

android {
    namespace = "hz.wq.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    viewBinding {
        enable = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
//    sourceSets {
//        val main = getByName<SourceSet>("main")
//        main.resources.srcDirs(
//            "src/main/res",
//            "src/main/res-sw"
//        )
//    }
//    sourceSets {
//        val main by getting<AndroidSourceSet>()
//        main.res.srcDirs("src/main/res", "src/main/res-sw")
//    }
//    sourceSets {
//        main {
//            // res 资源目录配置
//            res.srcDirs(
//                'src/main/res',
//                'src/main/res-sw',
//            )
//        }
//    }
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)
    api(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api("com.blankj:utilcodex:1.31.1")
    // 动画解析库：https://github.com/airbnb/lottie-android
    // 动画资源：https://lottiefiles.com、https://icons8.com/animated-icons
    implementation("com.airbnb.android:lottie:6.4.1")


    // 沉浸式框架：https://github.com/gyf-dev/ImmersionBar
    //    api("com.gyf.immersionbar:immersionbar:3.2.2")
    // 基础依赖包，必须要依赖
    api("com.geyifeng.immersionbar:immersionbar:3.2.2")
    // kotlin扩展（可选）
    api("com.geyifeng.immersionbar:immersionbar-ktx:3.2.2")
//
//    // 权限请求框架：https://github.com/getActivity/XXPermissions
//    implementation("com.github.getActivity:XXPermissions:12.3")
//    // 网络请求框架：https://github.com/getActivity/EasyHttp
//    implementation("com.github.getActivity:EasyHttp:10.2")
//



    // Shape 框架：https://github.com/getActivity/ShapeView
//    implementation("com.github.getActivity:ShapeView:6.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

extra.set("libVersion", version)
extra.set("libArtifactId", "common")
apply("../publishing.gradle.kts")