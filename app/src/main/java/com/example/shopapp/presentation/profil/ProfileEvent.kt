package com.example.shopapp.presentation.profil

sealed class ProfileEvent {
    data class EnteredFirstName(val value: String): ProfileEvent()
    data class EnteredLastName(val value: String): ProfileEvent()
    data class EnteredStreet(val value: String): ProfileEvent()
    data class EnteredCity(val value: String): ProfileEvent()
    data class EnteredZipCode(val value: String): ProfileEvent()
    object Save: ProfileEvent()
}