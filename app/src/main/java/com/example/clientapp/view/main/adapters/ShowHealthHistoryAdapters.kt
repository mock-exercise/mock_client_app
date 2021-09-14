package com.example.clientapp.view.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.databinding.ItemDeclareHistoryBinding
import com.example.connectorlibrary.enitity.Health

class ShowHealthHistoryAdapters :
    RecyclerView.Adapter<ShowHealthHistoryAdapters.HealthViewHolder>() {

    private var mOnItemClickListener: ((Health) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Health) -> Unit)?) {
        mOnItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<Health>() {
        override fun areItemsTheSame(oldItem: Health, newItem: Health): Boolean {
            return oldItem.health_id == newItem.health_id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Health, newItem: Health): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Health, newItem: Health): Any? {
            return super.getChangePayload(oldItem, newItem)
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
                mOnItemClickListener?.let { it1 -> it1(myHealth) }
            }
            binding.apply {
                health = myHealth
            }
        }
    }
}
