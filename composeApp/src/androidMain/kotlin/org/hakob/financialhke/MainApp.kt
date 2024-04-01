package org.hakob.financialhke

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.hakob.financialhke.koin.coreModule
import com.hakob.financialhke.koin.initKoin
import com.hakob.financialhke.koin.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

class MainApp : Application() {

    var androidModule = module {

    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(coreModule() + platformModule)
        }
//        startKoin(
//
//            module {
//                single<Context> { this@MainApp }
////                viewModel { BreedViewModel(get(), get { parametersOf("BreedViewModel") }) }
//                single<SharedPreferences> {
//                    get<Context>().getSharedPreferences(
//                        "KAMPSTARTER_SETTINGS",
//                        Context.MODE_PRIVATE
//                    )
//                }
////                single<AppInfo> { AndroidAppInfo }
//                single {
//                    { Log.i("Startup", "Hello from Android/Kotlin!") }
//                }
//            }
//        )
    }
}

//object AndroidAppInfo : AppInfo {
//    override val appId: String = BuildConfig.APPLICATION_ID
//}
