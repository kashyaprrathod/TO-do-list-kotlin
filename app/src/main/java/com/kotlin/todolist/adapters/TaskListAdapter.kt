package com.kotlin.todolist.adapters

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.todolist.databinding.AdpTasklistBinding
import com.kotlin.todolist.dbts.JobEntities

class TaskListAdapter(var arl_data : List<JobEntities>,var isFadding:Boolean) : RecyclerView.Adapter<TaskListAdapter.Vholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vholder {
        return Vholder(AdpTasklistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vholder, position: Int) {
        holder.setData(position);
    }

    override fun getItemCount(): Int {
        return arl_data.size
    }

    inner class Vholder(var binding : AdpTasklistBinding) : RecyclerView.ViewHolder(binding.root){
        fun setData(position: Int) {
            binding.tv.text = "\u2022  "+arl_data.get(position).job;

            if(arl_data.get(position).status){
                binding.tv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG;
                if(isFadding){
                    binding.tv.setTextColor(Color.LTGRAY)
                }else{
                    binding.tv.setTextColor(Color.BLACK)
                }
            }else{
                binding.tv.paintFlags = Paint.ANTI_ALIAS_FLAG;
            }

            itemView.setOnClickListener {
                if(::lstnr.isInitialized){
                    arl_data.get(position).status = !arl_data.get(position).status
                    lstnr.onClick(arl_data.get(position))
                    notifyDataSetChanged()
                }
            }
        }
    }

    public interface onTaskClickListner{
        fun onClick(Job : JobEntities);
    }
    lateinit var lstnr : onTaskClickListner;
    public fun setOnTaskClickListner(lstnr : onTaskClickListner){
        this@TaskListAdapter.lstnr = lstnr;
    }

}
