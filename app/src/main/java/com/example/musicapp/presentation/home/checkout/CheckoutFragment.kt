package com.example.musicapp.presentation.home.checkout

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentCheckoutBinding
import com.example.musicapp.presentation.model.Ticket
import java.text.DecimalFormat

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    private val args: CheckoutFragmentArgs by navArgs()

    private val currencyFormatter = DecimalFormat("#,###")

    private var countDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupData()
        startTimer(15 * 60 * 1000L)

        binding.btnContinue.setOnClickListener {
            navigateToPayment()
        }
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

    // Hiển thị thông tin vé
    private fun setupData() {
        val selectedTickets = args.selectedTickets.toList()

        if (selectedTickets.isEmpty()) {
            binding.btnContinue.isEnabled = false
            binding.btnContinue.alpha = 0.5f
            return
        }

        var totalPrice = 0L

        selectedTickets.forEach { ticket ->
            val priceValue = ticket.price
                .filter { it.isDigit() }
                .toLongOrNull() ?: 0L

            val subtotal = priceValue * ticket.quantity
            totalPrice += subtotal
        }

        // Hiển thị danh sách vé
        binding.ticketName.text = buildString {
            selectedTickets.forEachIndexed { index, ticket ->
                append(ticket.name)
                if (index < selectedTickets.lastIndex) {
                    append("\n")
                }
            }
        }

        binding.qty.text = buildString {
            selectedTickets.forEachIndexed { index, ticket ->
                append(String.format("%02d", ticket.quantity))
                if (index < selectedTickets.lastIndex) {
                    append("\n")
                }
            }
        }

        val formattedPrice = formatCurrency(totalPrice)

        // Cập nhật tổng tiền dưới màn hình
        binding.tvTotalBottom.text = formattedPrice

        // Nếu XML có TextView hiển thị tạm tính thì cập nhật tại đây
        // binding.tvSubtotal.text = formattedPrice
    }

    private fun formatCurrency(amount: Long): String {
        return "${currencyFormatter.format(amount)} đ"
    }

    // Chuyển sang màn hình thanh toán
    private fun navigateToPayment() {
        val selectedTickets = args.selectedTickets

        if (selectedTickets.isEmpty()) {
            return
        }

        findNavController().navigate(
            R.id.action_checkoutFragment_to_paymentFragment
        )
    }

    // Countdown 15 phút
    private fun startTimer(millisInFuture: Long) {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(millisInFuture, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60

                binding.tvTimer.text = String.format(
                    "%02d : %02d",
                    minutes,
                    seconds
                )
            }

            override fun onFinish() {
                binding.tvTimer.text = "00 : 00"
                binding.btnContinue.isEnabled = false
                binding.btnContinue.alpha = 0.5f
            }

        }.start()
    }

    override fun onDestroyView() {
        countDownTimer?.cancel()
        countDownTimer = null

        _binding = null
        super.onDestroyView()
    }
}
