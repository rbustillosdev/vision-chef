package dev.rbustillos.vision_chef.core.injection

import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rbustillos.vision_chef.BuildConfig
import dev.rbustillos.vision_chef.core.constants.MODEL_NAME
import dev.rbustillos.vision_chef.vision.data.remote.VisionDataSource
import dev.rbustillos.vision_chef.vision.data.repository.VisionRepositoryImpl
import dev.rbustillos.vision_chef.vision.domain.repository.VisionRepository
import dev.rbustillos.vision_chef.vision.domain.useCase.GetRecipeUseCase

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideGenerativeModel(): GenerativeModel = GenerativeModel(
        MODEL_NAME,
        BuildConfig.GOOGLE_VISION_KEY
    )

    @Provides
    fun provideVisionDataSource(generativeModel: GenerativeModel): VisionDataSource =
        VisionDataSource(generativeModel)

    @Provides
    fun provideVisionRepository(visionDataSource: VisionDataSource): VisionRepository =
        VisionRepositoryImpl(visionDataSource)

    @Provides
    fun provideGetRecipeUseCase(visionRepository: VisionRepository): GetRecipeUseCase =
        GetRecipeUseCase(visionRepository)
}