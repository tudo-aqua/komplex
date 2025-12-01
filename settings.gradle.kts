plugins {
    id("com.gradle.develocity") version "4.2.2"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "komplex"

develocity {
    buildScan {
        val isCI = System.getenv("CI").isNullOrEmpty().not()
        publishing.onlyIf { isCI }
        if (isCI) {
            tag("CI")
            uploadInBackground = false
            termsOfUseUrl = "https://gradle.com/help/legal-terms-of-use"
            termsOfUseAgree = "yes"
        }
    }
}