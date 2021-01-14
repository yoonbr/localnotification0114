package com.yoond.localnotification0114

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_dialog.*
import kotlinx.android.synthetic.main.activity_main.*

class DialogActivity : AppCompatActivity() {
    // 배열의 항목 개수만큼 초기화
    var arSelect = booleanArrayOf(false, false, false, false, false, false, false, false, false, false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        // 목록 대화상자 출력
        btnListClick.setOnClickListener{
            AlertDialog.Builder(this@DialogActivity).setTitle("원하는 Frietag 선택")
                .setItems(R.array.language){
                    dialog, which ->
                    // 출력할 배열 가져오기
                    val languages =
                        resources.getStringArray(R.array.language)
                    // 선택한 언어 가져오기
                    val pl = languages[which]
                    // 토스트로 출력
                    Toast.makeText(this@DialogActivity, pl, Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("확인", null)
                .show()
        }

        // 하나만 선택할 수 있는 대화상자 출력
        btnSingleChoice.setOnClickListener{
            AlertDialog.Builder(this@DialogActivity).setTitle("하나만 고르세요")
                .setSingleChoiceItems(R.array.language, 0){
                    dialog, which ->
                    // 출력할 배열 가져오기
                    val languages =
                        resources.getStringArray(R.array.language)
                    // 선택한 언어 가져오기
                    val pl = languages[which]
                }
                .setNegativeButton("확인", null)
                .show()
        }

        // 다중 선택이 가능한 대화상자 출력
        btnMultihoice.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("여러 개 선택할 수 있음")
                .setMultiChoiceItems(R.array.language,arSelect) { dialog, which, isChecked ->
                    // 선택한 인덱스의 값을 isChecked로 수정
                    arSelect[which] = isChecked
                }
                        .setPositiveButton("확인"){
                            dialog, which ->
                            // resource에 정의한 배열 가져오기
                            val ar = resources.getStringArray(R.array.language)
                            // 메시지
                            var select = ""
                            // 배열을 순회할 때 사용할 인덱스
                            var idx = 0
                            // 선택 여부 배열을 순회하면서
                            for(i in arSelect) {
                                // 선택이 되었다면 그 데이터를 select에 추가
                                if(i==true){
                                    select = select + ar[idx] + "\t"
                                }
                                idx = idx + 1
                            }
                            Toast.makeText(this@DialogActivity, select, Toast.LENGTH_LONG).show()
                        }
                .setNegativeButton("취소", null)
                .show()
                }


        // 작업 상황을 알려주는 작업 진행 대화상자를 출력
        btnProgress.setOnClickListener{
            // 작업 진행 대화상자 생성
            // 현재는 대화상자보다 ProgressBar위젯을 이용하는 것을 권장
            // AlertDialog 계열은 UI가 좋지않다고함
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("진행 중")
            progressDialog.setMessage("다운로드를 받고 있습니다.")
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog.setIndeterminate(true)
            progressDialog.show()

        }

        // 날짜 선택 대화상자 출력
        btnDate.setOnClickListener{
            DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth ->
                    Toast.makeText(this@DialogActivity,
                        "${year}년 ${month+1}월 ${dayOfMonth}일", Toast.LENGTH_LONG).show()
                    // 월은 항상 -1
            }, 2021, 0, 14)
                .show()
        }

        // 시간 선택 대화상자
        btnTime.setOnClickListener{
            val cal = Calendar.getInstance()

            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{
                view, hourOfDay, minute ->
                Toast.makeText(this@DialogActivity, "${hourOfDay}:${minute} 분 선택",
                Toast.LENGTH_LONG).show()
            }, cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), false)
                .show()
        }

        btnCustom.setOnClickListener{
            // layout 파일에 정의 된 내용을 View로 만들기
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            // 전개한 뷰를 가지고 대화상자를 생성
            val view = inflater.inflate(R.layout.custom_layout,null)
            // 전개한 뷰를 가지고 대화상자를 생성
            AlertDialog.Builder(this)
                .setNegativeButton("취소", null)
                .setView(view)
                .create()
                .show()
        }
    }
}

