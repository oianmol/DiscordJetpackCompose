package dev.baseio.discordjetpackcompose.ui.routes.dashboard.createServer

import dev.baseio.discordjetpackcompose.R

data class SampleServerTemplateModel(
    val textProvider: Int,
    val iconProvider: Int
)

object ServerTemplates {
    val type = listOf(
        SampleServerTemplateModel(
            textProvider = R.string.gaming,
            iconProvider = R.drawable.dark_app_logo
        ),
        SampleServerTemplateModel(
            textProvider = R.string.school_club,
            iconProvider = R.drawable.dark_app_logo
        ),
        SampleServerTemplateModel(
            textProvider = R.string.study_group,
            iconProvider = R.drawable.dark_app_logo
        ),
        SampleServerTemplateModel(
            textProvider = R.string.friends,
            iconProvider = R.drawable.dark_app_logo
        ),
        SampleServerTemplateModel(
            textProvider = R.string.artists_and_creators,
            iconProvider = R.drawable.dark_app_logo
        ),
        SampleServerTemplateModel(
            textProvider = R.string.local_community,
            iconProvider = R.drawable.dark_app_logo
        )
    )
}