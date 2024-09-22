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
    implementation("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    implementation("org.modelmapper:modelmapper:3.2.1")
    implementation("com.fasterxml:jackson-xml-databind:0.6.2")
    implementation("com.sparkjava:spark-core:2.9.4")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("org.json:json:20090211")
}

tasks.test {
    useJUnitPlatform()
}