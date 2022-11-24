package com.example.morethanyesterday.record

data class RecordModel(

    //  운동이름
    var Name: String = "",

    // 운동타입
    var Type: String = "",

    // 총 세트 수
    var set : String = "",

    // 총 중량
    var kg : String = "",

    // 최고 중량
    var bestKg : String = "",

    // 총 횟수
    var count : String = ""

)