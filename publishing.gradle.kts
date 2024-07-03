apply(plugin = "maven-publish")
extensions.configure<PublishingExtension>("publishing") {
    publications {
        create<MavenPublication>("release") {
            groupId = "io.github.wq-william"
            artifactId = project.extra["libArtifactId"] as String
            version = project.extra["libVersion"] as String
            afterEvaluate {
                from(components["release"])
            }
            pom {
                name = "My Library"
                description = "A concise description of my library"
                url = "https://github.com/wq-william/wqlib"
                properties = mapOf(
                    "myProp" to "value",
                    "prop.with.dots" to "anotherValue"
                )
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "github_13477226"
                        name = "415906626@qq.com"
                        email = "415906626@qq.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/wq-william/wqlib.git"
                    developerConnection = "scm:git:ssh://github.com/wq-william/wqlib.git"
                    url = "https://github.com/wq-william/wqlib"
                }
            }
        }
    }

}