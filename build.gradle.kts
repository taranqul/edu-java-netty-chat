plugins {
    java
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    group = "edu.taranqul"
    repositories {
        mavenCentral()
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.28")
        annotationProcessor("org.projectlombok:lombok:1.18.28")
    }
}
