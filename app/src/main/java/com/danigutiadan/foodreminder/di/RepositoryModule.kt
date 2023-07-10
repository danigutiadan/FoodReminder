package com.danigutiadan.foodreminder.di
import com.danigutiadan.foodreminder.features.dashboard.home.data.datasource.HomeDataSource
import com.danigutiadan.foodreminder.features.dashboard.home.data.repository.HomeRepositoryImpl
import com.danigutiadan.foodreminder.features.dashboard.home.domain.repository.HomeRepository
import com.danigutiadan.foodreminder.features.dashboard.profile.data.repository.ProfileRepositoryImpl
import com.danigutiadan.foodreminder.features.dashboard.profile.domain.repository.ProfileRepository
import com.danigutiadan.foodreminder.features.food_type.data.datasource.FoodTypeDataSource
import com.danigutiadan.foodreminder.features.food_type.data.repository.FoodTypeRepositoryImpl
import com.danigutiadan.foodreminder.features.food_type.domain.repository.FoodTypeRepository
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.data.datasource.AddUserInfoDataSource
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.data.repository.AddUserInfoRepositoryImpl
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.domain.repository.AddUserInfoRepository
import com.danigutiadan.foodreminder.features.onboarding.data.datasource.AuthDataSource
import com.danigutiadan.foodreminder.features.onboarding.signin.data.datasource.UserDataSource
import com.danigutiadan.foodreminder.features.onboarding.signin.data.repository.LoginRepositoryImpl
import com.danigutiadan.foodreminder.features.onboarding.signin.data.repository.UserRepositoryImpl
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository.LoginRepository
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository.UserRepository
import com.danigutiadan.foodreminder.features.onboarding.signup.data.repository.RegisterRepositoryImpl
import com.danigutiadan.foodreminder.features.onboarding.signup.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

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

    @Singleton
    @Provides
    fun provideFoodTypeRepository(foodTypeDataSource: FoodTypeDataSource): FoodTypeRepository = FoodTypeRepositoryImpl(foodTypeDataSource)
}