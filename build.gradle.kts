// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    `maven-publish`
    `java-library`
}
publishing {
//    publications {
//        create<MavenPublication>("myLibrary") {
//            from(components["java"])
//        }
//    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "hz.wq.testhttpsend"
            artifactId = "libraryaaa"
            version = "1.0.1"

            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "myRepo"
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
}
group = "com.my-company"
version = "1.0"