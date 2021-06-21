package com.programmer2704.stoxxwatch.app

import android.app.Application
import android.content.Context
import com.programmer2704.stoxxwatch.annotations.ApplicationContext
import com.programmer2704.stoxxwatch.data.AppDatabaseManager
import com.programmer2704.stoxxwatch.data.DatabaseManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val app: Application){


    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideDatabaseManager(@ApplicationContext context: Context):
            DatabaseManager = AppDatabaseManager(context)

}