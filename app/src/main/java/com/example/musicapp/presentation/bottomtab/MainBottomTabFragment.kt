package com.example.musicapp.presentation.bottomtab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musicapp.databinding.FragmentMainBottomTabBinding

class MainBottomTabFragment : Fragment() {

    private var _binding: FragmentMainBottomTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBottomTabBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager
            .findFragmentById(binding.bottomNavHostFragment.id)
            ?.findNavController()
            ?.let { navController ->
                binding.bottomNav.setupWithNavController(navController)
            }

        adjustBottomNavSpacing()
    }

    private fun adjustBottomNavSpacing() {
        binding.bottomNav.post {
            val menuView = binding.bottomNav.getChildAt(0) as? ViewGroup
                ?: return@post

            val offset = -4f * resources.displayMetrics.density

            for (i in 0 until menuView.childCount) {
                val itemView = menuView.getChildAt(i)
                adjustLabelPosition(itemView, offset)
            }
        }
    }

    private fun adjustLabelPosition(view: View, offset: Float) {
        if (view is TextView) {
            view.translationY = offset
            return
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                adjustLabelPosition(view.getChildAt(i), offset)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
