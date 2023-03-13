package com.example.morethanyesterday.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.morethanyesterday.AddExerciseModel
import com.example.morethanyesterday.databinding.ActivityAddExerciseBinding
import com.example.morethanyesterday.utils.FBRef
import com.google.firebase.database.FirebaseDatabase

class AddExerciseActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding: ActivityAddExerciseBinding? = null


    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언

    private val binding get() = vBinding!!

    private lateinit var exerciseId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityAddExerciseBinding.inflate(layoutInflater)
        exerciseId = FBRef.exerciseRef.push().key.toString()
        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시

        binding.exerciseSaveBtn.setOnClickListener {
            setExercise()
        }
        setContentView(binding.root)
    }

    // 작성한 글을 등록
    private fun setExercise() {
        // 운동의 데이터(운동종류, 운동이름)
        val type = binding.addExerciseType.text.toString()
        val name = binding.addExerciseName.text.toString()


        // 키 값 하위에 데이터 넣음
        FirebaseDatabase.getInstance().getReference("exercise")
            .child("all")
            .child(exerciseId)
            .setValue(AddExerciseModel(type, name, exerciseId))

        // 키 값 하위에 데이터 넣음
        FirebaseDatabase.getInstance().getReference("exercise")
            .child(exerciseTypeRule(type))
            .child(exerciseId)
            .setValue(AddExerciseModel(type, name, exerciseId))


        // 등록 확인 메시지 띄움
        Toast.makeText(this, "저장완료", Toast.LENGTH_SHORT).show()

        Log.d("ssss", type)
        // 글쓰기 액티비티 종료
        finish()


    }

    fun exerciseTypeRule(addExerciseType: String): String {
        if (addExerciseType == "등") {
            return "back"
        } else if (addExerciseType == "가슴") {
            return "chest"
        } else if (addExerciseType == "이두") {
            return "bicep"
        } else if (addExerciseType == "삼두") {
            return "triceps"
        } else if (addExerciseType == "어깨") {
            return "shoulder"
        } else if (addExerciseType == "복근") {
            return "abs"
        } else if (addExerciseType == "하체") {
            return "lowerBody"
        } else if (addExerciseType == "승모근") {
            return "trapezius"
        } else if (addExerciseType == "유산소") {
            return "cardio"
        } else
            return "오류"


    }
}