package com.example.morethanyesterday.record.fragments




import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.morethanyesterday.AddExerciseActivity
import com.example.morethanyesterday.AddExerciseModel
import com.example.morethanyesterday.R



private lateinit var exerciseId: String

class ExerciseAllRVAdapter(
    val context: Context, // 컨텍스트
    val items: MutableList<AddExerciseModel> = mutableListOf(),
) : RecyclerView.Adapter<ExerciseAllRVAdapter.Viewholder>() {
    // 리사이클러뷰의 어댑터 -> RecyclerView.Adapter를 상속해서 구현

    // RecyclerView 에서 사용하는 View 홀더 클래스
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseAllRVAdapter.Viewholder {
        // 레이아웃 인플레이터 -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_rv_item, parent, false)

        // 아직 데이터는 들어가있지 않은 껍데기
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: ExerciseAllRVAdapter.Viewholder, position: Int) {
        val exercise = items[position]

        val intent = Intent(context, AddExerciseActivity::class.java)
        // 선택된 카드의 ID 정보를 intent 에 추가한다.
        intent.putExtra("exerciseId", exercise.exerciseId)

        exerciseId = intent.getStringExtra("exerciseId").toString()
        val exerciseType = holder.itemView.findViewById<TextView>(R.id.exerciseTypeArea)
        val exerciseName = holder.itemView.findViewById<TextView>(R.id.exerciseNameArea)

        // 카드에 운동부위 세팅
        exerciseType.text = exercise.exerciseType
        // 카드에 이름 세팅
        exerciseName.text = exercise.exerciseName

        holder.itemView.setOnClickListener {
//            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener {

            return@setOnLongClickListener (true)
        }
    }

    // 아이템들의 총 개수 반환
    override fun getItemCount(): Int = items.size


    // 각 아이템에 데이터 넣어줌
    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}