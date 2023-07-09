package com.danigutiadan.foodreminder.di

import android.app.Application
import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.database.FoodReminderDatabase
import com.danigutiadan.foodreminder.features.dashboard.home.data.datasource.HomeDataSource
import com.danigutiadan.foodreminder.features.dashboard.home.data.datasource.HomeDataSourceImpl
import com.danigutiadan.foodreminder.features.dashboard.home.data.repository.HomeRepositoryImpl
import com.danigutiadan.foodreminder.features.dashboard.home.domain.repository.HomeRepository
import com.danigutiadan.foodreminder.features.dashboard.profile.data.datasource.ProfileDataSource
import com.danigutiadan.foodreminder.features.dashboard.profile.data.datasource.ProfileDataSourceImpl
import com.danigutiadan.foodreminder.features.dashboard.profile.data.repository.ProfileRepositoryImpl
import com.danigutiadan.foodreminder.features.dashboard.profile.domain.repository.ProfileRepository
import com.danigutiadan.foodreminder.features.dashboard.profile.domain.usecases.LogoutUseCase
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.data.datasource.AddUserInfoDataSource
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.data.datasource.AddUserInfoDataSourceImpl
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.data.repository.AddUserInfoRepositoryImpl
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.domain.repository.AddUserInfoRepository
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.domain.usecases.UploadUserImageUseCase
import com.danigutiadan.foodreminder.features.onboarding.data.datasource.AuthDataSource
import com.danigutiadan.foodreminder.features.onboarding.data.datasource.AuthDataSourceImpl
import com.danigutiadan.foodreminder.features.onboarding.signin.data.datasource.UserDataSource
import com.danigutiadan.foodreminder.features.onboarding.signin.data.datasource.UserDataSourceImpl
import com.danigutiadan.foodreminder.features.onboarding.signin.data.repository.LoginRepositoryImpl
import com.danigutiadan.foodreminder.features.onboarding.signin.data.repository.UserRepositoryImpl
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository.LoginRepository
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository.UserRepository
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.usecases.EmailLoginUseCase
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.usecases.GetUserInfoUseCase
import com.danigutiadan.foodreminder.features.onboarding.signup.data.repository.RegisterRepositoryImpl
import com.danigutiadan.foodreminder.features.onboarding.signup.domain.repository.RegisterRepository
import com.danigutiadan.foodreminder.features.onboarding.signup.domain.usecases.EmailRegisterUseCase
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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

    @Singleton
     @Provides
    fun  providePlacesClient(app: Application) = Places.createClient(app)



    //PREFERENCES---------------------------
    @Singleton
    @Provides
    fun providePreferences(app: Application) = Preferences(app)

    @Singleton
    @Provides
    fun provideDatabase(app: Application) = FoodReminderDatabase.getInstance(app)


}