package com.danigutiadan.foodreminder.di

import android.app.Application
import com.danigutiadan.foodreminder.Preferences
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

    //REPOSITORIES-------------------------
    @Singleton
    @Provides
    fun provideLoginRepository(authDataSource: AuthDataSource) : LoginRepository = LoginRepositoryImpl(authDataSource)

    @Singleton
    @Provides
    fun provideRegisterRepository(authDataSource: AuthDataSource) : RegisterRepository = RegisterRepositoryImpl(authDataSource)

    @Singleton
    @Provides
    fun provideAddUserInfoRepository(addUserInfoDataSource: AddUserInfoDataSource): AddUserInfoRepository = AddUserInfoRepositoryImpl(addUserInfoDataSource)

    @Singleton
    @Provides
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository = UserRepositoryImpl(userDataSource = userDataSource)

    @Singleton
    @Provides
    fun provideHomeRepository(homeDataSource: HomeDataSource): HomeRepository = HomeRepositoryImpl(homeDataSource = homeDataSource)

    @Singleton
    @Provides
    fun provideProfileRepository(homeDataSource: HomeDataSource): ProfileRepository = ProfileRepositoryImpl(homeDataSource = homeDataSource)

    //USECASES------------------------------
    @Singleton
    @Provides
    fun provideLoginUseCase(repository: LoginRepository) = EmailLoginUseCase(repository)

    @Singleton
    @Provides
    fun provideEmailRegisterUseCase(repository: RegisterRepository) = EmailRegisterUseCase(repository)

    @Singleton
    @Provides
    fun provideUploadUserImageUseCase(addUserInfoRepository: AddUserInfoRepository) = UploadUserImageUseCase(addUserInfoRepository)

    @Singleton
    @Provides
    fun provideGetUserInfoUseCase(userRepository: UserRepository) = GetUserInfoUseCase(userRepository = userRepository)

    @Singleton
    @Provides
    fun provideLogoutUseCase(profileRepository: ProfileRepository) = LogoutUseCase(profileRepository = profileRepository)


    //DATASOURCES---------------------------
    @Singleton
    @Provides
    fun provideAuthDataSource(auth: FirebaseAuth, db: FirebaseFirestore, preferences: Preferences) : AuthDataSource = AuthDataSourceImpl(auth, db, preferences)

    @Singleton
    @Provides
    fun provideAddUserInfoDataSource(db: FirebaseFirestore, storage: FirebaseStorage, preferences: Preferences): AddUserInfoDataSource = AddUserInfoDataSourceImpl(db, storage, preferences)

    @Singleton
    @Provides
    fun provideHomeDataSource(auth: FirebaseAuth, preferences: Preferences, placesClient: PlacesClient): HomeDataSource = HomeDataSourceImpl(auth = auth, preferences = preferences, placesClient = placesClient)

    @Singleton
    @Provides
    fun provideProfileDataSource(auth: FirebaseAuth, preferences: Preferences): ProfileDataSource = ProfileDataSourceImpl(auth = auth, preferences = preferences)

    @Singleton
    @Provides
    fun provideUserDataSource(db: FirebaseFirestore): UserDataSource = UserDataSourceImpl(db = db)

    //PREFERENCES---------------------------
    @Singleton
    @Provides
    fun providePreferences(app: Application) = Preferences(app)


}