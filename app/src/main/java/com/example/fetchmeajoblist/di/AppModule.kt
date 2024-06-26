package com.example.fetchmeajoblist.di

import com.example.fetchmeajoblist.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(Logging) {
                logger = Logger.ANDROID
                level = if (BuildConfig.DEBUG) {
                    LogLevel.ALL
                } else {
                    LogLevel.NONE
                }
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = BuildConfig.DEBUG
                })
            }
        }
    }
}
