package com.samsul.favorite.di

import android.content.Context
import com.samsul.capstoneproject.di.MapsModuleDependencies
import com.samsul.favorite.ui.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [MapsModuleDependencies::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: MapsModuleDependencies): Builder
        fun build(): FavoriteComponent
    }

}