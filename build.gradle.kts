import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    kotlin("jvm") apply false
    kotlin("plugin.serialization") apply false
    id("io.gitlab.arturbosch.detekt") apply false
}

group = "dmitriy.losev.cs"
version = "0.0.1"

// Применить detekt ко всем подпроектам
subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    configure<DetektExtension> {
        buildUponDefaultConfig = true
        config.setFrom("${rootProject.projectDir}/config/detekt/detekt.yml")
        basePath = rootProject.projectDir.absolutePath
        parallel = true
        allRules = false
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            html.required.set(true)
            xml.required.set(false)
            md.required.set(false)
        }
    }
}

// Функция для получения всех зависимых проектов (включая транзитивные)
fun Project.getAllProjectDependencies(configurationName: String = "compileClasspath"): Set<Project> {
    val dependencies = mutableSetOf<Project>()

    fun collectDependencies(project: Project) {
        project.configurations.findByName(configurationName)?.allDependencies?.forEach { dependency ->
            if (dependency is ProjectDependency) {
                val dependentProject = dependency.dependencyProject
                if (dependencies.add(dependentProject)) {
                    collectDependencies(dependentProject)
                }
            }
        }
    }

    collectDependencies(this)
    return dependencies
}

// Корневая задача detekt, которая запускает проверку только для trade и его зависимостей
val detektAllTask = tasks.register("detektAll") {
    group = "verification"
    description = "Run detekt on trade module and its dependencies"
}

// Корневая задача test, которая запускает тесты только для trade и его зависимостей
val testAllTask = tasks.register("testAll") {
    group = "verification"
    description = "Run tests on trade module and its dependencies"
}

// Настроить зависимости после оценки всех проектов
gradle.projectsEvaluated {
    val tradeProject = project(":trade")
    val tradeDependencies = tradeProject.getAllProjectDependencies()
    val projectsToCheck = tradeDependencies + tradeProject

    // Настроить detektAll
    detektAllTask.configure {
        dependsOn(projectsToCheck.mapNotNull { it.tasks.findByName("detekt") })
    }

    // Настроить testAll
    testAllTask.configure {
        dependsOn(projectsToCheck.mapNotNull { it.tasks.findByName("test") })
    }
}

