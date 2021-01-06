package com.animall.assignment2.di

import android.content.Context
import com.animall.assignment2.BuildConfig
import com.animall.assignment2.api.BookDeserializer
import com.animall.assignment2.data.entities.Book
import com.animall.assignment2.data.local.AppDatabase
import com.animall.assignment2.data.local.BookDao
import com.animall.assignment2.data.remote.BookRemoteDataSource
import com.animall.assignment2.data.remote.GoogleBooksService
import com.animall.assignment2.data.repository.BookRepository
import com.animall.assignment2.util.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit{
        val levelType: HttpLoggingInterceptor.Level
        if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            levelType = HttpLoggingInterceptor.Level.BODY else levelType = HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(logging)

        val gson = GsonBuilder()
                .registerTypeAdapter(Book::class.java, BookDeserializer())
                //.registerTypeAdapter(BookListApiResponse::class.java, BookListApiResponseDeserializer())
                .create()


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }



    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideGoogleBooksService(retrofit: Retrofit): GoogleBooksService = retrofit.create(GoogleBooksService::class.java)

    @Singleton
    @Provides
    fun provideBookRemoteDataSource(googleBooksService: GoogleBooksService) = BookRemoteDataSource(googleBooksService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideBookDao(db: AppDatabase) = db.bookDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: BookRemoteDataSource,
                          localDataSource: BookDao
    ) =
        BookRepository(remoteDataSource, localDataSource)
}