
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

    // Mặc định chọn VietQR
    private var selectedMethodId: String? = "vietqr"

    inner class PaymentViewHolder(
        val binding: ItemPaymentMethodBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentViewHolder {
        val binding = ItemPaymentMethodBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaymentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PaymentViewHolder,
        position: Int
    ) {
        val method = methods[position]
        val isSelected = method.id == selectedMethodId

        holder.binding.apply {
            tvPaymentName.text = method.name
            ivLogo.setImageResource(method.iconRes)

            rbPayment.isChecked = isSelected

            ivSelected.visibility =
                if (isSelected) View.VISIBLE else View.GONE

            layoutSubIcons.visibility =
                if (method.subIcons.isNotEmpty()) View.VISIBLE
                else View.GONE

            root.setOnClickListener {
                val oldPosition = methods.indexOfFirst {
                    it.id == selectedMethodId
                }

                if (selectedMethodId == method.id) return@setOnClickListener

                selectedMethodId = method.id

                if (oldPosition != -1) {
                    notifyItemChanged(oldPosition)
                }

                notifyItemChanged(position)

                onMethodSelected(method)
            }

            rbPayment.setOnClickListener {
                root.performClick()
            }
        }
    }

    override fun getItemCount(): Int = methods.size

    fun setSelectedMethod(methodId: String?) {
        val oldPosition = methods.indexOfFirst {
            it.id == selectedMethodId
        }

        selectedMethodId = methodId

        val newPosition = methods.indexOfFirst {
            it.id == selectedMethodId
        }

        if (oldPosition != -1) {
            notifyItemChanged(oldPosition)
        }

        if (newPosition != -1 && newPosition != oldPosition) {
            notifyItemChanged(newPosition)
        }
    }
}
