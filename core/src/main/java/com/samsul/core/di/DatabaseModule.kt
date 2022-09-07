package com.samsul.core.di

import android.content.Context
import androidx.room.Room
import com.samsul.core.data.local.room.UserDao
import com.samsul.core.data.local.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase = Room.databaseBuilder(
        context,
        UserDatabase::class.java, "User.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideUserDao(database: UserDatabase): UserDao = database.userDao()
}