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
    compileOnly("org.projectlombok:lombok:1.18.28") // или последняя доступная версия
    annotationProcessor("org.projectlombok:lombok:1.18.28") // если используете annotationProcessor
}

tasks.test {
    useJUnitPlatform()
}