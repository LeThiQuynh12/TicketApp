package com.example.musicapp.presentation.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.musicapp.R
import com.example.musicapp.data.model.City
import com.example.musicapp.data.model.Event
import com.example.musicapp.data.model.Location
import com.example.musicapp.databinding.FragmentHomeBinding
import com.example.musicapp.presentation.home.banner.BannerAdapter
import com.example.musicapp.presentation.home.banner.EventSpeacialAdapter
import com.example.musicapp.presentation.home.city.CityAdapter
import com.example.musicapp.presentation.home.event.EventAdapter
import com.example.musicapp.presentation.model.HomeItem
import com.example.musicapp.presentation.model.toFormattedString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val videoList = listOf("10wXlU23cfk", "PaMk-Ahcwuk", "9avQo3IiiDA")
    private var currentPage = 0

    private val handler = Handler(Looper.getMainLooper())

    private val scrollRunnable = object : Runnable {
        override fun run() {
            if (videoList.isEmpty()) return

            if (currentPage >= videoList.size) {
                currentPage = 0
            }

            binding.viewPagerBanner.setCurrentItem(currentPage++, true)
            handler.postDelayed(this, 4000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBanner()
        setupSections()
        setupRecyclerViewEvent()
        setupRecyclerViewCity()
    }

    private fun setupBanner() {
        val adapter = BannerAdapter(videoList)
        binding.viewPagerBanner.adapter = adapter

        binding.viewPagerBanner.offscreenPageLimit = 1
        setupDots(videoList.size)

        binding.viewPagerBanner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position)
                currentPage = position
            }
        })

        handler.postDelayed(scrollRunnable, 5000)
    }

    private fun setupDots(count: Int) {
        binding.layoutDots.removeAllViews()
        val density = resources.displayMetrics.density
        val size = (8 * density).toInt()
        val margin = (4 * density).toInt()

        for (i in 0 until count) {
            val dot = View(requireContext())
            val params = LinearLayout.LayoutParams(size, size)
            params.setMargins(margin, 0, margin, 0)
            dot.layoutParams = params
            dot.setBackgroundResource(R.drawable.dot_unselected)
            binding.layoutDots.addView(dot)
        }
        updateDots(0)
    }

    private fun updateDots(position: Int) {
        for (i in 0 until binding.layoutDots.childCount) {
            val dot = binding.layoutDots.getChildAt(i)
            dot.setBackgroundResource(
                if (i == position) R.drawable.dot_selected
                else R.drawable.dot_unselected
            )
        }
    }

    private fun setupSections() {
        val sampleImages = listOf(
            "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fimages.tkbcdn.com%2F2%2F360%2F479%2Fts%2Fds%2F11%2Fd6%2Fe8%2Ff6893bb9663c34bf576326976023a43d.png&w=640&q=75",
            "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fimages.tkbcdn.com%2F2%2F360%2F479%2Fts%2Fds%2F18%2F88%2F18%2Fe332c989034090fa1c08b6f7d77aca85.jpg&w=640&q=75",
            "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fimages.tkbcdn.com%2F2%2F360%2F479%2Fts%2Fds%2F60%2F1c%2F1b%2F76f3f9991f16bf7f46a613fdce6bf568.jpeg&w=640&q=75",
            "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fimages.tkbcdn.com%2F2%2F360%2F479%2Fts%2Fds%2F8c%2F41%2Ff0%2F8b5bb5b96ff2c969c5b0b454d1304473.jpg&w=640&q=75",
            "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fimages.tkbcdn.com%2F2%2F360%2F479%2Fts%2Fds%2F60%2Fa3%2F34%2F3700a1d51db32056f57ef089fe8751c4.jpg&w=640&q=75"

        )

        val sampleTrending = listOf(
            "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fimages.tkbcdn.com%2F2%2F608%2F332%2Fts%2Fds%2F2d%2F4a%2Fae%2F3f932822f53decae05767457e02c7bf5.jpeg&w=1920&q=75",
            "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fimages.tkbcdn.com%2F2%2F608%2F332%2Fts%2Fds%2F71%2F54%2F2d%2F8e539b88433cc6be0c072f53b94d84e0.png&w=1920&q=75",
            "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fimages.tkbcdn.com%2F2%2F608%2F332%2Fts%2Fds%2Fd8%2F08%2F79%2F489f5f3a0f9109b574742e75e2753040.jpeg&w=1920&q=75",
            "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fimages.tkbcdn.com%2F2%2F608%2F332%2Fts%2Fds%2Fdf%2F73%2F58%2F48093f2ebde108ffb8ae51fe702b1fcb.jpg&w=1920&q=75",
        )

        binding.rvSpecialEvents.apply {
            adapter = EventSpeacialAdapter(sampleImages)
        }

        binding.rvTrendingEvents.apply {
            adapter = EventSpeacialAdapter(sampleTrending, isTrending = true)
        }
    }

    private fun setupRecyclerViewEvent() {
        val sections = EventMockData.getMockSections()

        val flatList = mutableListOf<HomeItem>()

        sections.forEach { (categoryName, events) ->
            flatList.add(HomeItem.Header(id = categoryName, title = categoryName))

            events.forEach { event ->
                val presentationLocation = com.example.musicapp.presentation.model.Location(
                    name = event.location.name,
                    province = event.location.province,
                    district = event.location.district,
                    ward = event.location.ward,
                    addressDetail = event.location.addressDetail
                )

                flatList.add(
                    HomeItem.Event(
                        id = event.id,
                        imageUrl = event.imageUrl,
                        category = categoryName,
                        name = event.name,
                        price = event.price,
                        date = event.date,
                        description = event.description,
                        location = presentationLocation,
                        gallery = event.gallery,
                        seatMapUrl = event.seatMapUrl,
                        organizerName = event.organizerName,
                        contactPhone = event.contactPhone
                    )
                )
            }
        }

        val adapter = EventAdapter { eventItem ->
            val action = HomeFragmentDirections.actionNavHomeToEventDetailFragment(eventItem)
            findNavController().navigate(action)
        }

        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItemViewType(position) == 0) 2 else 1
            }
        }

        binding.rvNormalEvents.layoutManager = layoutManager
        binding.rvNormalEvents.adapter = adapter
        adapter.submitList(flatList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(scrollRunnable)
        _binding = null
    }

    private fun setupRecyclerViewCity() {
        val cityList = listOf(
            City(
                "Tp. Hồ Chí Minh",
                "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fsalt.tkbcdn.com%2Fts%2Fds%2Ff9%2F65%2F0a%2F820ff0dd8178de627b64574b8a475fee.png&w=1920&q=75"
            ),
            City(
                "Hà Nội",
                "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fsalt.tkbcdn.com%2Fts%2Fds%2F8c%2F60%2F7e%2F9cfb9b5c032a63acd87599a23871b2c7.png&w=1920&q=75"
            ),
            City(
                "Đà Lạt",
                "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fsalt.tkbcdn.com%2Fts%2Fds%2F87%2Fa5%2Fca%2Fc85260e784845d634bb8a4a8070238ab.png&w=1920&q=75"
            ),
            City(
                "Vị trị khác",
                "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fsalt.tkbcdn.com%2Fts%2Fds%2F24%2F27%2F08%2F683a9d51b6011b6081815cf2409918f1.png&w=1920&q=75"
            )
        )
        val adapter = CityAdapter()
        binding.rvCity.adapter = adapter
        adapter.submitList(cityList)
    }
}