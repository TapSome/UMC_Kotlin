package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    //Gson
    private var gson: Gson = Gson()
    //song
    private var song: Song = Song()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainPlayerLayout.setOnClickListener {
            startActivity(Intent(this,SongActivity::class.java))
        }

        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(), 0, 215, false, "music_lilac")



        binding.mainPlayerLayout.setOnClickListener {

            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
            startActivity(intent)
        }

        initNavigation()

        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false


        }
    }

    private fun setMiniPlayer(song : Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainProgressSb.progress = (song.second*1000/song.playTime)

        if(song.isPlaying){
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
        }else{
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
        }
    }


    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

    }

    override fun onStart(){
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)

        val jsonSong = sharedPreferences.getString("song", null)
        song = if(jsonSong == null) {
            Song(
                binding.mainMiniplayerTitleTv.text.toString(),
                binding.mainMiniplayerSingerTv.text.toString(),
                0,
                215,
                false,
                "music_lilac"
            )
        }else {
            gson.fromJson(jsonSong, Song::class.java)
        }
    }

}

