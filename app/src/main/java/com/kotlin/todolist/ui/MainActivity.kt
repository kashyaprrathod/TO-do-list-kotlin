package com.kotlin.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.todolist.R
import com.kotlin.todolist.adapters.TodoListAdapter
import com.kotlin.todolist.dbts.EntitiesDatabase
import com.kotlin.todolist.models.ToDoListModel
import com.kotlin.todolist.utils.SharedPrefHelpers
import com.kotlin.todolist.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    var createNewtask = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
//            if(it.resultCode == RESULT_OK){
            createToDoList()
//            }
        })


    var largeFont = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        largeFont = SharedPrefHelpers.getInstabce(MainActivity@this).getLargeFont()

        if(SharedPrefHelpers.getInstabce(MainActivity@this).getDarkMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        setUi();

        setContentView(R.layout.activity_main)

        Listners();

        createToDoList();

    }

    private fun setUi() {
        if(largeFont)
            setTheme(R.style.smallfont)
        else
            setTheme(R.style.largefont)
    }

    private fun Listners() {
        fab_addlist.setOnClickListener(View.OnClickListener {
            createNewtask.launch(Intent(this, CreateNewToDoActivity::class.java))
        })

        iv_settings.setOnClickListener(View.OnClickListener {

            startActivity(Intent(MainActivity@this,SettingsActivity::class.java))

        })
    }

    private fun createToDoList() {
        CoroutineScope(Dispatchers.Main).launch {

            var arl_tablename: List<String> =
                EntitiesDatabase.getInstance(this@MainActivity)?.jobDao()?.getTableName()!!;

//            Log.e("TAG", "createToDoList:lst size: "+arl_tablename.get(0))

            var arl_todolist: ArrayList<ToDoListModel> = ArrayList();

            for (name in arl_tablename) {
                val count =
                    EntitiesDatabase.getInstance(this@MainActivity)?.jobDao()
                        ?.getTableRemainingStatus(name);
                arl_todolist.add(ToDoListModel(name, count!!))
            }

            var total : Int = EntitiesDatabase.getInstance(this@MainActivity)?.jobDao()?.getTotolColumn()!!
            var done :Int =
                EntitiesDatabase.getInstance(this@MainActivity)?.jobDao()?.getRemainingTaskCount()!!

            setRecyclerView(arl_todolist, total, done);

        }
    }

    lateinit var adp_todolist: TodoListAdapter;

    private fun setRecyclerView(tableName: List<ToDoListModel>, total: Int, done: Int) {

        Log.e("TAG", "setRecyclerView: " + tableName.size)

        if (tableName.isEmpty()) {
            ll_data.visibility = GONE;
            ll_empty.visibility = VISIBLE
        } else {

            ll_data.visibility = VISIBLE;
            ll_empty.visibility = GONE

            tv_prg.setText("$done / $total")

            var pr : Int = (done * 100).toFloat().div(total).roundToInt();

            tv_prgperc.setText("("+pr+"%)")

            if (!::adp_todolist.isInitialized) {

                adp_todolist = TodoListAdapter(tableName)
                rv_list.adapter = adp_todolist
                rv_list.layoutManager = LinearLayoutManager(MainActivity@ this)
                adp_todolist.setonTableItemClickListner(object : TodoListAdapter.clickListner {
                    override fun click(string: String) {
                        var i = Intent(this@MainActivity, TaskListActivity::class.java);
                        i.putExtra(Utils.PATHINTENT, string)
                        createNewtask.launch(i);
                    }
                })
            }

            adp_todolist.setNewList(tableName);

        }
    }
}