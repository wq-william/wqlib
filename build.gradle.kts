// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false

//    `maven-publish`
//    alias(libs.plugins.mavenPublish) apply false

}
subprojects {
    apply(plugin = "maven-publish")
}
//group = "com.example"
//version = "1.0.0"

//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
//            from(components["java"])
//            artifactId = "my-library"
//        }
//    }
//
//    repositories {
//        maven {
//            url = uri("https://path/to/your/maven/repository")
//            credentials {
//                username = project.findProperty("mavenUsername") as String? ?: ""
//                password = project.findProperty("mavenPassword") as String? ?: ""
//            }
//        }
//    }
//}
//publishing {
////    publications {
////        create<MavenPublication>("myLibrary") {
////            from(components["java"])
////        }
////    }
//    publications {
////        create<MavenPublication>("mavenJava") {
//        create<MavenPublication>("mavenJava") {
//            groupId = "hz.wq.testhttpsend"
//            artifactId = "libraryaaa"
//            version = "1.0.1"
//
//            from(components["java"])
//        }
//    }
////    repositories {
////        maven {
////            url = uri("https://path/to/your/maven/repository")
////            credentials {
////                username = project.findProperty("mavenUsername") as String? ?: ""
////                password = project.findProperty("mavenPassword") as String? ?: ""
////            }
////        }
////    }
//    repositories {
//        maven {
//            name = "myRepo"
//            url = uri(layout.buildDirectory.dir("repo"))
//        }
//
//    }
//}
//group = "com.my-company"
//version = "1.0.2"

