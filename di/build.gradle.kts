import dependencies.addTimberDependencies
plugins {
    plugins.`android-core-library`
}
android {
    namespace = "com.example.di"
}
dependencies {
    addTimberDependencies(configurationName = "api")
}