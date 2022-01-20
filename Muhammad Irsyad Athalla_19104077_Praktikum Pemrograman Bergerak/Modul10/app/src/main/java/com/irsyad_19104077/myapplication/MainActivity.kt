package com.irsyad_19104077.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.irsyad_19104077.myapplication.Data.Quote
import com.irsyad_19104077.myapplication.db.QuoteHelper
import com.irsyad_19104077.myapplication.helper.EXTRA_POSITION
import com.irsyad_19104077.myapplication.helper.EXTRA_QUOTE
import com.irsyad_19104077.myapplication.helper.REQUEST_ADD
import com.irsyad_19104077.myapplication.helper.REQUEST_UPDATE
import com.irsyad_19104077.myapplication.helper.RESULT_ADD
import com.irsyad_19104077.myapplication.helper.RESULT_DELETE
import com.irsyad_19104077.myapplication.helper.RESULT_UPDATE
import com.irsyad_19104077.myapplication.helper.mapCursorToArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var quoteHelper: QuoteHelper
    private lateinit var adapter: QuoteAdapter

    class QuoteAdapter {

    }

    private val EXTRA_STATE = "EXTRA_STATE"
    private lateinit var binding: ActivityMainBinding

    class ActivityMainBinding {

        val rvQuotes: Any
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Quotes"
        binding.rvQuotes.layoutManager = LinearLayoutManager(this)
        binding.rvQuotes.setHasFixedSize(true)
        adapter = QuoteAdapter(this)
        binding.rvQuotes.adapter = adapter
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, QuoteAddUpdateActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
        }
        quoteHelper = QuoteHelper.getInstance(applicationContext)
        quoteHelper.open()
        if (savedInstanceState == null) {
            loadQuotes()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Quote>(EXTRA_STATE)
            if (list != null) {
                adapter.listQuotes = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listQuotes)
    }

    private fun loadQuotes() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val cursor = quoteHelper.queryAll()
            var quotes = mapCursorToArrayList(cursor)
            progressbar.visibility = View.INVISIBLE
            if (quotes.size > 0) {
                adapter.listQuotes = quotes
            } else {
                adapter.listQuotes = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvQuotes, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                REQUEST_ADD -> if (resultCode == RESULT_ADD) {
                    val quote = data.getParcelableExtra<Quote>(EXTRA_QUOTE) as Quote
                    adapter.addItem(quote)
                    binding.rvQuotes.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                REQUEST_UPDATE ->
                    when (resultCode) {
                        RESULT_UPDATE -> {
                            val quote = data.getParcelableExtra<Quote>(EXTRA_QUOTE) as Quote
                            val position = data.getIntExtra(EXTRA_POSITION, 0)
                            adapter.updateItem(position, quote)
                            binding.rvQuotes.smoothScrollToPosition(position)
                            showSnackbarMessage("Satu item berhasil diubah")
                        }
                        RESULT_DELETE -> {
                            val position = data.getIntExtra(EXTRA_POSITION, 0)
                            adapter.removeItem(position)
                            showSnackbarMessage("Satu item berhasil dihapus")
                        }
                    }
            }
        }
    }
}