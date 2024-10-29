plugins {
    id("c7-conventions")
}

val CAMUNDA_VERSION: String by project
val JUEL_VERSION = "2.2.7"

dependencies {
    testImplementation("org.camunda.bpm.dmn:camunda-dmn-junit5:$CAMUNDA_VERSION")
    testImplementation("de.odysseus.juel:juel-api:${JUEL_VERSION}")
    testImplementation("de.odysseus.juel:juel-impl:${JUEL_VERSION}")
    testImplementation("de.odysseus.juel:juel-spi:${JUEL_VERSION}")
}