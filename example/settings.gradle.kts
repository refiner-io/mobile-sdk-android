import de.fayard.refreshVersions.core.FeatureFlag.GRADLE_UPDATES
import de.fayard.refreshVersions.core.FeatureFlag.LIBS

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.60.3"
}

refreshVersions {
    featureFlags {
        enable(LIBS)
        disable(GRADLE_UPDATES)
    }
}

include( ":androidApp")
rootProject.name = "example"
