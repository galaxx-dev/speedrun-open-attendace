package com.arysugiarto.attendence.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

     /* How To Add Database
     *
     *
     @Singleton
     @Provides
     fun provideDatabase(
        @ApplicationContext context: Context
     ) = Room.databaseBuilder(
        context,
        PanintiDatabase::class.java,
        Const.Database.DATABASE_NAME
     ).fallbackToDestructiveMigration().build()
     *
     *
     */

    /* How To Add Dao
    *
    *
    @Singleton
    @Provides
    fun provideUserDao(database: PanintiDatabase) = database.userDao()
    *
    *
    */

}