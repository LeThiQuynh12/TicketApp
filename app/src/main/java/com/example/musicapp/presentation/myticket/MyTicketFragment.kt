package com.example.musicapp.presentation.myticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentMyTicketBinding
import com.example.musicapp.presentation.model.MyTicket
import com.example.musicapp.presentation.model.TicketStatus

class MyTicketFragment : Fragment() {

    private var _binding: FragmentMyTicketBinding? = null
    private val binding get() = _binding!!

    private lateinit var ticketAdapter: MyTicketAdapter

    private var selectedStatus = TicketStatus.UPCOMING

    private val allTickets = listOf(
        MyTicket(
            id = "1",
            eventName = "Đêm nhạc thanh xuân 2026",
            eventDate = "20:00 • 25/12/2026",
            eventLocation = "Nhà hát lớn Hà Nội",
            ticketCode = "TKT-2026-001",
            price = 350000,
            status = TicketStatus.UPCOMING,
            imageUrl = "https://ticketgo.vn/uploads/images/event-gallery/event_gallery-e9aefd90d73849faafad1bcabe25d131.jpg"
        ),
        MyTicket(
            id = "2",
            eventName = "EDM Music Festival",
            eventDate = "19:30 • 28/12/2026",
            eventLocation = "Công viên Yên Sở, Hà Nội",
            ticketCode = "TKT-2026-002",
            price = 500000,
            status = TicketStatus.UPCOMING,
            imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/04/c4/d7/1588077ab74e3427e132431a3c7ff92e.jpeg"
        ),
        MyTicket(
            id = "3",
            eventName = "The Colorful Concert",
            eventDate = "20:00 • 10/10/2026",
            eventLocation = "Trung tâm Hội nghị Quốc gia",
            ticketCode = "TKT-2026-003",
            price = 450000,
            status = TicketStatus.USED,
            imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/4a/9f/01/0eda208346be87ff2e4e9fe79bdf9083.jpg"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyTicketBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupTabs()
        updateSummary()
        filterTickets(TicketStatus.UPCOMING)
    }

    private fun setupRecyclerView() {
        ticketAdapter = MyTicketAdapter(emptyList()) { ticket ->
            onTicketClicked(ticket)
        }

        binding.rvMyTickets.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ticketAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun setupTabs() {
        binding.tvTabUpcoming.setOnClickListener {
            filterTickets(TicketStatus.UPCOMING)
        }

        binding.tvTabUsed.setOnClickListener {
            filterTickets(TicketStatus.USED)
        }

        binding.tvTabCancelled.setOnClickListener {
            filterTickets(TicketStatus.CANCELLED)
        }
    }

    private fun filterTickets(status: TicketStatus) {
        selectedStatus = status

        val filteredTickets = allTickets.filter {
            it.status == status
        }

        ticketAdapter.updateTickets(filteredTickets)

        binding.layoutEmpty.visibility =
            if (filteredTickets.isEmpty()) View.VISIBLE else View.GONE

        binding.rvMyTickets.visibility =
            if (filteredTickets.isEmpty()) View.GONE else View.VISIBLE

        updateTabStyles()
    }

    private fun updateTabStyles() {
        val tabs = listOf(
            binding.tvTabUpcoming,
            binding.tvTabUsed,
            binding.tvTabCancelled
        )

        val selectedTab = when (selectedStatus) {
            TicketStatus.UPCOMING -> binding.tvTabUpcoming
            TicketStatus.USED -> binding.tvTabUsed
            TicketStatus.CANCELLED -> binding.tvTabCancelled
        }

        tabs.forEach { tab ->
            val isSelected = tab == selectedTab

            tab.setBackgroundResource(
                if (isSelected) {
                    R.drawable.bg_ticket_tab_selected
                } else {
                    R.drawable.bg_ticket_tab_unselected
                }
            )

            tab.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (isSelected) {
                        R.color.ticket_tab_selected_text
                    } else {
                        R.color.ticket_tab_unselected_text
                    }
                )
            )

            tab.setTypeface(
                null,
                if (isSelected) android.graphics.Typeface.BOLD
                else android.graphics.Typeface.NORMAL
            )
        }
    }

    private fun updateSummary() {
        val total = allTickets.size
        val upcoming = allTickets.count {
            it.status == TicketStatus.UPCOMING
        }

        binding.tvTotalTickets.text = String.format("%02d", total)
        binding.tvUpcomingCount.text = String.format("%02d", upcoming)
    }

    private fun onTicketClicked(ticket: MyTicket) {
        if (ticket.status == TicketStatus.UPCOMING) {
            Toast.makeText(
                requireContext(),
                "Mở mã QR vé: ${ticket.ticketCode}",
                Toast.LENGTH_SHORT
            ).show()

            // TODO: Điều hướng sang màn hình chi tiết vé / mã QR.
        } else {
            Toast.makeText(
                requireContext(),
                "Mã vé: ${ticket.ticketCode}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
