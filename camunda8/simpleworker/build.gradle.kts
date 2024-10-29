plugins {
    id("core-conventions")
}

val CAMUNDA_8_VERSION: String by project

dependencies {
    implementation("io.camunda:zeebe-client-java:$CAMUNDA_8_VERSION")

    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
