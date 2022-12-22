package com.example.morethanyesterday.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.morethanyesterday.AddExerciseModel
import com.example.morethanyesterday.ExerciseSetActivity
import com.example.morethanyesterday.databinding.FragmentRecordListBinding
import com.example.morethanyesterday.RecordLVAdapter
import com.example.morethanyesterday.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class RecordListFragment : Fragment() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentRecordListBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // 댓글(=본문+uid+시간) 목록
    private val AddExerciseList = mutableListOf<AddExerciseModel>()

    // 댓글의 키 목록
    private val AddExerciseKeyList = mutableListOf<String>()

    // 리스트뷰 어댑터 선언
    private lateinit var recordLVAdapter: RecordLVAdapter

    // 태그
    private val TAG = "RecordListFragment"

//    private val TAG = recordLVAdapter::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        // 뷰바인딩
        vBinding = FragmentRecordListBinding.inflate(inflater, container, false)

        // 리스트뷰 어댑터 연결(게시글 목록)
        recordLVAdapter = RecordLVAdapter(AddExerciseList)

        // 리스트뷰 어댑터 연결
        val lv : ListView = binding.RecordFragLV
        lv.adapter = recordLVAdapter

        // 모든 게시글 정보를 가져옴
        getExerciseListData()

        // 파이어베이스의 게시글 키를 기반으로 게시글 데이터(=제목+본문+uid+시간) 받아옴
        binding.RecordFragLV.setOnItemClickListener { parent, view, position, id ->

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(context, ExerciseSetActivity::class.java)

            // 글읽기 액티비티로 게시글의 키 값 전달
            intent.putExtra("key", AddExerciseKeyList[position])

            // 글읽기 액티비티 시작
            startActivity(intent)

        }

        // 뷰바인딩
        return binding.root

    }

    // 모든 게시글 정보를 가져옴
    private fun getExerciseListData() {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 게시글 목록 비움
                // -> 저장/삭제 마다 데이터 누적돼 게시글 중복으로 저장되는 것 방지
                AddExerciseList.clear()

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 로그
                    Log.d(TAG, "getBoardListData $dataModel")

                    // 아이템(=게시글)
                    val item = dataModel.getValue(AddExerciseModel::class.java)

                    // 게시글 목록에 아이템 넣음
                    AddExerciseList.add(item!!)

                    // 게시글 키 목록에 문자열 형식으로 변환한 키 넣음
                    AddExerciseKeyList.add(dataModel.key.toString())

                }
                // getPostData()와 달리 반복문임 -> 아이템'들'

                // 게시글 키 목록을 역순으로 출력
                AddExerciseKeyList.reverse()
                Log.d(TAG, "getBoardListData - boardKeyList $AddExerciseKeyList")

                // 게시글 목록도 역순 출력
                AddExerciseList.reverse()

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                recordLVAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        FBRef.userRef.child("temporary").addValueEventListener(postListener)

    }

}