package com.example.firebaserealtimedatabase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimedatabase.databinding.ItemRvBinding
import com.example.firebaserealtimedatabase.models.MyMessage

class RvAdapter(val list:ArrayList<MyMessage> = ArrayList()): RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(val itemRvBinding:ItemRvBinding): RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(myMessage: MyMessage,position: Int){
            itemRvBinding.tvId.text = myMessage.id
            itemRvBinding.tvText.text = myMessage.text
            itemRvBinding.tvDate.text = myMessage.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}