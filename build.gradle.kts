import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jlleitschuh.ktlint)
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.secrets) apply false
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.jlleitschuh.ktlint.get().pluginId)
    }
    ktlint {
        reporters {
            reporter(ReporterType.CHECKSTYLE)
        }
    }
}
