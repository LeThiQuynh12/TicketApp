package com.example.musicapp.presentation.home.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentTicketSelectionBinding
import com.example.musicapp.presentation.model.Ticket
import java.text.DecimalFormat

class TicketSelectionFragment : Fragment() {
    private var _binding: FragmentTicketSelectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var ticketAdapter: TicketAdapter

    private val selectedTicketsMap = mutableMapOf<String, Pair<Ticket, Int>>()
    private val currencyFormatter = DecimalFormat("#,###")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSelectTicket.setOnClickListener {
            val ticketsToSend = selectedTicketsMap.values.map { pair ->
                val ticket = pair.first
                ticket.quantity = pair.second
                ticket
            }.toTypedArray()

            val action = TicketSelectionFragmentDirections
                .actionTicketSelectionFragmentToCheckoutFragment(ticketsToSend)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        val mockTickets = listOf(
            Ticket("1", "A Lie - Lời Nói Dối", "700.000"),
            Ticket("2", "The Unsaid - Những Điều Ta Chưa Nói", "900.000"),
            Ticket("3", "Nostalgia - Hoài Niệm", "1.200.000"),
            Ticket("4", "The Truth - Sự Thật", "1.900.000")
        )

        ticketAdapter = TicketAdapter(mockTickets) { ticket, quantity ->
            if (quantity > 0) {
                selectedTicketsMap[ticket.id] = Pair(ticket, quantity)
            } else {
                selectedTicketsMap.remove(ticket.id)
            }
            updateUIState()
        }

        binding.rvTickets.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ticketAdapter
        }
    }

    private fun updateUIState() {
        val totalQuantity = selectedTicketsMap.values.sumOf { it.second }

        if (totalQuantity > 0) {
            binding.cvSelectedTickets.visibility = View.VISIBLE
            updateTicketSummaryDetails()

            binding.btnSelectTicket.apply {
                isEnabled = true
                text = "Tiếp tục ($totalQuantity vé)"
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        } else {
            binding.cvSelectedTickets.visibility = View.GONE

            binding.btnSelectTicket.apply {
                isEnabled = false
                text = "Vui lòng chọn vé"
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    private fun updateTicketSummaryDetails() {
        val detailsBuilder = StringBuilder()
        var totalPriceSum = 0L

        selectedTicketsMap.values.forEach { (ticket, qty) ->
            detailsBuilder.append("${qty}x ${ticket.name}\n")

            val priceValue = ticket.price.replace(".", "").toLongOrNull() ?: 0L
            totalPriceSum += priceValue * qty
        }

        binding.tvTicketDetails.text = detailsBuilder.toString().trim()

        val formattedPrice = currencyFormatter.format(totalPriceSum)
        binding.tvTotalPrice.text = "Tổng: $formattedPrice đ"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}