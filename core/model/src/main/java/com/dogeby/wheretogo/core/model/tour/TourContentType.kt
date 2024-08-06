package com.dogeby.wheretogo.core.model.tour

enum class TourContentType(val id: String) {
    TouristSpot("12"),
    CulturalFacility("14"),
    Festival("15"),
    LeisureSport("28"),
    Shopping("38"),
    TravelCourse("25"),
    Accommodation("32"),
    Restaurant("39"),
    ;

    companion object {

        fun getDestinations() = listOf(
            TouristSpot,
            CulturalFacility,
            LeisureSport,
            Shopping,
            TravelCourse,
        )

        fun getAllExceptFestival() = entries.filter { it != Festival }
    }
}
