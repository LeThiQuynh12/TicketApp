package com.example.musicapp.presentation.home

import com.example.musicapp.data.model.Event
import com.example.musicapp.data.model.Location

object EventMockData {
    fun getMockSections(): List<Pair<String, List<Event>>> {
        return listOf(
            "Âm nhạc" to listOf(
                Event(
                    id = "1",
                    imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/04/ae/18/9ee650dff5237d8bcfdb2f92aa049e1c.jpg",
                    name = "Đêm nhạc Minh Tuyết - Hoàng Hải",
                    price = "Từ 500.000 đ",
                    date = "28 Tháng 04, 2026",
                    description = "Mô tả chi tiết sự kiện âm nhạc...",
                    location = Location(
                        "Nhà Hát Ca Múa Nhạc Thăng Long",
                        "Hà Nội",
                        "Bắc Từ Liêm",
                        "Cổ Nhuế",
                        "16"
                    ),
                    gallery = listOf("url1", "url2"),
                    seatMapUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/04/ae/18/9ee650dff5237d8bcfdb2f92aa049e1c.jpg",
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
                    location = Location("Sân vận động Mỹ Đình", "Hà Nội", "Bắc Từ Liêm", "Cổ Nhuế", "16"),
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
                    location = Location("Sân vận động Mỹ Đình", "Hà Nội", "Bắc Từ Liêm", "Cổ Nhuế", "16"),
                    gallery = listOf("url1"),
                    seatMapUrl = "https://example.com/map3.jpg",
                    organizerName = "BTC Văn Hóa",
                    contactPhone = "0987654321"
                ),

                Event(
                    id = "4",
                    imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/d3/4d/db/4ccab5246ec8f9fa1647955c1dbd5c9c.jpg",
                    name = "Chào Show",
                    price = "Từ 1.000.000 đ",
                    date = "25 Tháng 04, 2026",
                    description = "Mô tả văn hóa...",
                    location = Location("Sân vận động Mỹ Đình", "Hà Nội", "Bắc Từ Liêm", "Cổ Nhuế", "16"),
                    gallery = listOf("url1"),
                    seatMapUrl = "https://example.com/map3.jpg",
                    organizerName = "BTC Văn Hóa",
                    contactPhone = "0987654321"
                )
            ),
            "Tham quan và trải nghiệm" to listOf(
                Event(
                    id = "5",
                    imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/a6/0a/4a/60e9e35f58d00a4df2f987fe5f02803c.jpg",
                    name = "Địa đảo Củ Chi",
                    price = "Từ 3.000.000 đ",
                    date = "25 Tháng 04, 2026",
                    description = "Mô tả...",
                    location = Location("Sân vận động Mỹ Đình", "Hà Nội", "Bắc Từ Liêm", "Cổ Nhuế", "16"),
                    gallery = listOf("url1", "url2"),
                    seatMapUrl = "https://example.com/map2.jpg",
                    organizerName = "BTC K-Pop",
                    contactPhone = "0987654321"
                ),
                Event(
                    id = "6",
                    imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/90/5b/9c/9fa0af20c655a892db19176c1c9ef7ba.jpg",
                    name = "VinWonders Wave Park & Water Park",
                    price = "Từ 3.000.000 đ",
                    date = "25 Tháng 04, 2026",
                    description = "Mô tả...",
                    location = Location("Sân vận động Mỹ Đình", "Hà Nội", "Bắc Từ Liêm", "Cổ Nhuế", "16"),
                    gallery = listOf("url1", "url2"),
                    seatMapUrl = "https://example.com/map2.jpg",
                    organizerName = "BTC K-Pop",
                    contactPhone = "0987654321"
                )
            ),
            "Hội thảo và workshop" to listOf(
                Event(
                    id = "8",
                    imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/9e/b9/cd/e37d8440a8ba59649e3cee440dc4b75d.png",
                    name = "Làm nến thơm",
                    price = "Từ 3.000.000 đ",
                    date = "25 Tháng 04, 2026",
                    description = "Mô tả...",
                    location = Location("Sân vận động Mỹ Đình", "Hà Nội", "Bắc Từ Liêm", "Cổ Nhuế", "16"),
                    gallery = listOf("url1", "url2"),
                    seatMapUrl = "https://example.com/map2.jpg",
                    organizerName = "BTC K-Pop",
                    contactPhone = "0987654321"
                ),

                Event(
                    id = "9",
                    imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/60/2e/8f/7745b34257e6a25406e016f98d3a9df9.png",
                    name = "[FLOWER 1969’s] WORKSHOP SOLID PERFUME - NƯỚC HOA KHÔ",
                    price = "Từ 3.000.000 đ",
                    date = "25 Tháng 04, 2026",
                    description = "Mô tả...",
                    location = Location("Sân vận động Mỹ Đình", "Hà Nội", "Bắc Từ Liêm", "Cổ Nhuế", "16"),
                    gallery = listOf("url1", "url2"),
                    seatMapUrl = "https://example.com/map2.jpg",
                    organizerName = "BTC K-Pop",
                    contactPhone = "0987654321"
                )
            ),
            "Thể thao" to listOf(
                Event(
                    id = "10",
                    imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/c8/14/ee/c7309f2991beab5310911603aca35c4c.jpg",
                    name = "SUBICHA MERCHANDISE",
                    price = "Từ 3.000.000 đ",
                    date = "25 Tháng 04, 2026",
                    description = "Mô tả...",
                    location = Location("Sân vận động Mỹ Đình", "Hà Nội", "Bắc Từ Liêm", "Cổ Nhuế", "16"),
                    gallery = listOf("url1", "url2"),
                    seatMapUrl = "https://example.com/map2.jpg",
                    organizerName = "BTC K-Pop",
                    contactPhone = "0987654321"
                ),
                Event(
                    id = "11",
                    imageUrl = "https://images.tkbcdn.com/2/608/332/ts/ds/92/13/7b/5401cb0326ac911fa08c1a509d3cde3b.png",
                    name = "VBA 2026 - Saigon Heat - Ha Noi Buffaloes 21.06",
                    price = "Từ 3.000.000 đ",
                    date = "25 Tháng 04, 2026",
                    description = "Mô tả...",
                    location = Location("Sân vận động Mỹ Đình", "Hà Nội", "Bắc Từ Liêm", "Cổ Nhuế", "16"),
                    gallery = listOf("url1", "url2"),
                    seatMapUrl = "https://example.com/map2.jpg",
                    organizerName = "BTC K-Pop",
                    contactPhone = "0987654321"
                )
            )
        )
    }
}