package com.example.musicapp.presentation.myticket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemMyTicketBinding
import com.example.musicapp.presentation.model.MyTicket
import com.example.musicapp.presentation.model.TicketStatus
import java.text.DecimalFormat

class MyTicketAdapter(
    private var tickets: List<MyTicket>,
    private val onViewTicketClick: (MyTicket) -> Unit
) : RecyclerView.Adapter<MyTicketAdapter.TicketViewHolder>() {

    inner class TicketViewHolder(
        val binding: ItemMyTicketBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketViewHolder {
        val binding = ItemMyTicketBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TicketViewHolder,
        position: Int
    ) {
        val ticket = tickets[position]

        holder.binding.apply {
            tvEventName.text = ticket.eventName
            tvEventDate.text = ticket.eventDate
            tvEventLocation.text = ticket.eventLocation
            tvTicketCode.text = ticket.ticketCode

            val formatter = DecimalFormat("#,###")
            tvTicketPrice.text = "${formatter.format(ticket.price)} đ"

            Glide.with(ivEventImage)
                .load(ticket.imageUrl)
                .placeholder(R.drawable.img_city)
                .error(R.drawable.img_city)
                .centerCrop()
                .into(ivEventImage)

            when (ticket.status) {
                TicketStatus.UPCOMING -> {
                    tvTicketStatus.text = "SẮP DIỄN RA"
                    tvTicketStatus.setTextColor(
                        root.context.getColor(R.color.ticket_green)
                    )
                    tvTicketStatus.setBackgroundResource(
                        R.drawable.bg_ticket_status
                    )
                    btnViewTicket.visibility = View.VISIBLE
                    btnViewTicket.text = "Xem mã vé"
                    btnViewTicket.isEnabled = true
                }

                TicketStatus.USED -> {
                    tvTicketStatus.text = "ĐÃ SỬ DỤNG"
                    tvTicketStatus.setTextColor(
                        root.context.getColor(R.color.ticket_blue)
                    )
                    tvTicketStatus.setBackgroundResource(
                        R.drawable.bg_ticket_status_used
                    )
                    btnViewTicket.visibility = View.VISIBLE
                    btnViewTicket.text = "Xem chi tiết"
                    btnViewTicket.isEnabled = true
                }

                TicketStatus.CANCELLED -> {
                    tvTicketStatus.text = "ĐÃ HỦY"
                    tvTicketStatus.setTextColor(
                        root.context.getColor(R.color.ticket_red)
                    )
                    tvTicketStatus.setBackgroundResource(
                        R.drawable.bg_ticket_status_cancelled
                    )
                    btnViewTicket.visibility = View.GONE
                }
            }

            btnViewTicket.setOnClickListener {
                onViewTicketClick(ticket)
            }
        }
    }

    override fun getItemCount(): Int = tickets.size

    fun updateTickets(newTickets: List<MyTicket>) {
        tickets = newTickets
        notifyDataSetChanged()
    }
}
