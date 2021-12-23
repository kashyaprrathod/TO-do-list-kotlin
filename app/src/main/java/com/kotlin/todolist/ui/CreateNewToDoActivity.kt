package com.kotlin.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kotlin.todolist.R
import com.kotlin.todolist.dbts.EntitiesDatabase
import com.kotlin.todolist.dbts.JobEntities
import com.kotlin.todolist.utils.Utils
import kotlinx.android.synthetic.main.activity_create_new_to_do.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class CreateNewToDoActivity : AppCompatActivity() {

    var TAG: String = CreateNewToDoActivity::class.java.simpleName

    var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_to_do)

        name = intent.getStringExtra(Utils.PATHINTENT);

        name?.let { fetchData(it) }

        Listners();

    }

    private fun fetchData(name: String) {
        CoroutineScope(Dispatchers.Main).launch {
            var data: List<JobEntities> =
                EntitiesDatabase.getInstance(this@CreateNewToDoActivity)?.jobDao()
                    ?.getDataByTitle(name)!!;

            et_title.setText(name);

            var txt = ""

            for (ent: JobEntities in data) {
                txt = txt + ent.job;
                txt = txt + "\n";
            }

            et_task.setText(txt)
        }

    }

    private fun Listners() {

        iv_done.setOnClickListener(View.OnClickListener {

            if (isValid()) {
                createTask();
            }
        })

    }

    private fun isValid(): Boolean {
        if (et_title.text.toString().isEmpty()) {
            return false;
        } else if (et_task.text.toString().isEmpty()) {
            return false;
        }
        return true
    }

    private fun createTask() {
        var title = et_title.text.toString();
        var task: List<String> = et_task.text.toString().split("\n")

        task = removeDuplicates(task as ArrayList<String>)

        var dao = EntitiesDatabase.getInstance(CreateNewToDoActivity@ this)?.jobDao();

        var finalList: ArrayList<JobEntities> = ArrayList();

        if (name != null) {
            for (t in task) {
                if (dao?.ifRecordAvailable(name!!, t)!!) {
                    finalList.add(dao.getDataByTitleAndTask(name!!, t))
                } else {
                    finalList.add(JobEntities(name!!, t, false))
                }
            }

            dao?.deleteDataBYTitle(name!!)

            for (ent: JobEntities in finalList) {
                ent.title = title;
                if(!ent.job.isEmpty())
                    dao?.insertData(ent)
            }
        } else {
            for (stask in task) {
                if(!stask.isEmpty())
                    dao?.insertData(JobEntities(title, stask, false))
            }

        }

        setResult(RESULT_OK)
        finish()
    }

    fun removeDuplicates(list: ArrayList<String>): List<String> {
        val set: MutableSet<String> = TreeSet(Comparator<String> { o1, o2 ->
            if (o1.equals(o2)) {
                0
            } else 1
        })
        set.addAll(list!!)
        return ArrayList(set)
    }



}