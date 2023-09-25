package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.di

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.profile.domain.repository.ProfileRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.profile.domain.usecases.LogoutUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.FoodRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.DeleteFoodUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.GetAllFoodUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.GetFoodByIdUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.GetFoodInfoByBarcodeUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.GetFoodWithFiltersUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.SaveFoodUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.UpdateFoodStatusUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.UpdateFoodUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.repository.FoodTypeRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.usecases.GetAllFoodTypesUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.usecases.InsertFoodTypeUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.adduserinfo.domain.repository.AddUserInfoRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.adduserinfo.domain.usecases.UploadUserImageUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.repository.LoginRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.repository.UserRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.usecases.EmailLoginUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.usecases.GetUserInfoUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signup.domain.repository.RegisterRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signup.domain.usecases.EmailRegisterUseCase
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
    fun provideGetAllFoodUseCase(foodRepository: FoodRepository) =
        GetAllFoodUseCase(foodRepository = foodRepository)

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
    fun provideGetFoodInfoByBarcodeUseCase(foodRepository: FoodRepository) =
        GetFoodInfoByBarcodeUseCase(foodRepository = foodRepository)

    @Singleton
    @Provides
    fun provideSaveFoodUseCase(foodRepository: FoodRepository) =
        SaveFoodUseCase(foodRepository = foodRepository)

    @Singleton
    @Provides
    fun provideGetFoodByIdUseCase(foodRepository: FoodRepository) =
        GetFoodByIdUseCase(foodRepository = foodRepository)

    @Singleton
    @Provides
    fun provideDeleteFoodUseCase(foodRepository: FoodRepository) =
        DeleteFoodUseCase(foodRepository = foodRepository)

    @Singleton
    @Provides
    fun provideGetFoodWithFiltersUseCase(foodRepository: FoodRepository) =
        GetFoodWithFiltersUseCase(foodRepository = foodRepository)

    @Singleton
    @Provides
    fun provideUpdateFoodUseCase(foodRepository: FoodRepository) =
        UpdateFoodUseCase(foodRepository = foodRepository)

    @Singleton
    @Provides
    fun provideUpdateFoodStatusUseCase(foodRepository: FoodRepository) =
        UpdateFoodStatusUseCase(foodRepository = foodRepository)

}