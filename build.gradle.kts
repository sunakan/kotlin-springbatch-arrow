//import org.gradle.internal.deprecation.DeprecatableConfiguration
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//import org.jetbrains.kotlin.kapt3.base.Kapt.kapt
import org.springframework.boot.gradle.tasks.run.BootRun


plugins {
    val kotlinVersion = "1.4.21"
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    //kotlin("kapt") version kotlinVersion

    // -----------------------------------------------------------------------------
    // 参考：https://plugins.gradle.org/plugin/org.jlleitschuh.gradle.ktlint
    // $ gradle ktlintFormat
    // -----------------------------------------------------------------------------
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven(url = "https://dl.bintray.com/arrow-kt/arrow-kt/")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.batch:spring-batch-test")

    // jackson(自分で入れる)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // junit(自分で入れる)
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    // インメモリDB(自分で入れる)
    implementation("org.hsqldb:hsqldb")
    // SpringBoot + KotlinでメタデータをIDEに認識させるために必要
    // https://spring.pleiades.io/guides/tutorials/spring-boot-kotlin/
    //kapt("org.springframework.boot:spring-boot-configuration-processor")
}

// 自分で入れる
tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

//------------------------------------------------------------------------------
// bootRun時のデフォルトのプロファイルを設定
// $ gradle bootRun
//------------------------------------------------------------------------------
tasks.withType<BootRun> {
    args("--spring.profiles.active=develop")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//-------------------------------------------------------------------------------
//
//-------------------------------------------------------------------------------
//fun Configuration.isDeprecated(): Boolean =
//    if (this is DeprecatableConfiguration) {
//        resolutionAlternatives != null
//    } else {
//        false
//    }

// ------------------------------------------------------------------------------
// 依存解決
// $ gradle downloadDependencies
// ------------------------------------------------------------------------------
tasks.register("downloadDependencies") {
    doLast {
        val allDeps = configurations.names
            .map { configurations[it] }
            .filter { it.isCanBeResolved }
            .map { it.resolve().size }
            .sum()
        println("Downloaded all dependencies: $allDeps")
    }
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    this.archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}
