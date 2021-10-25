package com.example.roomdtabase

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdtabase.roomdata.AppRoomDatabase
import com.example.roomdtabase.roomdata.RoomDatabaseBuilder
import java.util.concurrent.Executors

class CustomAdapter(private val context: Context,
                    private val employeeData: ArrayList<EmployeeDataClass>
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val databaseRoom: AppRoomDatabase = RoomDatabaseBuilder.getInstance(context)


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val empDetailsConstraintLayout: ConstraintLayout =
            view.findViewById(R.id.empDetailsContraintLayout)
        val empDetailsEditConstraintLayout: ConstraintLayout =
            view.findViewById(R.id.editEmpDetailsConstraintLayout)


        val nameTextView: TextView = view.findViewById(R.id.empNameTextView)
        val contactTextView: TextView = view.findViewById(R.id.empContactTextView)
        val addressTextView: TextView = view.findViewById(R.id.empAddressTextView)
        val editDataImgBtn: ImageButton = view.findViewById(R.id.editEmpDetailsBtn)
        val deleteDataImgBtn: ImageButton = view.findViewById(R.id.deleteEmpDetailsBtn)


        val nameEditTextView: EditText = view.findViewById(R.id.empNameEditTextView)
        val contactEditTextView: EditText = view.findViewById(R.id.empContactEditTextView)
        val addressEditTextView: TextView = view.findViewById(R.id.empAddressEditTextView)

        val updateDetailsBtn: Button = view.findViewById(R.id.updateDetailsBtn)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_employee_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = employeeData.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val empId = employeeData[position].id
        val empName = employeeData[position].name!!
        val empContact = employeeData[position].contact!!
        val empAddress = employeeData[position].address!!

        holder.nameTextView.text = empName
        holder.contactTextView.text = empContact
        holder.addressTextView.text = empAddress

        holder.deleteDataImgBtn.setOnClickListener {
            deleteDataFromDBUsingRoom(empId, position)
        }

        holder.editDataImgBtn.setOnClickListener {
            updateUI(holder, empName, empContact, empAddress)

            holder.updateDetailsBtn.setOnClickListener {
                val updatedName = holder.nameEditTextView.text.toString()
                val updatedContact = holder.contactEditTextView.text.toString()
                val updatedAddress = holder.addressEditTextView.text.toString()

                Log.i("Data", "=== Name: $updatedName, Contact: $updatedContact, Address: $updatedAddress ===")

                updateDetailsInDB(
                    empId,
                    updatedName,
                    updatedContact,
                    updatedAddress,
                    position,
                    holder
                )
            }
        }
    }


    private fun deleteDataFromDBUsingRoom(empId: Int, position: Int) {
        Executors.newSingleThreadExecutor().execute {
            databaseRoom.employeeDao().deleteEmployeeDetails(EmployeeDataClass(id = empId))
        }
        Toast.makeText(
            context,
            "Employee with Id: $empId is deleted",
            Toast.LENGTH_SHORT
        ).show()

        deleteItem(position)
    }

    private fun updateUI(
        holder: ViewHolder,
        empName: String,
        empContact: String,
        empAddress: String
    ) {

        holder.nameEditTextView.setText(empName)
        holder.contactEditTextView.setText(empContact)
        holder.addressEditTextView.setText(empAddress)


        holder.empDetailsConstraintLayout.visibility = View.INVISIBLE


        holder.empDetailsEditConstraintLayout.visibility = View.VISIBLE
    }

    private fun updateDetailsInDB(
        empId: Int,
        updatedName: String,
        updatedContact: String,
        updatedAddress: String,
        position: Int,
        holder: ViewHolder
    ) {

        Executors.newSingleThreadExecutor().execute {
            databaseRoom.employeeDao().updateEmployeeDetails(
                EmployeeDataClass(
                    id = empId,
                    name = updatedName,
                    contact = updatedContact,
                    address = updatedAddress
                )
            )
        }


        holder.empDetailsEditConstraintLayout.visibility = View.GONE


        holder.empDetailsConstraintLayout.visibility = View.VISIBLE

        Toast.makeText(
            context,
            "Employee with Id: $empId is updated",
            Toast.LENGTH_SHORT
        ).show()

        setItem(position, empId, updatedName, updatedContact, updatedAddress)
    }


    private fun setItem(
        position: Int,
        empID: Int,
        empName: String,
        empContact: String,
        empAddress: String
    ) {
        employeeData[position] = EmployeeDataClass(empID, empName, empContact, empAddress)
        notifyDataSetChanged()
    }


    private fun deleteItem(position: Int) {
        employeeData.removeAt(position)
        notifyDataSetChanged()
    }
}