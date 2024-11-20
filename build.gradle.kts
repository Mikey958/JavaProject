plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("com.opencsv:opencsv:5.5.2")
    compileOnly("org.projectlombok:lombok:1.18.30") // или последняя доступная версия
    annotationProcessor("org.projectlombok:lombok:1.18.30") // если используете annotationProcessor
    implementation("commons-io:commons-io:2.14.0")
    //API
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    implementation("org.json:json:20231013")
}

tasks.test {
    useJUnitPlatform()
}