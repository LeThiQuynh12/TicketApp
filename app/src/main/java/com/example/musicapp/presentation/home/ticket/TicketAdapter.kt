package com.example.musicapp.presentation.home.ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemTicketBinding
import com.example.musicapp.presentation.model.Ticket

class TicketAdapter(
    private var tickets: List<Ticket>,
    private val onQuantityChanged: (Ticket, Int) -> Unit
) : RecyclerView.Adapter<TicketViewHolder>() {

    private val selectedQuantities = mutableMapOf<String, Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ItemTicketBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = tickets[position]
        val currentQty = selectedQuantities[ticket.id] ?: 0

        holder.bind(ticket, currentQty) { newQty ->
            selectedQuantities[ticket.id] = newQty
            onQuantityChanged(ticket, newQty)
        }
    }

    override fun getItemCount(): Int = tickets.size

    fun updateData(newTickets: List<Ticket>) {
        this.tickets = newTickets
        notifyDataSetChanged()
    }
}