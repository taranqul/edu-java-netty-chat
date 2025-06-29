plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.springframework.boot") version "3.2.4"
    application
}

dependencies {
    implementation("io.netty:netty-all:4.2.2.Final")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.springframework.boot:spring-boot-starter:3.5.3")

}

application {
    mainClass.set("edu.taranqul.chat.server.App")
}

tasks.shadowJar {
    archiveBaseName.set("server")
    archiveClassifier.set("all")
    archiveVersion.set("")
}