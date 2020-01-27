package i.krishnasony.customizeorders.di

import i.krishnasony.customizeorders.CustomizeOrderApplication
import i.krishnasony.customizeorders.network.ApiService
import i.krishnasony.customizeorders.viewModel.OrderViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module{
    viewModel {
        OrderViewModel()
    }
    single {
       androidContext() as CustomizeOrderApplication
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.myjson.com/bins/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single {
        get<Retrofit>().create<ApiService>(ApiService::class.java)
    }

    single {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        builder.networkInterceptors().add(httpLoggingInterceptor)
        builder.readTimeout(300, TimeUnit.SECONDS)
        builder.connectTimeout(300, TimeUnit.SECONDS)
        builder.build()
    }
}

