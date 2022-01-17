val kotlinVersion: String by System.getProperties()
val serializationVersion: String by project
val ktorVersion: String by project
val coroutinesVersion: String by project
val htmlJvmVersion: String by project
val styledVersion: String by project
val reduxVersion: String by project
val reactVersion: String by project
val reactDomVersion: String by project
val reactRouterDomVersion: String by project
val reactReduxVersion: String by project

plugins {
    val kotlinVersion: String by System.getProperties()

    kotlin("multiplatform") version kotlinVersion
    application
}

group = "de.rahn.kotlin.multiplatform.tictactoe"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-html-builder:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$htmlJvmVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$reactVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$reactDomVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:$styledVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:$reactRouterDomVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:$reduxVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:$reactReduxVersion")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

application {
    mainClass.set("de.rahn.kotlin.multiplatform.tictactoe.ServerKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}