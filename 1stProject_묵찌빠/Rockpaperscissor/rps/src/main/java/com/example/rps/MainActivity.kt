package com.example.rps

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // 알림창 사용하기 위한 이벤트 핸들러
    val eventHandler = object : DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, p: Int) {
            if (p == DialogInterface.BUTTON_POSITIVE) {
                finish()
            } else if (p == DialogInterface.BUTTON_NEGATIVE) {
                Log.d("ko", "clicked negative")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // howto 버튼 클릭 시, 설명 뷰로 이동
        button_howto.setOnClickListener {
            val intent:Intent = Intent(this, Howto::class.java)
            startActivity(intent)
        }
        // exit 버튼 클릭 시, 종료할 건지 재확인 알림창
        button_exit.setOnClickListener {
            AlertDialog.Builder(this).run{
                setTitle("WARNING DIALOG")
                setMessage("Are you sure you want to exit?")
                setPositiveButton("Yes", eventHandler)
                setNegativeButton("No", eventHandler)
                show()
            }
        }
        // game start 버튼 클릭 시, 인게임 화면으로 전환
        button_gamestart.setOnClickListener {
            val intent:Intent = Intent(this, Ingame::class.java)
            startActivity(intent)
        }
    }

}