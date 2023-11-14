package dependencies
import core.Dependencies

internal val androidUiDependencies = listOf(
    Dependencies.coreKtx,
    Dependencies.appcompat,
    Dependencies.material,
    Dependencies.constraintLayout,
    Dependencies.activity
)

internal val androidxLifeCycleDependencies = listOf(
    Dependencies.lifecycleViewModel,
    Dependencies.lifecycleLiveData,
    Dependencies.lifecycleCommon,
    Dependencies.lifecycleRuntime,
    Dependencies.lifecycleExtensions,
)

internal val coroutinesAndroidDependencies = listOf(
    Dependencies.kotlinCoroutines,
    Dependencies.kotlinCoroutinesCore
)

internal val networkDependencies = listOf(
    Dependencies.retrofit,
    Dependencies.moshiConverter,
    Dependencies.okhHttp3,
    Dependencies.okhHttp3Interceptor,
)

internal val navigationDependencies = listOf(
    Dependencies.navigationUi,
    Dependencies.navigationFragment,
    Dependencies.navigationFragmentKtx,
    Dependencies.fragment,
)



