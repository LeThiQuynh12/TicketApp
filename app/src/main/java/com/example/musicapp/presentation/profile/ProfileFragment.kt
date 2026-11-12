package com.example.musicapp.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.musicapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProfileInfo()
        setupSettingItems()
        setupLogout()
    }

    private fun setupProfileInfo() {
        binding.tvProfileName.text = "Quỳnh Lê Thị"
        binding.tvProfileEmail.text = "quynh@example.com"
    }

    private fun setupSettingItems() {
        setupItem(
            binding.itemEditProfile,
            "👤",
            "Thông tin cá nhân",
            "Cập nhật tên, email và số điện thoại"
        )

        setupItem(
            binding.itemChangePassword,
            "🔒",
            "Đổi mật khẩu",
            "Thay đổi mật khẩu tài khoản"
        )

        setupItem(
            binding.itemPayment,
            "💳",
            "Phương thức thanh toán",
            "Quản lý phương thức thanh toán"
        )

        setupItem(
            binding.itemNotification,
            "🔔",
            "Thông báo",
            "Tùy chỉnh thông báo sự kiện"
        )

        setupItem(
            binding.itemLanguage,
            "🌐",
            "Ngôn ngữ",
            "Tiếng Việt"
        )

        setupItem(
            binding.itemPrivacy,
            "🛡",
            "Quyền riêng tư",
            "Quản lý dữ liệu và bảo mật"
        )

        setupItem(
            binding.itemHelp,
            "❓",
            "Trung tâm trợ giúp",
            "Câu hỏi thường gặp và hỗ trợ"
        )

        setupItem(
            binding.itemAbout,
            "ℹ",
            "Về TicketApp",
            "Thông tin ứng dụng và phiên bản"
        )
    }

    private fun setupItem(
        item: com.example.musicapp.databinding.ItemProfileSettingBinding,
        icon: String,
        title: String,
        subtitle: String
    ) {
        item.tvItemIcon.text = icon
        item.tvItemTitle.text = title
        item.tvItemSubtitle.text = subtitle
        item.root.setOnClickListener {
            Toast.makeText(
                requireContext(),
                title,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupLogout() {
        binding.btnLogout.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Bạn đã chọn đăng xuất",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}