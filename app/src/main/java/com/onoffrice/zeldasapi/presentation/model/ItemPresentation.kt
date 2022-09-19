package com.onoffrice.zeldasapi.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemPresentation(
    val category: String,
    val commonLocations: List<String>,
    val description: String,
    val drops: List<String>,
    val id: Int,
    val image: String,
    val name: String
): Parcelable