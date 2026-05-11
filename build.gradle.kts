plugins {
    java
    application
    id("com.diffplug.spotless") version "6.23.3"
}

group = "br.ufsc.ine.leb"
version = "1.0.0"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClass.set("br.ufsc.ine.leb.roza.ui.legacy.RozaUi")
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src/main/java"))
        }
    }
    test {
        java {
            setSrcDirs(listOf("src/test/java"))
        }
        resources {
            setSrcDirs(listOf("src/test/resources"))
        }
    }
    create("expt") {
        java {
            setSrcDirs(listOf("src/expt/java"))
        }
        resources {
            setSrcDirs(listOf("src/expt/resources"))
        }
        compileClasspath += sourceSets.main.get().output + sourceSets.main.get().compileClasspath
        runtimeClasspath += sourceSets.main.get().output + sourceSets.main.get().runtimeClasspath
    }
}

dependencies {
    implementation("com.github.javaparser:javaparser-core:3.10.0")
    implementation("com.github.javaparser:javaparser-symbol-solver-core:3.9.1")

    implementation("org.jsoup:jsoup:1.11.3")

    implementation("org.graphstream:gs-core:2.0")
    implementation("org.graphstream:gs-ui-swing:2.0")

    implementation("junit:junit:4.13.2")
    implementation("org.junit.jupiter:junit-jupiter-api:5.9.3")

    implementation("org.jetbrains:annotations:24.0.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("runLegacyRozaUi") {
    group = "application"
    description = "Runs the legacy Roza UI."
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("br.ufsc.ine.leb.roza.ui.legacy.RozaUi")
}

fun registerExperimentTask(
    taskName: String,
    taskDescription: String,
    experimentMainClass: String,
    experimentArgs: List<String> = emptyList(),
) {
    tasks.register<JavaExec>(taskName) {
        group = "verification"
        description = taskDescription
        classpath = sourceSets["expt"].runtimeClasspath
        mainClass.set(experimentMainClass)
        args(experimentArgs)
    }
}

registerExperimentTask("runExperimentA", "Runs experiment a, which compares similarity metrics for reuse-oriented refactoring candidates.", "br.ufsc.ine.leb.roza.expt.a.Experiment")

registerExperimentTask("runExperimentB", "Runs experiment b, which generates a compact similarity measurement example.", "br.ufsc.ine.leb.roza.expt.b.Examples")

registerExperimentTask("runExperimentC", "Runs experiment c, which compares clustering configurations for implicit-setup refactoring.", "br.ufsc.ine.leb.roza.expt.c.Experiment")

registerExperimentTask("runExperimentD", "Runs experiment d, which measures reuse after refactoring 16 student programs.", "br.ufsc.ine.leb.roza.expt.d.Experiment")

registerExperimentTask("runExperimentE", "Runs experiment e, which refactors the banking-system use case.", "br.ufsc.ine.leb.roza.expt.e.Experiment")

registerExperimentTask("runExperimentF", "Runs experiment f, which measures reuse after refactoring 47 student programs.", "br.ufsc.ine.leb.roza.expt.f.Experiment")

registerExperimentTask("runExperimentG", "Runs experiment g, which benchmarks baseline and optimized clustering scalability on Apache Commons Lang.", "br.ufsc.ine.leb.roza.expt.g.Experiment")

registerExperimentTask("runExperimentH", "Runs experiment h, which compares original, local-only, and global refactoring on Apache Commons Lang.", "br.ufsc.ine.leb.roza.expt.h.Experiment")

spotless {
    java {
        target("src/main/java/**/*.java", "src/test/java/**/*.java", "src/expt/java/**/*.java")
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
        indentWithTabs(2)
        toggleOffOn()
    }
}
