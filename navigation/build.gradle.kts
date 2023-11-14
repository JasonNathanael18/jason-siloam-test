import dependencies.addNavigationDependencies
import dependencies.addTimberDependencies
plugins {
    plugins.`android-core-library`
    id ("androidx.navigation.safeargs.kotlin")
}
android {
    namespace = "com.example.navigation"
}
dependencies {
    addTimberDependencies(configurationName = "api")
    addNavigationDependencies()
}