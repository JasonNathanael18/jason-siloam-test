import dependencies.addHiltDependencies
import dependencies.addNavigationDependencies
import dependencies.addNavigationModule
import dependencies.addTimberDependencies
plugins {
    plugins.`android-base-library`
}
android {
    namespace = "com.example.commonui"
}
dependencies {
    addTimberDependencies(configurationName = "api")
    addNavigationModule()
    addNavigationDependencies()
    addHiltDependencies()
}