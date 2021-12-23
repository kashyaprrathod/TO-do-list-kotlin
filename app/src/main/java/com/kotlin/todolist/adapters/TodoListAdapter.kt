package com.kotlin.todolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.todolist.databinding.AdpTablenmaesBinding
import com.kotlin.todolist.models.ToDoListModel

class TodoListAdapter(var arl_tablename : List<ToDoListModel>) : RecyclerView.Adapter<TodoListAdapter.Vholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vholder {
        return Vholder(AdpTablenmaesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vholder, position: Int) {
        holder.setData(position);
    }

    override fun getItemCount(): Int {
        return arl_tablename.size;
    }

    inner class Vholder(val binding : AdpTablenmaesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(position: Int) {
            binding.tvTabelname.text = arl_tablename.get(position).name
            binding.tvRemainingcount.text = ""+arl_tablename.get(position).remaining

            itemView.setOnClickListener(View.OnClickListener {
                if(::lstn.isInitialized)
                    lstn?.click(arl_tablename.get(position).name);
            })
        }
    }

    lateinit var lstn : clickListner;

    public interface clickListner{
        fun click(string: String)
    }


    fun setonTableItemClickListner(lstn : clickListner){
        this@TodoListAdapter.lstn = lstn
    }

    fun setNewList(tableName: List<ToDoListModel>) {
        this@TodoListAdapter.arl_tablename = tableName
        notifyDataSetChanged()
    }


}