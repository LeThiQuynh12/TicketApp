package com.example.musicapp.presentation.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentPaymentBinding
import com.example.musicapp.presentation.model.PaymentMethod
import java.text.DecimalFormat

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private val args: PaymentFragmentArgs by navArgs()
    private val viewModel: PaymentViewModel by viewModels()
    private lateinit var methodAdapter: PaymentMethodAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPaymentBinding.bind(view)

        setupToolbar()
        setupPaymentMethods()
        displayOrderInfo()
        setupListeners()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun displayOrderInfo() {
        binding.tvUserEmail.text = "lequynh4431r@gmail.com"

        val formatter = DecimalFormat("#,###")
        binding.tvTotalBottom.text = "${formatter.format(args.totalAmount)} đ"
    }

    private fun setupPaymentMethods() {
        val methods = listOf(
            PaymentMethod("vnpay", "VNPAY/Ứng dụng ngân hàng", R.drawable.logo_vnpay),
            PaymentMethod("vietqr", "VietQR", R.drawable.logo_vietqr),
            PaymentMethod("shopeepay", "ShopeePay", R.drawable.logo_shopeepay),
            PaymentMethod("zalopay", "ZaloPay", R.drawable.logo_zalopay)
        )

        methodAdapter = PaymentMethodAdapter(methods) { method ->
            viewModel.selectMethod(method.id)
        }

        binding.rvPaymentMethods.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = methodAdapter
        }

        if (viewModel.selectedMethodId.value == null) {
            viewModel.selectMethod("vietqr")
        }

        viewModel.selectedMethodId.observe(viewLifecycleOwner) { selectedId ->
            methodAdapter.setSelectedMethod(selectedId)
        }
    }

    private fun setupListeners() {
        binding.btnPayment.setOnClickListener {
            when (viewModel.selectedMethodId.value) {
                "vnpay" -> {
                    findNavController().navigate(
                        R.id.action_paymentFragment_to_vietQrPaymentFragment
                    )
                }

                "vietqr" -> {
                    findNavController().navigate(
                        R.id.action_paymentFragment_to_vietQrPaymentFragment
                    )
                }

                "shopeepay" -> {
                    findNavController().navigate(
                        R.id.action_paymentFragment_to_vietQrPaymentFragment
                    )
                }

                "zalopay" -> {
                    findNavController().navigate(
                        R.id.action_paymentFragment_to_vietQrPaymentFragment
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
