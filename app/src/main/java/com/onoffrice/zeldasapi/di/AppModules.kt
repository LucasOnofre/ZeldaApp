package com.onoffrice.zeldasapi.di

import com.onoffrice.zeldasapi.data.api.ZeldaApiService
import com.onoffrice.zeldasapi.data.api.retrofit.HttpClient
import com.onoffrice.zeldasapi.data.api.retrofit.RetrofitClient
import com.onoffrice.zeldasapi.data.datasource.RemoteDataSource
import com.onoffrice.zeldasapi.data.datasource.RemoteDataSourceImpl
import com.onoffrice.zeldasapi.data.repository.CategoriesRepositoryImpl
import com.onoffrice.zeldasapi.domain.repository.CategoriesRepository
import com.onoffrice.zeldasapi.domain.usecase.GetCategoriesUseCase
import com.onoffrice.zeldasapi.domain.usecase.GetCategoryInfoUseCase
import com.onoffrice.zeldasapi.presentation.feature.categoryitems.CategoryItemsViewModel
import com.onoffrice.zeldasapi.presentation.feature.categories.CategoriesViewModel
import com.onoffrice.zeldasapi.presentation.feature.itemdetail.ItemDetailViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val BASE_URL = "https://botw-compendium.herokuapp.com/api/v2/"

val domainModules = module {
    factory { GetCategoryInfoUseCase(repository = get()) }
    factory { GetCategoriesUseCase(repository = get()) }
}

val presentationModules = module {
    viewModel { CategoryItemsViewModel(getCategoryInfoUseCase = get(), dispatcher = Dispatchers.IO) }
    viewModel { CategoriesViewModel(getCategoriesUseCase = get(), dispatcher = Dispatchers.IO) }
    viewModel { ItemDetailViewModel() }
}

val dataModules = module {
    factory<RemoteDataSource> { RemoteDataSourceImpl(service = get()) }
    factory<CategoriesRepository> { CategoriesRepositoryImpl(remoteDataSource = get()) }
}

val anotherModules = module {
    single { RetrofitClient(application = androidContext(), BASE_URL).newInstance() }
    single { HttpClient(get()) }
    factory { get<HttpClient>().create(ZeldaApiService::class.java) }
}