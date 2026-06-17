plugins {
    id("java")
    // 1. Adds Spring Boot capabilities
    id("org.springframework.boot") version "4.0.0"
    // 2. Manages version numbers for all Spring dependencies automatically
    id("io.spring.dependency-management") version "1.1.5"
}

group = "org.example"
version = "1.0-SNAPSHOT"

// 3. Explicitly sets the Java version to 25 as requested
java {
    sourceCompatibility = JavaVersion.VERSION_25
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}