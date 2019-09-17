package com.fofism.karmaunwaste

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.database.sqlite.SQLiteDatabase
import com.fofism.karmaunwaste.db.ProductItemContract
import android.content.ContentValues
import android.os.Build
import android.view.View
import android.widget.*
import com.fofism.karmaunwaste.db.ProductDbHelper
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDate.parse
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var mHelper: ProductDbHelper
    private lateinit var mTaskListView: ListView
    private var mAdapter: ArrayAdapter<String>? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTaskListView = findViewById(R.id.list_products)
        progressBar = findViewById(R.id.progressBar)

        mHelper = ProductDbHelper(this)
        updateUI()
    }

    fun deleteTask(view: View) {
        val parent = view.getParent() as View
        val taskTextView = parent.findViewById<TextView>(R.id.task_title)
        val task = taskTextView.text.toString()
        val db = mHelper.writableDatabase
        db.delete(ProductItemContract.ProductEntry.TABLE,
                ProductItemContract.ProductEntry.Food_item + " = ?",
                arrayOf(task))
        db.close()
        updateUI()
    }

    private fun updateUI() {

        val taskList = ArrayList<String>()
        val db = mHelper.readableDatabase
        val cursor = db.query(ProductItemContract.ProductEntry.TABLE,
                arrayOf(ProductItemContract.ProductEntry._ID, ProductItemContract.ProductEntry.Food_item), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val idx = cursor.getColumnIndex(ProductItemContract.ProductEntry.Food_item)

            if(progressBar != null) {
                var daysPassed = 1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val today = LocalDate.now()
                    val productBought =
                        parse(cursor.getString(cursor.getColumnIndex(ProductItemContract.ProductEntry.Purchase_Date)))
                     daysPassed = Duration.between(productBought, today).toDays().toInt()
                } else {
                    daysPassed = 1
                }
            val expirationStatus = daysPassed / 7 * 100
            progressBar!!.setProgress(expirationStatus)
            }
            taskList.add(cursor.getString(idx))
        }

        if (mAdapter == null) {
            mAdapter = ArrayAdapter(this,
                    R.layout.item_todo,
                    R.id.task_title,
                    taskList)
            mTaskListView.adapter = mAdapter
        } else {
            mAdapter?.clear()
            mAdapter?.addAll(taskList)
            mAdapter?.notifyDataSetChanged()
        }

        cursor.close()
        db.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_add_task -> {
                val taskEditText = EditText(this)
                val dialog = AlertDialog.Builder(this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
                            val task = taskEditText.text.toString()
                            val db = mHelper.getWritableDatabase()
                            val values = ContentValues()
                            values.put(ProductItemContract.ProductEntry.Food_item, task)
                            db.insertWithOnConflict(ProductItemContract.ProductEntry.TABLE,
                                    null,
                                    values,
                                    SQLiteDatabase.CONFLICT_REPLACE)
                            db.close()

                            Log.d(TAG, "Task to add: " + task)
                            updateUI()
                        })
                        .setNegativeButton("Cancel", null)
                        .create()
                dialog.show()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}
