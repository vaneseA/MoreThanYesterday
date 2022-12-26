package com.example.morethanyesterday.view.main


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.morethanyesterday.AddExerciseModel
import com.example.morethanyesterday.RecordLVAdapter
import com.example.morethanyesterday.databinding.ActivityMainBinding
import com.example.morethanyesterday.view.SelectedDateRecordActivity
import com.example.morethanyesterday.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {


    private var selectedDate = ""

    // (전역변수) 바인딩 객체 선언
    private var vBinding: ActivityMainBinding? = null


    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언

    private val binding get() = vBinding!!

    // 게시글 키
    private lateinit var key: String

    // 댓글(=본문+uid+시간) 목록
    private val AddExerciseList = mutableListOf<AddExerciseModel>()

    // 댓글의 키 목록
    private val AddExerciseKeyList = mutableListOf<String>()

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

        val intent = Intent(this, SelectedDateRecordActivity::class.java)

        binding.title.text = "More than yesterday"
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var selectedDate = String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth)
            binding.diaryTextView.visibility = View.VISIBLE
            binding.goToWriteBtn.visibility = View.VISIBLE
//            binding.contextEditText.visibility = View.VISIBLE
//            binding.diaryContent.visibility = View.INVISIBLE
            binding.diaryTextView.text = selectedDate
//            binding.contextEditText.setText("")
            checkDay(year, month, dayOfMonth, userID)
            // RecordWriteAcitivity : 넘기고자 하는 Component
             intent.putExtra("Date", selectedDate)
            getExerciseListData(selectedDate)
        }
        // 리스트뷰 어댑터 연결(운동목록)
        recordLVAdapter = RecordLVAdapter(AddExerciseList)
        // 리스트뷰 어댑터 연결
        val lv: ListView = binding.mainLV
        lv.adapter = recordLVAdapter

        //추가된 운동 출력

        // 파이어베이스의 게시글 키를 기반으로 게시글 데이터(=제목+본문+uid+시간) 받아옴
        binding.mainLV.setOnItemClickListener { parent, view, position, id ->

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(this, SelectedDateRecordActivity::class.java)

            // 운동세트액티비티의 키 값 전달
            intent.putExtra("key", AddExerciseKeyList[position])
            Log.d("key", AddExerciseKeyList[position])
            // 운동세트액티비티 시작
            startActivity(intent)


        }
        binding.goToWriteBtn.setOnClickListener {
            Log.d("goToWriteBtn", selectedDate)
            // 키 값 하위에 데이터 넣음
            startActivity(intent)
//            val intentSecond = Intent(this, AllFragment::class.java)
//            intentSecond.putExtra("Date", selectedDate)
//            startActivity(intentSecond)
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
//            binding.diaryContent.visibility = View.VISIBLE
//            binding.diaryContent.text = str
            binding.goToWriteBtn.visibility = View.INVISIBLE
//            binding.updateBtn.visibility = View.VISIBLE
//            binding.deleteBtn.visibility = View.VISIBLE


//            binding.updateBtn.setOnClickListener {
////                binding.contextEditText.visibility = View.VISIBLE
//                binding.diaryContent.visibility = View.INVISIBLE
////                binding.contextEditText.setText(str)
//                binding.goToWriteBtn.visibility = View.VISIBLE
//                binding.updateBtn.visibility = View.INVISIBLE
//                binding.deleteBtn.visibility = View.INVISIBLE
////                binding.diaryContent.text = binding.contextEditText.text
//            }
//            binding.deleteBtn.setOnClickListener {
//                binding.diaryContent.visibility = View.INVISIBLE
//                binding.updateBtn.visibility = View.INVISIBLE
//                binding.deleteBtn.visibility = View.INVISIBLE
////                binding.contextEditText.setText("")
////                binding.contextEditText.visibility = View.VISIBLE
//                binding.goToWriteBtn.visibility = View.VISIBLE
////                removeDiary(fname)
//            }
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
// 게시글 하나의 정보를 가져옴
// 모든 게시글 정보를 가져옴
    private fun getExerciseListData(selectedDate:String) {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 게시글 목록 비움
                // -> 저장/삭제 마다 데이터 누적돼 게시글 중복으로 저장되는 것 방지
                AddExerciseList.clear()

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for (dataModel in dataSnapshot.children) {

                    // 아이템(=게시글)
                    val item = dataModel.getValue(AddExerciseModel::class.java)

                    // 게시글 목록에 아이템 넣음
                    AddExerciseList.add(item!!)

                    // 게시글 키 목록에 문자열 형식으로 변환한 키 넣음
                    AddExerciseKeyList.add(dataModel.key.toString())
                    Log.d("keykey", dataModel.key.toString())
                }
                // getPostData()와 달리 반복문임 -> 아이템'들'

                // 게시글 키 목록을 역순으로 출력
                AddExerciseKeyList.reverse()

                // 게시글 목록도 역순 출력
                AddExerciseList.reverse()

//            // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                recordLVAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        FBRef.userRef.child(selectedDate).addValueEventListener(postListener)
        Log.d("selectedDate_Main", selectedDate)
    }
}