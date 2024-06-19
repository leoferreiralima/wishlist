plugins {
	java
	jacoco

	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.sonarqube") version "5.0.0.4638"
}


group = "dev.leoferreira"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val springdocVersion = "2.3.0"
val testcontainersVersion = "1.19.8"

dependencies {
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-web")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
	testImplementation("org.testcontainers:mongodb:$testcontainersVersion")
	testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
	testImplementation("io.projectreactor:reactor-test")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required = true
	}
}

sonar {
	properties {
		property("sonar.projectKey", "leoferreiralima_wishlist")
		property("sonar.organization", "leoferreiralima")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.coverage.exclusions", "src/main/java/dev/leoferreira/wishlist/WishlistApplication.java, src/main/java/dev/leoferreira/wishlist/**/GenericErrorResponseCustomizer.java")
	}
}