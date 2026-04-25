package com.example.musicapp.presentation.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.musicapp.databinding.FragmentEventDetailBinding


class EventDetailFragment : Fragment() {
    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!

    private val args: EventDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val event = args.eventData

        binding.tvNameEvent.text = event.name
        binding.tvDescription.text = event.description
        binding.tvPrice.text = "Từ ${event.price}"
        binding.tvOrganizerName.text = "Đơn vị tổ chức: ${event.organizerName}"
        Glide.with(this).load(event.imageUrl).into(binding.imgCover)
        Glide.with(this).load(event.seatMapUrl).into(binding.imgSeatMap)
        if (event.gallery.isNotEmpty()) {
            Glide.with(this).load(event.gallery[0]).into(binding.imgGallery1)
            if (event.gallery.size > 1) {
                Glide.with(this).load(event.gallery[1]).into(binding.imgGallery2)
            }
        }
    }
}