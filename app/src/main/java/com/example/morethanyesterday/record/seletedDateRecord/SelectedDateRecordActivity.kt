package com.example.morethanyesterday.record.seletedDateRecord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.morethanyesterday.AddExerciseActivity
import com.example.morethanyesterday.R
import com.example.morethanyesterday.databinding.ActivityRecordWriteBinding
import com.example.morethanyesterday.databinding.ActivitySelectedDateRecordBinding
import com.example.morethanyesterday.record.RecordWriteAcitivity

class SelectedDateRecordActivity : AppCompatActivity() {
    // (전역변수) 바인딩 객체 선언
    private var vBinding: ActivitySelectedDateRecordBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivitySelectedDateRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var selectedDate =intent.getStringExtra("Date")

        //        MainActivity에서 RecordWriteAcitivity로 데이터값 받음
        binding.selectedDate.text = selectedDate
        binding.backToTheMainBtn.setOnClickListener {
            finish()
        }
        binding.exerciseRecordAddBtn.setOnClickListener{
            val intent = Intent(this, RecordWriteAcitivity::class.java)
        intent.putExtra("Date", selectedDate)
            startActivity(intent)
        }
    }
}