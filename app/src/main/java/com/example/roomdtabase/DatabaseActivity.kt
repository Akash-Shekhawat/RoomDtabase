package com.example.roomdtabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdtabase.roomdata.RoomDatabaseBuilder
import java.util.concurrent.Executors


class DatabaseActivity : AppCompatActivity() {


    lateinit var recyclerView: RecyclerView
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        val intent = intent
        BUTTON_CLICKED = intent.getStringExtra(MainActivity.BUTTON_CLICKED_KEY).toString()

        initialiseRecyclerView()

    }

    private fun initialiseRecyclerView() {
        recyclerView = findViewById(R.id.employeeDetailsRecyclerView)

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        setupListFromRoom()
    }



    private fun setupListFromRoom() {
        val roomDatabaseBuilder = RoomDatabaseBuilder.getInstance(this)
        var employeeList: List<EmployeeDataClass>

        Executors.newSingleThreadExecutor().execute {

            employeeList = roomDatabaseBuilder.employeeDao().getAllEmployeeDetails()

            recyclerView.apply {

                runOnUiThread {
                    customAdapter =
                        CustomAdapter(
                            this@DatabaseActivity,
                            employeeList as ArrayList<EmployeeDataClass>
                        )
                    recyclerView.adapter = customAdapter
                    customAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
        lateinit var BUTTON_CLICKED: String
    }

}