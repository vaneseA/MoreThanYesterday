package com.example.morethanyesterday.ui.fragments


import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.morethanyesterday.AddExerciseModel
import com.example.morethanyesterday.PrivateRecordModel
import com.example.morethanyesterday.R
import com.example.morethanyesterday.utils.FBRef


private lateinit var selectedDate: String

class ExerciseRVAdapter(
    val context: Context, // 컨텍스트
    val items: MutableList<AddExerciseModel> = mutableListOf(),
) : RecyclerView.Adapter<ExerciseRVAdapter.ViewHolder>() {


    // itemClickListener
    interface ItemClick {
        fun onClick(view: View, position: Int) {
        }
    }

    var itemClick: ItemClick? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val exerciseType = view.findViewById<TextView>(R.id.exerciseTypeArea)
        val exerciseName = view.findViewById<TextView>(R.id.exerciseNameArea)

    }
    // RecyclerView 에서 사용하는 View 홀더 클래스
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // 레이아웃 인플레이터 -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_exercise, parent, false)

        // 아직 데이터는 들어가있지 않은 껍데기
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.findViewById<LinearLayout>(R.id.exerciseArea).setOnClickListener { v ->
            itemClick?.onClick(v, position)
        }
        // 카드에 이름 세팅
        holder.exerciseName.text = items[position].name
        // 카드에 운동부위 세팅
        holder.exerciseType.text = items[position].type
//        val exercise = items[position]

//        val intent = Intent(context, AddExerciseActivity::class.java)

//        val intent = Intent(context, MainActivity::class.java)
        // 선택된 카드의 ID 정보를 intent 에 추가한다.
//        intent.putExtra("exerciseId", exercise.exerciseId)
//        var intentSecond = getIntent()
//        val selectedDate = intentSecond.getStringExtra("Date").toString()
//        Log.d("selectedDate_RV", selectedDate)

//        exerciseId = intent.getStringExtra("exerciseId").toString()





    }

    // 아이템들의 총 개수 반환
    override fun getItemCount(): Int = items.size


//    // 각 아이템에 데이터 넣어줌
//    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    private fun showDialog(
        name: String,
        Type: String,
        context: Context,
        selectedDate: String
    ) {

        // custom_dialog를 뷰 객체로 반환
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)

        // 대화상자 생성
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle(
                "${selectedDate}에" +
                        "\n${name}(${Type})\n이 운동을 추가하시겠습니까?"
            )

//         대화상자 띄움
        val alertDialog = builder.show()

        val yesBtn = alertDialog.findViewById<ConstraintLayout>(R.id.yesBtn)
        val noBtn = alertDialog.findViewById<ConstraintLayout>(R.id.noBtn)


        yesBtn.setOnClickListener {
            addExercise(Type, name, selectedDate)

            Log.d("selectedDate_RV_Yes", selectedDate)
            alertDialog.dismiss()

        }
        noBtn.setOnClickListener {
            alertDialog.dismiss()
            Toast.makeText(context, "취소되었습니다", Toast.LENGTH_SHORT).show()
        }

    }

    private fun addExercise(
        Type: String,
        Name: String,
        selectedDate: String
    ) {
        // 키 값 하위에 데이터 넣음
        FBRef.userRef
            .child(selectedDate)
            .child(Name)
            .setValue(PrivateRecordModel(Type, Name, selectedDate))

    }
}