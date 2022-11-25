package com.example.morethanyesterday


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.morethanyesterday.record.RecordLVAdapter
import com.example.morethanyesterday.record.RecordModel
import com.example.morethanyesterday.record.RecordWriteAcitivity
import com.example.morethanyesterday.databinding.ActivityMainBinding
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {


    private var selectedDate = ""

    // (전역변수) 바인딩 객체 선언
    private var vBinding: ActivityMainBinding? = null


    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언

    private val binding get() = vBinding!!

    // 게시글 키
    private lateinit var key: String

    // 댓글(=본문+uid+시간) 목록
    private val recordList = mutableListOf<RecordModel>()

    // 댓글의 키 목록
    private val recordKeyList = mutableListOf<String>()

    // 리스트뷰 어댑터 선언
    private lateinit var recordLVAdapter: RecordLVAdapter

    var userID: String = "userID"
    lateinit var fname: String
    lateinit var str: String
    lateinit var updateBtn: Button
    lateinit var deleteBtn: Button
    lateinit var saveBtn: Button
    lateinit var diaryTextView: TextView
    lateinit var diaryContent: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityMainBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        val intent = Intent(this, RecordWriteAcitivity::class.java)

        binding.title.text = "More than yesterday"
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var selectedDate = String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth)
            binding.diaryTextView.visibility = View.VISIBLE
            binding.goToWriteBtn.visibility = View.VISIBLE
//            binding.contextEditText.visibility = View.VISIBLE
            binding.diaryContent.visibility = View.INVISIBLE
            binding.updateBtn.visibility = View.INVISIBLE
            binding.deleteBtn.visibility = View.INVISIBLE
            binding.diaryTextView.text = selectedDate
//            binding.contextEditText.setText("")
            checkDay(year, month, dayOfMonth, userID)
            // RecordWriteAcitivity : 넘기고자 하는 Component
            intent.putExtra("Date", selectedDate)

        }
        // 리스트뷰 어댑터 연결(운동목록)
        recordLVAdapter = RecordLVAdapter(recordList)

        // 리스트뷰 어댑터 연결
        val lv : ListView = binding.mainLV
        lv.adapter = recordLVAdapter

        // 파이어베이스의 게시글 키를 기반으로 게시글 데이터(=제목+본문+uid+시간) 받아옴
        lv.setOnItemClickListener { parent, view, position, id ->

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(context, BoardReadActivity::class.java)

            // 글읽기 액티비티로 게시글의 키 값 전달
            intent.putExtra("key", recordList[position])

            // 글읽기 액티비티 시작
            startActivity(intent)

        }
        binding.goToWriteBtn.setOnClickListener {
            startActivity(intent)
//            saveDiary(fname)
//            binding.contextEditText.visibility = View.INVISIBLE
//            binding.goToWriteBtn.visibility = View.INVISIBLE
//            binding.updateBtn.visibility = View.VISIBLE
//            binding.deleteBtn.visibility = View.VISIBLE
//            str = binding.contextEditText.text.toString()
//            binding.diaryContent.text = str
//            binding.diaryContent.visibility = View.VISIBLE
        }
    }

    // 달력 내용 조회, 수정
    fun checkDay(cYear: Int, cMonth: Int, cDay: Int, userID: String) {
        //저장할 파일 이름설정
        fname = "" + userID + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"

        var fileInputStream: FileInputStream
        try {
            fileInputStream = openFileInput(fname)
            val fileData = ByteArray(fileInputStream.available())
            fileInputStream.read(fileData)
            fileInputStream.close()
            str = String(fileData)
//            binding.contextEditText.visibility = View.INVISIBLE
            binding.diaryContent.visibility = View.VISIBLE
            binding.diaryContent.text = str
            binding.goToWriteBtn.visibility = View.INVISIBLE
            binding.updateBtn.visibility = View.VISIBLE
            binding.deleteBtn.visibility = View.VISIBLE


            binding.updateBtn.setOnClickListener {
//                binding.contextEditText.visibility = View.VISIBLE
                binding.diaryContent.visibility = View.INVISIBLE
//                binding.contextEditText.setText(str)
                binding.goToWriteBtn.visibility = View.VISIBLE
                binding.updateBtn.visibility = View.INVISIBLE
                binding.deleteBtn.visibility = View.INVISIBLE
//                binding.diaryContent.text = binding.contextEditText.text
            }
            binding.deleteBtn.setOnClickListener {
                binding.diaryContent.visibility = View.INVISIBLE
                binding.updateBtn.visibility = View.INVISIBLE
                binding.deleteBtn.visibility = View.INVISIBLE
//                binding.contextEditText.setText("")
//                binding.contextEditText.visibility = View.VISIBLE
                binding.goToWriteBtn.visibility = View.VISIBLE
//                removeDiary(fname)
            }
            if (diaryContent.text == null) {
                diaryContent.visibility = View.INVISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                diaryTextView.visibility = View.VISIBLE
                saveBtn.visibility = View.VISIBLE
//                binding.contextEditText.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


//    // 달력 내용 제거
//    @SuppressLint("WrongConstant")
//    fun removeDiary(readDay: String?) {
//        var fileOutputStream: FileOutputStream
//        try {
//            fileOutputStream = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
//            val content = ""
//            fileOutputStream.write(content.toByteArray())
//            fileOutputStream.close()
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }


//    // 달력 내용 추가
//    @SuppressLint("WrongConstant")
//    fun saveDiary(readDay: String?) {
//        var fileOutputStream: FileOutputStream
//        try {
//            fileOutputStream = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
//            val content = binding.contextEditText.text.toString()
//            fileOutputStream.write(content.toByteArray())
//            fileOutputStream.close()
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
}