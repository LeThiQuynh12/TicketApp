package com.example.musicapp.presentation.home.ticket

import android.content.res.ColorStateList
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
import java.text.NumberFormat
import java.util.Locale

class TicketSelectionFragment : Fragment() {

    private var _binding: FragmentTicketSelectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var ticketAdapter: TicketAdapter

    // Lưu vé đã chọn theo ID, tránh trùng vé
    private val selectedTicketsMap =
        linkedMapOf<String, Pair<Ticket, Int>>()

    private val currencyFormatter: NumberFormat =
        NumberFormat.getNumberInstance(Locale("vi", "VN")).apply {
            maximumFractionDigits = 0
            minimumFractionDigits = 0
            isGroupingUsed = true
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketSelectionBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        updateUIState()
    }

    // Toolbar
    private fun setupToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationContentDescription("Quay lại")

            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    // RecyclerView
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
            setHasFixedSize(true)
        }
    }

    // Xử lý sự kiện
    private fun setupClickListeners() {

        binding.btnSelectTicket.setOnClickListener {

            // Kiểm tra lại trước khi chuyển màn hình
            if (selectedTicketsMap.isEmpty()) {
                updateUIState()
                return@setOnClickListener
            }

            val ticketsToSend = selectedTicketsMap.values.map { (ticket, quantity) ->
                ticket.apply {
                    this.quantity = quantity
                }
            }.toTypedArray()

            val action = TicketSelectionFragmentDirections
                .actionTicketSelectionFragmentToCheckoutFragment(ticketsToSend)

            findNavController().navigate(action)
        }
    }

    // Cập nhật giao diện
    private fun updateUIState() {

        val totalQuantity = selectedTicketsMap.values.sumOf {
            it.second
        }

        val hasSelectedTickets = totalQuantity > 0

        binding.cvSelectedTickets.visibility =
            if (hasSelectedTickets) View.VISIBLE else View.GONE

        binding.btnSelectTicket.apply {

            isEnabled = hasSelectedTickets

            text = if (hasSelectedTickets) {
                "Tiếp tục ($totalQuantity vé)"
            } else {
                "Vui lòng chọn vé"
            }

            backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    if (hasSelectedTickets) R.color.green
                    else R.color.gray
                )
            )

            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (hasSelectedTickets) R.color.white
                    else R.color.gray
                )
            )

            alpha = if (hasSelectedTickets) 1f else 0.7f
        }

        if (hasSelectedTickets) {
            updateTicketSummaryDetails()
        }
    }

    // Tính tổng tiền và hiển thị chi tiết
    private fun updateTicketSummaryDetails() {

        var totalPriceSum = 0L

        val details = selectedTicketsMap.values.map { (ticket, quantity) ->

            val unitPrice = parsePrice(ticket.price)
            val subtotal = unitPrice * quantity

            totalPriceSum += subtotal

            "${quantity} x ${ticket.name}: ${formatPrice(subtotal)} đ"
        }

        binding.tvTicketDetails.text = details.joinToString("\n")

        binding.tvTotalPrice.text =
            "Tổng: ${formatPrice(totalPriceSum)} đ"
    }

    // Chuyển chuỗi giá thành số nguyên VND
    private fun parsePrice(price: String): Long {
        return price.filter { it.isDigit() }.toLongOrNull() ?: 0L
    }

    // Định dạng tiền tệ
    private fun formatPrice(price: Long): String {
        return currencyFormatter.format(price)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
