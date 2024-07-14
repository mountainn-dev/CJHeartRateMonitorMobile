package com.san.heartratemonitormobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.san.heartratemonitormobile.databinding.ItemReportBinding
import com.san.heartratemonitormobile.domain.model.ReportModel

class ReportAdapter(
    private val items: List<ReportModel>
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {
    inner class ReportViewHolder(
        private val binding: ItemReportBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            loadContent(position)
        }

        private fun loadContent(position: Int) {
            binding.txtThreshold.text = items[position].threshold.toString()
            binding.txtThresholdOver.text = String.format(
                THRESHOLD_OVER_MESSAGE,
                items[position].reportHeartRate - items[position].threshold)
            binding.txtName.text = items[position].name.get()
            binding.txtTodayReportCount.text = String.format(TODAY_REPORT_COUNT_MESSAGE, items[position].reportCountToday)
            binding.txtReportDate.text = items[position].reportDate.toString()
            binding.txtReportTime.text = items[position].reportTime.toString()
            binding.txtReportLatitude.text = items[position].locationLatitude.toString()
            binding.txtReportLongitude.text = items[position].locationLongitude.toString()
            binding.txtAction.text = items[position].action.actionName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ReportViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(position)
    }

    companion object {
        private const val THRESHOLD_OVER_MESSAGE = "+%d"
        private const val TODAY_REPORT_COUNT_MESSAGE = "오늘 신고 %d건"
    }
}