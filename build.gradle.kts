plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.analyzer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.register<JavaExec>("runExample") {
    mainClass = "org.analyzer.MainKt"
    classpath = sourceSets.main.get().runtimeClasspath
    val programArgs = project.findProperty("args")?.toString()?.split("\\s+".toRegex())
    args(programArgs ?: listOf<String>())
}
