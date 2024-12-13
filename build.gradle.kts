plugins {
    id("java")
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.postgresql:postgresql")
    implementation ("com.opencsv:opencsv:5.8")
    compileOnly("org.projectlombok:lombok:1.18.34") // или последняя доступная версия
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    implementation("commons-io:commons-io:2.14.0")

    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.modelmapper:modelmapper:3.2.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    //API
    implementation("org.jfree:jfreechart:1.5.3")  // Зависимость для работы с JFreeChart
    implementation("org.jfree:jcommon:1.0.24")  // Зависимость для JFreeChart (совместимость)
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    implementation("org.json:json:20231013")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")

    // Gson для работы с JSON
    implementation("com.google.code.gson:gson:2.10")

    // Apache Commons Codec (опционально, для генерации MD5)
    implementation("commons-codec:commons-codec:1.16.0")
}

tasks.test {
    useJUnitPlatform()
}