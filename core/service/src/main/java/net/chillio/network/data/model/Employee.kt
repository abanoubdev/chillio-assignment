package net.chillio.network.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    val biography: String,
    val email_address: String,
    val employee_type: String,
    val full_name: String,
    val phone_number: String,
    val photo_url_large: String,
    val photo_url_small: String,
    val team: String,
    val uuid: String
)