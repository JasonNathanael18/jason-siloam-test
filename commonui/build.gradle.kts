import dependencies.*

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
    addDomainModule()
}