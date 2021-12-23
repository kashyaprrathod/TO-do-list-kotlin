package com.kotlin.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.todolist.R
import com.kotlin.todolist.adapters.TaskListAdapter
import com.kotlin.todolist.adapters.TodoListAdapter
import com.kotlin.todolist.dbts.EntitiesDatabase
import com.kotlin.todolist.dbts.JobEntities
import com.kotlin.todolist.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListActivity : AppCompatActivity() {

    var listName: String = "";

    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
            getData()
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        listName = intent.getStringExtra(Utils.PATHINTENT).toString();

        tv_name.text = listName;

        getData();

        Listner();

    }

    private fun Listner() {
        iv_edit.setOnClickListener(View.OnClickListener {

            var i = Intent(this@TaskListActivity, CreateNewToDoActivity::class.java);
            i.putExtra(Utils.PATHINTENT, listName)
            launcher.launch(i)

        })
    }

    private fun getData() {

        CoroutineScope(Dispatchers.Main).launch {
            var data = EntitiesDatabase.getInstance(this@TaskListActivity)?.jobDao()
                ?.getDataByTitle(listName);

            Log.e("TAG", "getData: " + data?.size)
            setupRecyclerview(data);
        }
    }

    lateinit var adp_list: TaskListAdapter;

    private fun setupRecyclerview(data: List<JobEntities>?) {
        if (data.isNullOrEmpty()) {

        } else {
            if (!::adp_list.isInitialized) {
                adp_list = TaskListAdapter(data);
                rv_tasklist.adapter = adp_list;
                rv_tasklist.layoutManager = LinearLayoutManager(this)
                adp_list.setOnTaskClickListner(object : TaskListAdapter.onTaskClickListner {
                    override fun onClick(Job: JobEntities) {
                        EntitiesDatabase.getInstance(this@TaskListActivity)?.jobDao()
                            ?.updateData(Job);
                    }
                })
            }
        }
    }
}