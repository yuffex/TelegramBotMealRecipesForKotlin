plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.telegram:telegrambots:5.3.0")
    implementation("com.google.cloud:google-cloud-translate:2.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation ("com.google.cloud:google-cloud-translate:2.3.1")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}