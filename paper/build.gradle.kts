plugins {
    `config-paper`
}

project.group = "${rootProject.group}.paper"

repositories {
    maven("https://repo.md-5.net/content/repositories/snapshots/")

    maven("https://ci.ender.zone/plugin/repository/everything/")

    maven("https://repo.glaremasters.me/repository/towny/")

    maven("https://repo.bg-software.com/repository/api/")
    
    maven("https://repo.bg-software.com/repository/api")

    maven("https://maven.enginehub.org/repo")

    maven("https://repo.oraxen.com/releases")
    configureArchRepository()
}

dependencies {
    implementation(libs.triumph.cmds)

    implementation(libs.fusion.paper)

    implementation(libs.metrics)

    compileOnly("net.william278.husktowns:Husktowns-paper:4.0.1")
    compileOnly(libs.informative.annotations)

    compileOnly(libs.vault) {
        exclude("org.bukkit", "bukkit")
    }

    compileOnly(libs.griefprevention)

    compileOnly(libs.worldguard)
    compileOnly(libs.worldedit)

    compileOnly(libs.kingdoms)

    compileOnly(libs.factions) {
        exclude("org.kitteh")
        exclude("org.spongepowered")
        exclude("com.darkblade12")
    }

    compileOnly(libs.plotsquared)

    compileOnly(libs.skyblock)

    compileOnly(libs.paster)

    compileOnly(libs.towny)

    compileOnly(libs.lands)

    compileOnly(libs.mcmmo)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        listOf(
            "org.bstats"
        ).forEach {
            relocate(it, "libs.$it")
        }
    }

    configurations.all { //todo() FIX ME later, fucking forced dependencies, give me a fucking break
        resolutionStrategy {
            force("org.apache.logging.log4j:log4j-bom:2.24.1")
            force("com.google.guava:guava:33.3.1-jre")
            force("com.google.code.gson:gson:2.11.0")
            force("it.unimi.dsi:fastutil:8.5.15")
        }
    }

    runPaper.folia.registerTask()

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")
        jvmArgs("-Dcom.mojang.eula.agree=true")

        downloadPlugins {
            modrinth("luckperms", "v5.5.0-bukkit")
        }

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }

    assemble {
        doLast {
            copy {
                from(shadowJar.get())
                into(rootProject.projectDir.resolve("jars"))
            }
        }
    }

    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
    }

    processResources {
        inputs.properties(mapOf(
            "name" to rootProject.name,
            "version" to project.version,
            "group" to "${project.group}.paper",
            "description" to project.description,
            "apiVersion" to libs.versions.minecraft.get(),
            "website" to "https://modrinth.com/plugin/crazyenchantments"
        ))

        filesMatching("plugin.yml") {
            expand(inputs.properties)
        }
    }
}

fun RepositoryHandler.configureArchRepository(dev: Boolean = false) {
    maven("${property("artifactory_contextUrl")}/gradle-${if (dev) "dev" else "release"}") {
        name = "arch"
        credentials {
            username = property("artifactory_user").toString()
            password = property("artifactory_password").toString()
        }
    }
}
