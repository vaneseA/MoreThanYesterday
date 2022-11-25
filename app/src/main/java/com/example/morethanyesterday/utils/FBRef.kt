package com.example.morethanyesterday.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

// 클래스 인스턴스 없이 클래스 내부에 접근하려면
class FBRef {

    // -> 클래스 내부에 객체 선언할 때 동반 객체로 선언
    companion object {

        // 파이어베이스
        private val database = Firebase.database

        // .getReference() -> 데이터베이스의 루트 폴더 주소 값을 반환

        // 운동데이터베이스
        val exerciseRef = database.getReference("exercise")

        // 유저데이터베이스
        val userRef = database.getReference("user")
    }


}