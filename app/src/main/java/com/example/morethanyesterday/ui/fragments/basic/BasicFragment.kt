package com.example.morethanyesterday.ui.fragments.basic

import androidx.databinding.ViewDataBinding
import com.example.morethanyesterday.R

abstract class BasicFragment<T : ViewDataBinding> : DataBindingBasicFragment<T>() {
    protected val RESULT_SUCCESS = 0
    protected val RESULT_FAILED = 1
    protected val RESULT_CANCELED = 2

    protected open fun initRecyclerView(){
        // 정리용 함수
    }

    protected open fun setOnClickListeners(){
        // 정리용 함수
    }

    override fun fragmentContainer(): Int = R.id.viewPager

//    protected fun setTagsToChipGroupChildren(
//        defaultGroup: SjTagGroupWithTags?,
//        groups: List<SjTagGroupWithTags>?,
//        isCheckedOperation: (SjTag) -> Boolean,
//        chipGroup: ChipGroup,
//        onCheckedChangeListener: CompoundButton.OnCheckedChangeListener
//    ) {
//        // clear chipGroup child
//        chipGroup.removeAllViews()
//
//        // add tags default group
//        if (defaultGroup != null) {
//            for (def in defaultGroup.tags) {
//                val chip = SjTagChip(context!!)
//                chip.setTagValue(TagValue(def))
//                chip.setCheckableMode(onCheckedChangeListener,isCheckedOperation(def))
//                chipGroup.addView(chip)
//                Log.d("태그 그룹","${chip.text} ${isCheckedOperation(def)}")
//            }
//        }
//
//        // add tags with group
//        if (groups != null) {
//            for (group in groups) {
//                for (tag in group.tags) {
//                    val chip = SjTagChip(context!!)
//                    chip.setTagValue(TagValue(tag, group.tagGroup.name))
//                    chip.setCheckableMode(onCheckedChangeListener,isCheckedOperation(tag))
//                    chipGroup.addView(chip)
//                    Log.d("태그 그룹","태그 ${chip.text} ${isCheckedOperation(tag)}")
//                }
//            }
//        }
//    }


}