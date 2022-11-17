package com.example.morethanyesterday.record

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.morethanyesterday.databinding.ActivityRecordWriteBinding
import com.google.android.material.tabs.TabLayoutMediator

class RecordWriteAcitivity : AppCompatActivity() {


    private  val tabTitleArray = arrayOf(
        "전체",
        "등",
        "가슴",
        "이두",
        "삼두",
        "승모근",
        "어깨",
        "하체",
        "복근",
        "유산소"
    )

    // (전역변수) 바인딩 객체 선언
    private var vBinding: ActivityRecordWriteBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!


    lateinit var mAdapter : ViewPagerFragmentStateAdapter
    // 게시글 키
    private lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityRecordWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout,viewPager){tab,position ->
            tab.text = tabTitleArray[position]
        }.attach()




    }
}