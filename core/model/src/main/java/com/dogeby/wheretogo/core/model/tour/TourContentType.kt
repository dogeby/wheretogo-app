package com.dogeby.wheretogo.core.model.tour

enum class TourContentType(val id: String) {
    TouristSpot("12"),
    CulturalFacility("14"),
    Festival("15"),
    TravelCourse("25"),
    LeisureSport("28"),
    Shopping("38"),
    Accommodation("32"),
    Restaurant("39"),
}

fun TourContentType.getDestination() = listOf(
    TourContentType.TouristSpot,
    TourContentType.CulturalFacility,
    TourContentType.LeisureSport,
    TourContentType.Shopping,
    TourContentType.TravelCourse,
)
