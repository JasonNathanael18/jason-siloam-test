package dependencies

import core.ModulesDep
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.addDiModule(configurationName:String = "implementation"){
    add(configurationName, project(ModulesDep.di))
}

fun DependencyHandler.addDomainModule(){
    add("implementation", project(ModulesDep.domain))
}

fun DependencyHandler.addDataModule(){
    add("implementation", project(ModulesDep.data))
}

fun DependencyHandler.addCommonModule(){
    add("implementation", project(ModulesDep.common))
}

fun DependencyHandler.addNavigationModule(){
    add("implementation", project(ModulesDep.navigation))
}

fun DependencyHandler.addFeatureModule(){
    featureModule.forEach {
        add("implementation", project(it))
    }
}