package com.example.morethanyesterday.record

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.morethanyesterday.AddExerciseModel
import com.example.morethanyesterday.R

class RecordLVAdapter(val recordList: MutableList<AddExerciseModel>) : BaseAdapter() {



    // 아이템 총 개수 반환
    override fun getCount(): Int = recordList.size

    // 아이템 반환
    override fun getItem(position: Int): Any = recordList[position]

    // 아이템의 아이디 반환
    override fun getItemId(position: Int): Long = position.toLong()

    // 아이템을 표시할 뷰 반환
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        // 레이아웃 인플레이터 -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        view = LayoutInflater.from(parent?.context).inflate(R.layout.record_lv_item, parent, false)

        // 운동이름,총 세트, 총 중량, 최고 중량, 총 횟수 영역에

        val exerciseType = view.findViewById<TextView>(R.id.TypeArea)
        val exerciseName = view.findViewById<TextView>(R.id.NameArea)
//        val exerciseSet = view.findViewById<TextView>(R.id.exerciseSetArea)
//        val exerciseKg = view.findViewById<TextView>(R.id.exerciseKgArea)
//        val exerciseBestKg = view.findViewById<TextView>(R.id.exerciseBestKgArea)
//        val exerciseCount = view.findViewById<TextView>(R.id.exerciseCountArea)



        // 운동이름,총 세트, 총 중량, 최고 중량, 총 횟수 넣음
        exerciseType!!.text = recordList[position].type
        exerciseName!!.text = recordList[position].name
//        exerciseSet!!.text = recordList[position].set
//        exerciseKg!!.text = recordList[position].kg
//        exerciseBestKg!!.text = recordList[position].bestKg
//        exerciseCount!!.text = recordList[position].count

        // 뷰 반환
        return view!!

    }

}
