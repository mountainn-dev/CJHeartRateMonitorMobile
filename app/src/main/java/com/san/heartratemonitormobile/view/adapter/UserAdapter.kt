package com.san.heartratemonitormobile.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.san.heartratemonitormobile.R
import com.san.heartratemonitormobile.databinding.ItemUserBinding
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.utils.Const
import java.time.LocalDate

class UserAdapter(
    private val items: List<UserModel>,
    private val context: Context
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            loadContent(position)
            setContentColor(position)
        }

        private fun loadContent(position: Int) {
            binding.txtName.text = items[position].name.get()
            binding.txtGender.text = items[position].gender.genderName
            binding.txtAge.text = String.format(
                Companion.AGE_MESSAGE,
                (LocalDate.now().year - items[position].birth.get().year + 1))
            binding.txtLastHeartRate.text = items[position].lastHeartRate.toString()
            binding.txtTodayReportCount.text =
                String.format(TODAY_REPORT_COUNT_MESSAGE, items[position].reportCountToday)
        }

        private fun setContentColor(position: Int) {
            setTodayReportCountColor(position)
        }

        private fun setTodayReportCountColor(position: Int) {
            if (items[position].reportCountToday > Const.ZERO) {
                val color = ContextCompat.getColor(context, R.color.red)
                binding.txtTodayReportCount.setTextColor(color)
            } else {
                val color = ContextCompat.getColor(context, R.color.black)
                binding.txtTodayReportCount.setTextColor(color)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return UserViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(position)
    }

    companion object {
        private const val TODAY_REPORT_COUNT_MESSAGE = "오늘 신고 %d건"
        private const val AGE_MESSAGE = "%d세"
    }
}