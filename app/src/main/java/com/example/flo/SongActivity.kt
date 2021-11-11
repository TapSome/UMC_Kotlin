package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding

    private val song: Song = Song()
    private lateinit var player:Player
    private val handler = Handler(Looper.getMainLooper())

    private var mediaPlayer: MediaPlayer? = null

    private var gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()

        player = Player(song.playTime,song.isPlaying)
        player.start()
        player.interrupt()

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songPlayerPlayIb.setOnClickListener {
            player.isPlaying=true
            setPlayerStatus(true)
            song.isPlaying = true
            mediaPlayer?.start()
        }

        binding.songPlayerPauseIb.setOnClickListener {
             player.isPlaying=false
            setPlayerStatus(false)
            song.isPlaying = false
            mediaPlayer?.pause()
        }

    }

    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("second") && intent.hasExtra("playTime") && intent.hasExtra("isPlaying") && intent.hasExtra("music")){
          song.title = intent.getStringExtra("title")!!
          song.singer = intent.getStringExtra("singer")!!
            song.second = intent.getIntExtra("second", 0)
          song.playTime = intent.getIntExtra("playTime", 0)
          song.isPlaying = intent.getBooleanExtra("isPlaying", false)
          song.music = intent.getStringExtra("music")!!
           val music = resources.getIdentifier(song.music, "raw", this.packageName)

          binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime/60, song.playTime % 60)
          binding.songTitleTv.text = song.title
          binding.songSingerTv.text = song.singer
          setPlayerStatus(song.isPlaying)
            mediaPlayer = MediaPlayer.create(this, music)
        }

    }


    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songPlayerPauseIb.visibility = View.VISIBLE
            binding.songPlayerPlayIb.visibility = View.GONE
        }
        else{
            binding.songPlayerPlayIb.visibility = View.VISIBLE
            binding.songPlayerPauseIb.visibility = View.GONE
        }

    }
    inner class Player(private val playTime:Int, var isPlaying: Boolean):Thread(){
        private var second = 0
        override fun run(){
            while(true){
                try{
                    if(second >= playTime)
                        break

                    if(isPlaying){
                        sleep(1000)
                        second++
                        handler.post{
                            binding.songPlayerSb.progress = second*1000/playTime
                            binding.songStartTimeTv.text = String.format("%02d:%02d", second/60, second% 60)
                        }

                    }
                }
                catch(e : InterruptedException){
                    Log.d("interrupt", "쓰레드가 종료되었습니다.")
                }

            }

        }
    }

    override fun onPause(){
        super.onPause()
        mediaPlayer?.pause() //미디어 플레이어 중지
        player.isPlaying = false //스레드 중지
        song.isPlaying = false
        song.second = (binding.songPlayerSb.progress*song.playTime)/1000
        setPlayerStatus(false) //정지상태일 때의 이미지로 전환

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //Gson
        val json = gson.toJson(song)
        editor.putString("song", json)

        editor.apply()

    }


    override fun onDestroy() {
        player.interrupt()
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

}