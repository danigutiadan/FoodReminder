package com.danigutiadan.foodreminder.di

import com.danigutiadan.foodreminder.features.add_food.domain.repository.AddFoodRepository
import com.danigutiadan.foodreminder.features.add_food.domain.usecases.GetFoodInfoByBarcodeUseCase
import com.danigutiadan.foodreminder.features.add_food.domain.usecases.SaveFoodUseCase
import com.danigutiadan.foodreminder.features.dashboard.home.domain.repository.HomeRepository
import com.danigutiadan.foodreminder.features.dashboard.home.domain.usecases.GetAllFoodUseCase
import com.danigutiadan.foodreminder.features.dashboard.profile.domain.repository.ProfileRepository
import com.danigutiadan.foodreminder.features.dashboard.profile.domain.usecases.LogoutUseCase
import com.danigutiadan.foodreminder.features.food_type.domain.repository.FoodTypeRepository
import com.danigutiadan.foodreminder.features.food_type.domain.usecases.GetAllFoodTypesUseCase
import com.danigutiadan.foodreminder.features.food_type.domain.usecases.InsertFoodTypeUseCase
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.domain.repository.AddUserInfoRepository
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.domain.usecases.UploadUserImageUseCase
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository.LoginRepository
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository.UserRepository
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.usecases.EmailLoginUseCase
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.usecases.GetUserInfoUseCase
import com.danigutiadan.foodreminder.features.onboarding.signup.domain.repository.RegisterRepository
import com.danigutiadan.foodreminder.features.onboarding.signup.domain.usecases.EmailRegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    //USECASES------------------------------
    @Singleton
    @Provides
    fun provideLoginUseCase(repository: LoginRepository) = EmailLoginUseCase(repository)

    @Singleton
    @Provides
    fun provideEmailRegisterUseCase(repository: RegisterRepository) =
        EmailRegisterUseCase(repository)

    @Singleton
    @Provides
    fun provideUploadUserImageUseCase(addUserInfoRepository: AddUserInfoRepository) =
        UploadUserImageUseCase(addUserInfoRepository)

    @Singleton
    @Provides
    fun provideGetUserInfoUseCase(userRepository: UserRepository) =
        GetUserInfoUseCase(userRepository = userRepository)

    @Singleton
    @Provides
    fun provideLogoutUseCase(profileRepository: ProfileRepository) =
        LogoutUseCase(profileRepository = profileRepository)

    @Singleton
    @Provides
    fun provideGetAllFoodUseCase(homeRepository: HomeRepository) =
        GetAllFoodUseCase(homeRepository = homeRepository)

    @Singleton
    @Provides
    fun provideInsertFoodTypeUseCase(foodTypeRepository: FoodTypeRepository) =
        InsertFoodTypeUseCase(foodTypeRepository = foodTypeRepository)

    @Singleton
    @Provides
    fun provideGetAllFoodTypesUseCase(foodTypeRepository: FoodTypeRepository) =
        GetAllFoodTypesUseCase(foodTypeRepository = foodTypeRepository)

    @Singleton
    @Provides
    fun provideGetFoodInfoByBarcodeUseCase(addFoodRepository: AddFoodRepository) =
        GetFoodInfoByBarcodeUseCase(addFoodRepository = addFoodRepository)

    @Singleton
    @Provides
    fun provideSaveFoodUseCase(addFoodRepository: AddFoodRepository) =
        SaveFoodUseCase(addFoodRepository = addFoodRepository)


}