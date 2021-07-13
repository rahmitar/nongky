package com.noobee.nongky.ui.list

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.noobee.nongky.R
import com.noobee.nongky.databinding.ActivityListKategoriBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListKategoriActivity : AppCompatActivity() {

    private lateinit var listKategoriBinding: ActivityListKategoriBinding

    private val listKategoriViewModel: ListKategoriViewModel by viewModels()

    private lateinit var listKategoriAdapter: RecylerViewListKategoriAdapter


    companion object{
        const val EXTRA_KATEGORI = "extra_kategori"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listKategoriBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_kategori)
        listKategoriAdapter = RecylerViewListKategoriAdapter(listKategoriViewModel)

        setupRecyclerView()
        listKategoriBinding.apply {
            lifecycleOwner = this@ListKategoriActivity
        }

        listKategoriViewModel.action.observe(this, Observer { action ->
            when(action){
                ListKategoriViewModel.ACTION_LIST_ITEMUPDATE -> listItemUpdate()
                ListKategoriViewModel.ACTION_LIST_TIMEOUT -> connectionTimeout()
            }
        })

        val tag = intent.getStringExtra(EXTRA_KATEGORI)
        listKategoriViewModel.setList(tag)
        Toast.makeText(this,"Activity berhasil",Toast.LENGTH_SHORT).show()
    }

    private fun connectionTimeout() {
        Toast.makeText(this, "Koneksi Gagal", Toast.LENGTH_SHORT).show()
    }

    private fun listItemUpdate() {
        listKategoriAdapter.listItem = listKategoriViewModel.listKategoriCafe
        listKategoriAdapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView(){
        listKategoriBinding.recyclerViewListKategori.apply {
            layoutManager = LinearLayoutManager(this@ListKategoriActivity, LinearLayoutManager.VERTICAL, false)
            adapter = listKategoriAdapter

        }
    }

}