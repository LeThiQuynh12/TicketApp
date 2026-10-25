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

        setupData()
        startTimer(15 * 60 * 1000)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnContinue.setOnClickListener { findNavController().navigate(R.id.action_checkoutFragment_to_paymentFragment) }
    }

    private fun setupData() {
        val selectedTickets = args.selectedTickets.toList()
        var totalPrice = 0L
        val summaryBuilder = StringBuilder()

        selectedTickets.forEach { ticket ->
            val priceValue = ticket.price.replace(".", "").toLongOrNull() ?: 0L
            totalPrice += priceValue * ticket.quantity
            binding.ticketName.text = ticket.name
            binding.qty.text = String.format("%02d", ticket.quantity)
        }

        val formattedPrice = currencyFormatter.format(totalPrice) + " đ"
        binding.tvTotalBottom.text = formattedPrice
    }

    private fun startTimer(millisInFuture: Long) {
        countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.tvTimer.text = String.format("%02d : %02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.tvTimer.text = "00 : 00"
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        _binding = null
    }
}