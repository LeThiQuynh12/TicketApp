package com.example.musicapp.presentation.home.ticket

import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemTicketBinding
import com.example.musicapp.presentation.model.Ticket

class TicketViewHolder(
    private val binding: ItemTicketBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ticket: Ticket, quantity: Int, onUpdate: (Int) -> Unit) {
        binding.tvTicketName.text = ticket.name
        binding.tvTicketPrice.text = "${ticket.price} đ"
        binding.tvQuantity.text = quantity.toString()

        binding.btnPlus.setOnClickListener {
            val newQty = quantity + 1
            onUpdate(newQty)
        }

        binding.btnMinus.setOnClickListener {
            if (quantity > 0) {
                val newQty = quantity - 1
                onUpdate(newQty)
            }
        }
    }
}