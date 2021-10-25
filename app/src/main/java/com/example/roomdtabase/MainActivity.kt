package com.example.roomdtabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.roomdtabase.roomdata.RoomDatabaseBuilder
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addDemoBtn: Button = findViewById(R.id.btn1)
        val demoBtn:Button = findViewById(R.id.btn2)
        demoBtn.setOnClickListener {
            startActivity(
                Intent(this, DatabaseActivity::class.java).putExtra(
                    MainActivity.BUTTON_CLICKED_KEY,
                    MainActivity.ROOM_DEMO_BTN
                )
            )
        }

        addDemoBtn.setOnClickListener {
            insertDataInDBUsingRoom()
            Toast.makeText(this, "Data added successfully", Toast.LENGTH_LONG).show()
        }
    }

    private fun insertDataInDBUsingRoom() {
        val database = RoomDatabaseBuilder.getInstance(this)

        Executors.newSingleThreadExecutor().execute {
            database.employeeDao().insertEmployeeDetails(
                EmployeeDataClass(
                    name = "arjuman",
                    contact = "435404424",
                    address = "New Jersy"
                )
            )
            database.employeeDao().insertEmployeeDetails(
                EmployeeDataClass(
                    name = "Tarun",
                    contact = "684357618",
                    address = "United States"
                )
            )
            database.employeeDao().insertEmployeeDetails(
                EmployeeDataClass(
                    name = "Ansh",
                    contact = "5458715405",
                    address = "Noida"
                )
            )

        }
    }
    companion object {
        const val BUTTON_CLICKED_KEY = "BUTTON_CLICKED"
        const val ROOM_DEMO_BTN = "ROOM_DEMO_BTN"
    }
}