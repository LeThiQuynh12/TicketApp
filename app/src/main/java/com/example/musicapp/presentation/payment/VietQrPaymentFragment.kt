package com.example.musicapp.presentation.payment

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.musicapp.databinding.FragmentVietQrPaymentBinding
import java.text.DecimalFormat

class VietQrPaymentFragment : Fragment() {

    private var _binding: FragmentVietQrPaymentBinding? = null
    private val binding get() = _binding!!

    private val args: VietQrPaymentFragmentArgs by navArgs()

    private var countDownTimer: CountDownTimer? = null

    private val currencyFormatter = DecimalFormat("#,###")

    private var remainingTimeMillis = INITIAL_TIME_MILLIS

    companion object {
        private const val INITIAL_TIME_MILLIS = 14 * 60 * 1000L + 26 * 1000L
        private const val KEY_REMAINING_TIME = "remaining_time"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        remainingTimeMillis = savedInstanceState?.getLong(KEY_REMAINING_TIME)
            ?: INITIAL_TIME_MILLIS
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVietQrPaymentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupTransferInfo()
        setupListeners()

        if (remainingTimeMillis > 0L) {
            startTimer(remainingTimeMillis)
        } else {
            onPaymentExpired()
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupTransferInfo() {
        val totalAmount = args.totalAmount
        val formattedAmount = currencyFormatter.format(totalAmount)

        // Hiển thị tổng tiền
        binding.tvTotalPrice.text = "$formattedAmount đ"

        // Thông tin ngân hàng
        val bankName = "BIDV"

        binding.layoutBank.apply {
            tvLabel.text = "Ngân hàng"
            tvValue.text = bankName

            btnCopy.setOnClickListener {
                copyToClipboard("Ngân hàng", bankName)
            }
        }

        // Tên chủ tài khoản
        val accountName = "LE THI QUYNH"

        binding.layoutAccountName.apply {
            tvLabel.text = "Tên tài khoản"
            tvValue.text = accountName

            btnCopy.setOnClickListener {
                copyToClipboard("Tên tài khoản", accountName)
            }
        }

        // Số tài khoản
        val accountNumber = "2153566162"

        binding.layoutAccountNumber.apply {
            tvLabel.text = "Số tài khoản"
            tvValue.text = accountNumber

            btnCopy.setOnClickListener {
                copyToClipboard("Số tài khoản", accountNumber)
            }
        }

        // Số tiền chuyển khoản
        val transferAmount = totalAmount.toString()

        binding.layoutAmount.apply {
            tvLabel.text = "Số tiền"

            tvValue.text = formattedAmount

            btnCopy.setOnClickListener {
                copyToClipboard("Số tiền", transferAmount)
            }
        }
    }

    private fun setupListeners() {
        binding.btnDownloadQr.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Chức năng tải mã QR đang được phát triển",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun copyToClipboard(label: String, text: String) {
        val clipboard = requireContext().getSystemService(
            Context.CLIPBOARD_SERVICE
        ) as ClipboardManager

        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(
            requireContext(),
            "Đã sao chép $label",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun startTimer(duration: Long) {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(duration, 1000L) {

            override fun onTick(millisUntilFinished: Long) {
                remainingTimeMillis = millisUntilFinished

                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60

                binding.tvTimer.text = String.format(
                    "%02d : %02d",
                    minutes,
                    seconds
                )
            }

            override fun onFinish() {
                remainingTimeMillis = 0L
                binding.tvTimer.text = "00 : 00"

                onPaymentExpired()
            }
        }.start()
    }

    private fun onPaymentExpired() {
        if (!isAdded || _binding == null) return

        binding.btnDownloadQr.isEnabled = false

        AlertDialog.Builder(requireContext())
            .setTitle("Hết thời gian thanh toán")
            .setMessage(
                "Đã hết thời gian giữ vé. Vui lòng quay lại và thực hiện đặt vé mới."
            )
            .setCancelable(false)
            .setPositiveButton("Đồng ý") { dialog, _ ->
                dialog.dismiss()
                findNavController().popBackStack()
            }
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(KEY_REMAINING_TIME, remainingTimeMillis)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        countDownTimer?.cancel()
        countDownTimer = null

        _binding = null

        super.onDestroyView()
    }
}
