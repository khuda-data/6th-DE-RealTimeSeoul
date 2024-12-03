plugins {
    id("java")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.kafka:kafka-clients:3.6.2")
    implementation("org.apache.kafka:kafka-streams:3.6.2")
    // Optional: Logging dependencies (choose one based on your logging preference)
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("org.json:json:20190722")
    implementation("software.amazon.awssdk:s3:2.20.2")

    // Test dependencies
    testImplementation("junit:junit:4.13.2")
}

tasks.test {
    useJUnitPlatform()
}