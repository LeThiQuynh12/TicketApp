package com.example.musicapp.presentation.home.ticket

import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemTicketBinding
import com.example.musicapp.presentation.model.Ticket

class TicketViewHolder(
    private val binding: ItemTicketBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ticket: Ticket, quantity: Int, onUpdate: (Int) -> Unit) {
        var currentQuantity = quantity

        binding.tvTicketName.text = ticket.name
        binding.tvTicketPrice.text = "${ticket.price} đ"
        binding.tvQuantity.text = currentQuantity.toString()

        updateQuantityButtons(currentQuantity)

        binding.btnPlus.setOnClickListener {
            currentQuantity++

            binding.tvQuantity.text = currentQuantity.toString()
            updateQuantityButtons(currentQuantity)

            onUpdate(currentQuantity)
        }

        binding.btnMinus.setOnClickListener {
            if (currentQuantity > 0) {
                currentQuantity--

                binding.tvQuantity.text = currentQuantity.toString()
                updateQuantityButtons(currentQuantity)

                onUpdate(currentQuantity)
            }
        }
    }

    private fun updateQuantityButtons(quantity: Int) {
        binding.btnMinus.isEnabled = quantity > 0
        binding.btnMinus.alpha = if (quantity > 0) 1f else 0.4f
    }
}