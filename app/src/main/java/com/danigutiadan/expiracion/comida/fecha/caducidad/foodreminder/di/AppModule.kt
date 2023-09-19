package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.di

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.FoodReminderNavigator
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.Preferences
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.api.ApiService
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.database.FoodReminderDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val RETROFIT_URL = "https://world.openfoodfacts.org/api/v2/"

    //FIREBASE
    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseStorage() = Firebase.storage



    //PREFERENCES---------------------------
    @Singleton
    @Provides
    fun providePreferences(app: Application) = Preferences(app)

    @Singleton
    @Provides
    fun provideDatabase(app: Application) = FoodReminderDatabase.getInstance(app)

    //RETROFIT------------------------------

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RETROFIT_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideNavigator(app: Application): FoodReminderNavigator = FoodReminderNavigator(app)

    @Singleton
    @Provides
    fun provideAlarmManager(app: Application) = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager
}