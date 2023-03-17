package com.example.rps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.rps.databinding.ActivityHowtoBinding
import kotlinx.android.synthetic.main.activity_main.*


class Howto : AppCompatActivity() {
    lateinit var binding: ActivityHowtoBinding
    // HOWTO screen 화면 출력 코드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHowtoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_howto)
    }

    // HOWTO 화면의 메뉴 나오는 부분 코드
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_mainscreen, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // gobacktomain 메뉴버튼으로 메인 화면으로 복귀하기
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.gobacktomain -> {
            finish()
            true
        }
        else -> true
    }
}