import dependencies.addDiModule
import dependencies.addDomainModule
import dependencies.addRoomDependencies

plugins {
    plugins.`android-core-library`
}

android {
    namespace = "com.example.data"

}

dependencies {
    addDiModule()
    addDomainModule()
    addRoomDependencies()
}