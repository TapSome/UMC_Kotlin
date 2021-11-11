package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter (private val albumList: ArrayList<Album>) : RecyclerView.Adapter<AlbumRVAdapter.ViewHoler>() {

    //클릭 인터페이스
    interface MyitemClickListener{
        fun onitemClick(album: Album)
    }

    //리스터 객체를 전달받는 함수량 리스터 객체를 저장할 변수
     private lateinit var mItemClickListener: MyitemClickListener

     fun setMyItemClickListener(itemClickListener: MyitemClickListener){
         mItemClickListener = itemClickListener
     }


    //뷰홀더를 생서해줘야 할때 호출 .. 아이템 뷰 객체를 만들어서 뷰홀더에 넣어줌
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHoler {
       val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup,false)

        return ViewHoler(binding)
    }

    //뷰홀더에 데이터 바인딩해줄때마다 호출
    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHoler, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onitemClick(albumList[position]) }
    }

    //데이터의 크기를 알려주는 함수 -> 꼭 해줘야됨
    override fun getItemCount(): Int  = albumList.size


    //뷰홀더
    inner class ViewHoler(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }

    }


}