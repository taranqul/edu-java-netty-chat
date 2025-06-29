plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

application {
    mainClass.set("edu.taranqul.chat.client.Client")
}

tasks.shadowJar {
    archiveBaseName.set("client")
    archiveClassifier.set("all")
    archiveVersion.set("")
}