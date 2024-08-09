package com.san.heartratemonitormobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.san.heartratemonitormobile.databinding.ItemReportBinding
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.view.listener.ItemClickEventListener

class ReportAdapter(
    private val items: List<ReportModel>,
    private val itemClickEventListener: ItemClickEventListener
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {
    inner class ReportViewHolder(
        private val binding: ItemReportBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            loadContent(position)
            setItemClickEventListener(position)
        }

        private fun loadContent(position: Int) {
            binding.txtThreshold.text = items[position].threshold.toString()
            val thresholdDiff = items[position].reportHeartRate - items[position].threshold
            binding.txtThresholdOver.text = if (items[position].reportHeartRate >= items[position].threshold) String.format(
                THRESHOLD_OVER_MESSAGE, thresholdDiff) else String.format(THRESHOLD_UNDER_MESSAGE, thresholdDiff)
            binding.txtName.text = items[position].name.get()
            binding.txtTodayReportCount.text = String.format(TODAY_REPORT_COUNT_MESSAGE, items[position].reportCountToday)
            binding.txtReportDate.text = items[position].reportDate.toString()
            binding.txtReportTime.text = items[position].reportTime.toString()
            binding.txtReportLatitude.text = items[position].locationLatitude.toString()
            binding.txtReportLongitude.text = items[position].locationLongitude.toString()
            binding.txtAction.text = items[position].action.actionName
        }

        private fun setItemClickEventListener(position: Int) {
            binding.itemReport.setOnClickListener {
                itemClickEventListener.onItemClickListener(position)
            }
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
        private const val THRESHOLD_UNDER_MESSAGE = "%d"
        private const val TODAY_REPORT_COUNT_MESSAGE = "(오늘 신고 %d건)"
    }
}