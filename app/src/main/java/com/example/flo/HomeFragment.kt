package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

    /*    binding.homeTodayMusic1Iv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment())
                .commitAllowingStateLoss()
        }*/

        //데이터  리스트 생성
        albumDatas.apply{
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_butter))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_nextlevel))
            add(Album("boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_boywithluv))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_weekend))
        }


        //더미데이터랑 adapter 연결
        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        //리사이클러뷰에 어댑터 연결
        binding.homeTodayMusicAlbumRecyclerView.adapter = albumRVAdapter

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyitemClickListener{

            override fun onitemClick(album: Album) {
                changeAlbumFragment(album)
            }
        })

        //레이아웃 매니저 설정
        binding.homeTodayMusicAlbumRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val bannerAdapter = BannerViewPagerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homeAdsVp.adapter = bannerAdapter
        binding.homeAdsVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }


}