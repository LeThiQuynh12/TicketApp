package com.example.musicapp.presentation.payment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemPaymentMethodBinding
import com.example.musicapp.presentation.model.PaymentMethod

class PaymentMethodAdapter(
    private var methods: List<PaymentMethod>,
    private val onMethodSelected: (PaymentMethod) -> Unit
) : RecyclerView.Adapter<PaymentMethodAdapter.PaymentViewHolder>() {

    private var selectedPosition = 0

    inner class PaymentViewHolder(val binding: ItemPaymentMethodBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val binding = ItemPaymentMethodBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PaymentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val method = methods[position]
        holder.binding.apply {
            tvPaymentName.text = method.name
            ivLogo.setImageResource(method.iconRes)
            rbPayment.isChecked = position == selectedPosition

            if (method.subIcons.isNotEmpty()) {
                layoutSubIcons.visibility = View.VISIBLE
            } else {
                layoutSubIcons.visibility = View.GONE
            }

            root.setOnClickListener {
                val oldPos = selectedPosition
                selectedPosition = holder.adapterPosition
                notifyItemChanged(oldPos)
                notifyItemChanged(selectedPosition)
                onMethodSelected(method)
            }

            rbPayment.setOnClickListener { root.performClick() }
        }
    }

    override fun getItemCount() = methods.size
}