package com.example.animationtransition_kotlin

import java.io.Serializable

data class RvData(
    val img: String,
    val name: String,
    val age: String
) : Serializable

/** (중요) RvData model을 Intent에 담아서 보낼려면 Serializable을 상속받아야함! */