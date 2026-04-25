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
import com.example.musicapp.databinding.FragmentHomeBinding
import com.example.musicapp.presentation.home.banner.BannerAdapter
import com.example.musicapp.presentation.home.banner.EventSpeacialAdapter
import com.example.musicapp.presentation.home.city.CityAdapter
import com.example.musicapp.presentation.home.event.EventAdapter
import com.example.musicapp.presentation.model.HomeItem
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
            "https://ticketbox.vn/_next/image?url=https%3A%2F%2Fimages.tkbcdn.com%2F2%2F360%2F479%2Fts%2Fds%2F60%2F1c%2F1b%2F76f3f9991f16bf7f46a613fdce6bf568.jpeg&w=640&q=75"
        )

        binding.rvSpecialEvents.apply {
            adapter = EventSpeacialAdapter(sampleImages)
        }

        binding.rvTrendingEvents.apply {
            adapter = EventSpeacialAdapter(sampleImages, isTrending = true)
        }
    }

    private fun setupRecyclerViewEvent() {
        val sections = listOf(
            "Âm nhạc" to listOf(
                Event(
                    id = "1",
                    imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/04/ae/18/9ee650dff5237d8bcfdb2f92aa049e1c.jpg",
                    name = "Đêm nhạc Minh Tuyết - Hoàng Hải",
                    price = "Từ 500.000 đ",
                    date = "28 Tháng 04, 2026",
                    description = "Mô tả chi tiết sự kiện âm nhạc...",
                    location = "Hà Nội",
                    gallery = listOf("url1", "url2"),
                    seatMapUrl = "https://example.com/map1.jpg",
                    organizerName = "BTC Music",
                    contactPhone = "0987654321"
                ),
                Event(
                    id = "2",
                    imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/80/14/b7/0d2ddc410e4735ae4ea11f488af05655.jpeg",
                    name = "EXO PLANET #6",
                    price = "Từ 3.000.000 đ",
                    date = "25 Tháng 04, 2026",
                    description = "Mô tả EXO...",
                    location = "Tp. Hồ Chí Minh",
                    gallery = listOf("url1", "url2"),
                    seatMapUrl = "https://example.com/map2.jpg",
                    organizerName = "BTC K-Pop",
                    contactPhone = "0987654321"
                )
            ),
            "Văn hóa" to listOf(
                Event(
                    id = "3",
                    imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/c1/81/68/096127bb1d5e99a116c1936613533f1d.jpg",
                    name = "Concert Về Về Thanh Xuân",
                    price = "Từ 1.000.000 đ",
                    date = "25 Tháng 04, 2026",
                    description = "Mô tả văn hóa...",
                    location = "Đà Lạt",
                    gallery = listOf("url1"),
                    seatMapUrl = "https://example.com/map3.jpg",
                    organizerName = "BTC Văn Hóa",
                    contactPhone = "0987654321"
                )
            )
        )

        // 2. Chuyển đổi sang flatList để đổ vào RecyclerView
        val flatList = mutableListOf<HomeItem>()

        sections.forEach { (categoryName, events) ->
            // Thêm Header
            flatList.add(HomeItem.Header(id = categoryName, title = categoryName))

            // Thêm các Event
            events.forEach { event ->
                flatList.add(
                    HomeItem.Event(
                        id = event.id,
                        imageUrl = event.imageUrl,
                        category = categoryName,
                        name = event.name,
                        price = event.price,
                        date = event.date,
                        description = event.description,
                        location = event.location,
                        gallery = event.gallery,
                        seatMapUrl = event.seatMapUrl,
                        organizerName = event.organizerName,
                        contactPhone = event.contactPhone
                    )
                )
            }
        }

        // 3. Setup Adapter và RecyclerView
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