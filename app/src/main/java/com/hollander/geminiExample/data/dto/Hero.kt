package com.hollander.geminiExample.data.dto

data class Hero(
    val id: Int,
    val name: String,
    val localized_name: String,
    val primary_attr: String,
    val attack_type: String,
    val roles: List<String>
)
