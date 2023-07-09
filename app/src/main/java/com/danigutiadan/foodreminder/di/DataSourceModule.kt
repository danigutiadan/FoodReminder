package com.danigutiadan.foodreminder.di

import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.database.FoodReminderDatabase
import com.danigutiadan.foodreminder.features.dashboard.home.data.datasource.HomeDataSource
import com.danigutiadan.foodreminder.features.dashboard.home.data.datasource.HomeDataSourceImpl
import com.danigutiadan.foodreminder.features.dashboard.profile.data.datasource.ProfileDataSource
import com.danigutiadan.foodreminder.features.dashboard.profile.data.datasource.ProfileDataSourceImpl
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.data.datasource.AddUserInfoDataSource
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.data.datasource.AddUserInfoDataSourceImpl
import com.danigutiadan.foodreminder.features.onboarding.data.datasource.AuthDataSource
import com.danigutiadan.foodreminder.features.onboarding.data.datasource.AuthDataSourceImpl
import com.danigutiadan.foodreminder.features.onboarding.signin.data.datasource.UserDataSource
import com.danigutiadan.foodreminder.features.onboarding.signin.data.datasource.UserDataSourceImpl
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    //DATASOURCES---------------------------
    @Singleton
    @Provides
    fun provideAuthDataSource(auth: FirebaseAuth, db: FirebaseFirestore, preferences: Preferences) : AuthDataSource = AuthDataSourceImpl(auth, db, preferences)

    @Singleton
    @Provides
    fun provideAddUserInfoDataSource(db: FirebaseFirestore, storage: FirebaseStorage, preferences: Preferences): AddUserInfoDataSource = AddUserInfoDataSourceImpl(db, storage, preferences)

    @Singleton
    @Provides
    fun provideHomeDataSource(auth: FirebaseAuth, preferences: Preferences, placesClient: PlacesClient, db: FoodReminderDatabase): HomeDataSource = HomeDataSourceImpl(auth = auth, preferences = preferences, placesClient = placesClient, db = db)

    @Singleton
    @Provides
    fun provideProfileDataSource(auth: FirebaseAuth, preferences: Preferences): ProfileDataSource = ProfileDataSourceImpl(auth = auth, preferences = preferences)

    @Singleton
    @Provides
    fun provideUserDataSource(db: FirebaseFirestore): UserDataSource = UserDataSourceImpl(db = db)
}