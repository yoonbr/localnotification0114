package com.yoond.localnotification0114

import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnVib.setOnClickListener{
            // 진동 객체 가져오기
            val vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            // 운영체제 버전 별 수행되는 코드 작성
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1){
                vib.vibrate(VibrationEffect.createOneShot(3000,VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vib.vibrate(3000)
            }
        }
        btnSystemSound.setOnClickListener{
            // 효과음의 URI 가져오기
            val notificationUri = RingtoneManager.getDefaultUri(
                RingtoneManager.TYPE_NOTIFICATION)
            // 효과음 가져오기
            var ringtone = RingtoneManager.getRingtone(applicationContext, notificationUri)
            // 효과음 재생
            ringtone.play()


        }
        btnCustomSound.setOnClickListener{
            // raw 디렉토리에 저장한 효과음 재생
            val player = MediaPlayer.create(this, R.raw.buttoneffect)
            player.start()
        }

        btnAlertDialog.setOnClickListener({
            AlertDialog.Builder(this@MainActivity)
                .setTitle("기본 대화상자")
                .setMessage("대화상자 만들기 연습 ")
                .setIcon(android.R.drawable.ic_menu_mylocation)
                // 대화상자가 닫히지 않게 설정
                .setCancelable(false)
                .setNegativeButton("닫기", null)
                .setPositiveButton("CHECK"){
                    dialog, which ->
                        Toast.makeText(this@MainActivity, "Check", Toast.LENGTH_LONG).show()
                    Log.e("Message", "확인버튼 누름")
                }
                .show()
        })
    }
}