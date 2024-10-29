plugins {
    id("c7-conventions")
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.34")
	annotationProcessor("org.projectlombok:lombok:1.18.34")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-external-task-client:7.22.0")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.3")
}