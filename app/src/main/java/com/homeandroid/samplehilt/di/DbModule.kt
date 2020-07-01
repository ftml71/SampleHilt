package com.homeandroid.samplehilt.di

import android.content.Context
import androidx.room.Room
import com.homeandroid.samplehilt.db.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {
    @Provides
    fun provideDatabase(
            @ApplicationContext appContext: Context
    ): AppDb {
        return Room.databaseBuilder(
                appContext,
                AppDb::class.java,
                "user.db"
        ).fallbackToDestructiveMigration().build()
    }
}