package com.example.clientapp.view.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.databinding.ItemDeclareHistoryBinding
import com.example.clientapp.utils.Constant.HealthyIndex
import com.example.connectorlibrary.enitity.Health
import com.example.connectorlibrary.enitity.Status
import com.example.connectorlibrary.enitity.Symptom

class ShowHealthHistoryAdapters :
    RecyclerView.Adapter<ShowHealthHistoryAdapters.HealthViewHolder>() {

    private var mOnItemClickListener: ((Health) -> Unit)? = null
    private var mLiSymptom = listOf<Symptom>()
    private var mLiStatus = listOf<String>()

    fun setOnItemClickListener(listener: ((Health) -> Unit)?) {
        mOnItemClickListener = listener
    }

    fun getListSymptom(liSymptom: List<Symptom>){
        mLiSymptom = liSymptom
    }

    fun getListStatus(liStatus: List<Status>){
        mLiStatus = liStatus.map { it.status_name }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Health>() {
        override fun areItemsTheSame(oldItem: Health, newItem: Health): Boolean {
            return oldItem.health_id == newItem.health_id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Health, newItem: Health): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthViewHolder =
        HealthViewHolder(
            ItemDeclareHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: HealthViewHolder, position: Int) {
        holder.bindData(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class HealthViewHolder(private val binding: ItemDeclareHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(myHealth: Health) {
            binding.root.setOnClickListener {
                mOnItemClickListener?.let {  it(myHealth) }
            }
            binding.apply {
                health = myHealth
                txtListSymptomName.text = getSymptomText(myHealth)
                isHealthy = isHealthy(myHealth)
                status = mLiStatus
            }
        }
    }

    private fun getSymptomText(health: Health): String{
        val liSymptomId = health.list_symptom_id
        val liUserSymptomName = mLiSymptom.filter { liSymptomId.contains(it.symptom_id - 1) }.map {it.symptom_name }
        return liUserSymptomName.joinToString()
    }

    private fun isHealthy(health: Health): Boolean{
        return health.list_symptom_id.contains(HealthyIndex)
    }
}
