plugins {
    id("c7-conventions")
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.34")
	annotationProcessor("org.projectlombok:lombok:1.18.34")
    implementation("org.camunda.bpm.extension:camunda-bpm-process-test-coverage-junit5:1.0.3")
}