package com.example.firebase_cloud_firestore_kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_cloud_firestore_kotlin.data.MessageItem
import de.hdodenhof.circleimageview.CircleImageView

class MessageAdapter(context: Context, myName: String, messageItems: ArrayList<MessageItem>) : RecyclerView.Adapter<MessageAdapter.VH>() {
    private var context: Context
    private var messageItems: ArrayList<MessageItem>
    private val TYPE_MY = 0
    private val TYPE_OTHER = 1
    private val MY_NAME = myName

    init {
        this.context = context
        this.messageItems = messageItems
    }

    //리사이클러뷰의 아이템뷰가 경우에 따라 다른 모양으로 보여야 할 때 사용하는 콜백 메소드가 있다 : getItemViewType
    //이 메소드에서 해당 position에 따른 식별값(ViewType 번호)를 정하여 리턴하면
    //그 값이 onCreateViewHolder() 메소드의 두번째 파라미터에 전달됨
    //onCreateViewHolder() 메소드 안에서 그 값에 따라 다른 xml 문서를 inflate 하면된다
    override fun getItemViewType(position: Int): Int {
        return if (messageItems[position].name.equals(MY_NAME)) {
            TYPE_MY //내가 쓴 글
        } else {
            TYPE_OTHER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var itemView = if (viewType == TYPE_MY) {
            LayoutInflater.from(context).inflate(R.layout.item_my_msg, parent, false)
        } else {
            LayoutInflater.from(context).inflate(R.layout.item_other_msg, parent, false)
        }

        //카톡 날짜 구분선도 이 타입으로 구분한것임
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item: MessageItem = messageItems[position]
        holder.tvName.text = item.name
        holder.tvMsg.text = item.message
        holder.tvTime.text = item.time
        //Glide.with(context).load(item.profileUrl).into(holder.civ)
    }

    override fun getItemCount(): Int {
        return messageItems.size
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //메세지 타입에 따라 뷰가 다름 바인딩 클래스를 고정 할 수 없다 (뷰가 두개라 누굴 써야할지 모르것다,,)
        //MyMessageboxBinding binding;
        //OtherMessageboxBinding binding2;
        //ViewHolder를 2개 만들어 사용하기도함 [MyVH, OtherVH]
        //홀더를 두개 만들면 onBinding할때도 분기 처리해야해서 이번에는 뷰 바인드 안쓰고 제작
        var civ: CircleImageView
        var tvName: TextView
        var tvMsg: TextView
        var tvTime: TextView

        init {
            //xml 의 id가 같아야 함
            civ = itemView.findViewById(R.id.civ)
            tvName = itemView.findViewById(R.id.tv_name)
            tvMsg = itemView.findViewById(R.id.tv_msg)
            tvTime = itemView.findViewById(R.id.tv_time)
        }
    }
}