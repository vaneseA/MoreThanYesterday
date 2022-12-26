package com.example.morethanyesterday.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.morethanyesterday.AddExerciseActivity
import com.example.morethanyesterday.AddExerciseModel
import com.example.morethanyesterday.databinding.FragmentTricepsBinding
import com.example.morethanyesterday.record.RecordWriteAcitivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class TricepsFragment : Fragment() {
    // (전역변수) 바인딩 객체 선언
    private var vBinding: FragmentTricepsBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // 리사이클러뷰 어댑터 선언
    lateinit var rvAdapter: ExerciseRVAdapter

    val items: MutableList<AddExerciseModel> = mutableListOf()

    // 운동ID
    private lateinit var exerciseId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰바인딩
        vBinding = FragmentTricepsBinding.inflate(inflater, container, false)

        rvAdapter = ExerciseRVAdapter(requireContext(), items)


        // 명시적 인텐트 -> 다른 액티비티 호출
        val intent = Intent(context, RecordWriteAcitivity::class.java)

//        exerciseAllRVAdapter = ExerciseAllRVAdapter(exerciseList)
        val rv: RecyclerView = binding.tricepsRecyclerView
        rv.adapter = rvAdapter

        // 게시판 프래그먼트에서 게시글의 키 값을 받아옴
        exerciseId = intent.getStringExtra("exerciseId").toString()

        // RecyclerView 에 LayoutManager 설정
        rv.layoutManager = LinearLayoutManager(context)
        // 리사이클러뷰의 아이템을 역순으로 정렬하게 함
        (rv.layoutManager as LinearLayoutManager).reverseLayout = true
        // 리사이클려뷰의 아이템을 쌓는 순서를 끝부터 쌓게 함
        (rv.layoutManager as LinearLayoutManager).stackFromEnd = true

        getExerciseDataForMain()

        // 블로그 더보기 클릭 -> 블로그 프래그먼트로 이동
        binding.exerciseAddBtn.setOnClickListener {

            startActivity(Intent(context, AddExerciseActivity::class.java))
        }

        return binding.root
    }

    private fun getExerciseDataForMain() {
        FirebaseDatabase.getInstance().getReference("/exercise").child("/chest")
            .addChildEventListener(object : ChildEventListener {
                // 글이 추가된 경우
                override fun onChildAdded(snapshot: DataSnapshot, prevChildKey: String?) {
                    snapshot?.let { snapshot ->
                        // snapshop 의 데이터를 exercise 객체로 가져옴
                        val exercise = snapshot.getValue(AddExerciseModel::class.java)
                        exercise?.let {
                            // 새 글이 마지막 부분에 추가된 경우
                            if (prevChildKey == null) {
                                //글 목록을 저장하는 변수에 exercise 객체 추가
                                items.add(it)
                                // RecyclerView 의 adapter 에 글이 추가된 것을 알림
                                rvAdapter?.notifyItemInserted(items.size - 1)
                            } else {
                                // 글이 중간에 삽입된 경우 prevChildKey 로 한단계 앞의 데이터의 위치를 찾은 뒤 데이터를 추가한다.
                                val prevIndex = items.map { it.exerciseId }.indexOf(prevChildKey)
                                items.add(prevIndex + 1, exercise)
                                // RecyclerView 의 adapter 에 글이 추가된 것을 알림
                                rvAdapter?.notifyItemInserted(prevIndex + 1)
                            }
                        }
                    }
                }


                // 글이 변경된 경우
                override fun onChildChanged(snapshot: DataSnapshot, prevChildKey: String?) {
                    snapshot?.let { snapshot ->
                        // snapshop 의 데이터를 exercise 객체로 가져옴
                        val exercise = snapshot.getValue(AddExerciseModel::class.java)
                        exercise?.let { memo ->
                            // 글이 변경된 경우 글의 앞의 데이터 인덱스에 데이터를 변경한다.
                            val prevIndex = items.map { it.exerciseId }.indexOf(prevChildKey)
                            items[prevIndex + 1] = exercise
                            rvAdapter.notifyItemChanged(prevIndex + 1)
                        }
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    snapshot?.let {
                        // snapshot 의 데이터를 exercise 객체로 가져옴
                        val exercise = snapshot.getValue(AddExerciseModel::class.java)
                        //
                        exercise?.let { memo ->
                            // 기존에 저장된 인덱스를 찾아서 해당 인덱스의 데이터를 삭제한다.
                            val existIndex =
                                items.map { it.exerciseId }.indexOf(exercise.exerciseId)
                            items.removeAt(existIndex)
                            rvAdapter?.notifyItemRemoved(existIndex)
                        }
                    }
                }

                // 글의 순서가 이동한 경우
                override fun onChildMoved(snapshot: DataSnapshot, prevChildKey: String?) {
                    // snapshot
                    snapshot?.let {
                        // snapshop 의 데이터를 Post 객체로 가져옴
                        val exercise = snapshot.getValue(AddExerciseModel::class.java)
                        exercise?.let { memo ->
                            // 기존의 인덱스를 구한다
                            val existIndex =
                                items.map { it.exerciseId }.indexOf(exercise.exerciseId)
                            // 기존에 데이터를 지운다.
                            items.removeAt(existIndex)
                            rvAdapter?.notifyItemRemoved(existIndex)
                            // prevChildKey 가 없는 경우 맨마지막으로 이동 된 것
                            if (prevChildKey == null) {
                                items.add(memo)
                                rvAdapter?.notifyItemChanged(items.size - 1)
                            } else {
                                // prevChildKey 다음 글로 추가
                                val prevIndex = items.map { it.exerciseId }.indexOf(prevChildKey)
                                items.add(prevIndex + 1, memo)
                                rvAdapter?.notifyItemChanged(prevIndex + 1)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // 취소가 된경우 에러를 로그로 보여준다
                    error?.toException()?.printStackTrace()
                }
            })
    }

    // 액티비티 파괴시
    override fun onDestroy() {

        // 바인딩 클래스 인스턴스 참조를 정리 -> 메모리 효율이 좋아짐
        vBinding = null
        super.onDestroy()

    }

}