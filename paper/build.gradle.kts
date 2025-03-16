plugins {
    id("paper-plugin")

    alias(libs.plugins.runPaper)
    alias(libs.plugins.shadow)
}

project.group = "${rootProject.group}.paper"
project.version = rootProject.version
project.description = "Adds over 80 enchantments to your server!"

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    maven("https://repo.essentialsx.net/releases")
}

dependencies {
    compileOnly(libs.bundles.shared) {
        exclude("org.spigotmc", "spigot-api")
        exclude("org.bstats", "bstats-bukkit")
        exclude("org.bukkit", "bukkit")
    }

    implementation(libs.fusion.paper)

    compileOnly(libs.paper)
}

tasks {
    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }

    assemble {
        dependsOn(shadowJar)

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

        listOf(
            "com.ryderbelserion.fusion"
        ).forEach {
            relocate(it, "libs.$it")
        }
    }

    processResources {
        inputs.properties("name" to rootProject.name)
        inputs.properties("version" to project.version)
        inputs.properties("group" to project.group)
        inputs.properties("apiVersion" to libs.versions.minecraft.get())
        inputs.properties("description" to project.description)
        inputs.properties("website" to "https://modrinth.com/plugin/crazyenchantments")

        filesMatching("paper-plugin.yml") {
            expand(inputs.properties)
        }
    }
}