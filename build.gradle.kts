plugins {
    id("root-plugin")
}

rootProject.group = "com.ryderbelserion.crazyenchantments"

val buildNumber: String? = System.getenv("BUILD_NUMBER")

rootProject.version = if (buildNumber != null) "${libs.versions.minecraft.get()}-$buildNumber" else "3.0.0"