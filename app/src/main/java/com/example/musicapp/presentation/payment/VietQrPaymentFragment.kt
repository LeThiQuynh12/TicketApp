package com.example.musicapp.presentation.home.payment

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
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentVietQrPaymentBinding
import java.text.DecimalFormat
import android.os.Handler
import android.os.Looper

class VietQrPaymentFragment : Fragment() {

    private var _binding: FragmentVietQrPaymentBinding? = null
    private val binding get() = _binding!!

    private val args: VietQrPaymentFragmentArgs by navArgs()
    private var countDownTimer: CountDownTimer? = null
    private val currencyFormatter = DecimalFormat("#,###")

    private val handler = Handler(Looper.getMainLooper())
    private var paymentRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVietQrPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTransferInfo()
        startTimer(14 * 60 + 26)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDownloadQr.setOnClickListener {
            Toast.makeText(requireContext(), "Đang tải mã QR...", Toast.LENGTH_SHORT).show()
        }
        fakePaymentSuccess()
    }

    private fun setupTransferInfo() {
        val totalAmount = args.totalAmount
        val formattedAmount = currencyFormatter.format(totalAmount)

//        binding.tvTotalPrice.text = "$formattedAmount đ"

        binding.layoutBank.tvLabel.text = "Tên ngân hàng"
        binding.layoutBank.tvValue.text = "BIDV"
        binding.layoutBank.btnCopy.setOnClickListener {
            copyToClipboard("Ngân hàng", "VPB")
        }

        binding.layoutAccountName.tvLabel.text = "Tên tài khoản"
        binding.layoutAccountName.tvValue.text = "LE THI QUYNH"
        binding.layoutAccountName.btnCopy.setOnClickListener {
            copyToClipboard("Tên tài khoản", "ZALOPAY TICKETBOX")
        }

        binding.layoutAccountNumber.tvLabel.text = "Số tài khoản"
        binding.layoutAccountNumber.tvValue.text = "2153 5661 62"
        binding.layoutAccountNumber.btnCopy.setOnClickListener {
            copyToClipboard("Số tài khoản", "ZLP26100141977944")
        }

        binding.layoutAmount.tvLabel.text = "Số tiền"
        binding.layoutAmount.tvValue.text =
            formattedAmount.replace(",", "")
        binding.layoutAmount.btnCopy.setOnClickListener {
            copyToClipboard("Số tiền", totalAmount.toString())
        }
    }

    private fun copyToClipboard(label: String, text: String) {
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "Đã sao chép $label", Toast.LENGTH_SHORT).show()
    }

    private fun startTimer(seconds: Int) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer((seconds * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val min = (millisUntilFinished / 1000) / 60
                val sec = (millisUntilFinished / 1000) % 60
                binding.tvTimer.text = String.format("%02d : %02d", min, sec)
            }

            override fun onFinish() {
                binding.tvTimer.text = "00 : 00"
                Toast.makeText(requireContext(), "Hết thời gian thanh toán!", Toast.LENGTH_LONG)
                    .show()
            }
        }.start()
    }

    private fun fakePaymentSuccess() {
        paymentRunnable = Runnable {
            if (isAdded) {
                showSuccessDialog()
            }
        }
        handler.postDelayed(paymentRunnable!!, 20000)
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Thông báo")
        builder.setMessage("Thanh toán thành công! Bạn có thể kiểm tra vé trong mục 'Vé của tôi'.")
        builder.setCancelable(false)
        builder.setPositiveButton("Xác nhận") { dialog, _ ->
            dialog.dismiss()
            findNavController().popBackStack(R.id.nav_home, false)
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        _binding = null
    }
}