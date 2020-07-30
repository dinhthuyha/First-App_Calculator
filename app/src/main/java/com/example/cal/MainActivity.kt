package com.example.cal
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import nguyenvanquan7826.com.Balan

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //tao ra cac mang chua cac so 1..9, cac nut xu li
    private val idBtn = intArrayOf(
            R.id.btn_zezo, R.id.btn_one, R.id.btn_two, R.id.btn_three,
            R.id.btn_four, R.id.btn_five, R.id.btn_six,
            R.id.btn_seven, R.id.btn_eight, R.id.btn_nine, R.id.btn_cham
    )

    private val btn = intArrayOf(
            R.id.btn_cong, R.id.btn_tru, R.id.btn_nhan, R.id.btn_chia, R.id.btn_congtru, R.id.btn_bang
    )

    private val btnXuli = intArrayOf(
            R.id.btn_CE, R.id.btn_C, R.id.btn_BS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        val typeface = Typeface.createFromAsset(assets, "DS-DIGIT.TTF")
        tvMath.typeface = typeface
        tvResult.typeface = typeface
        tvMath.visibility = View.VISIBLE
    }

    private fun setupUI() {

        //xet su anh xa va su kien click voi tat ca cac nut

        for (i in idBtn) {
            findViewById<View>(i).setOnClickListener(this)
        }
        for (i in btn) {
            findViewById<View>(i).setOnClickListener(this)
        }
        for (k in btnXuli) {
            findViewById<View>(k).setOnClickListener(this)
        }
        tvMath.setText("|")
        tvResult.text = "0"
    }

    override fun onClick(v: View) {
        val id = v.id

        //chech xem btn co phai la cac so hay khong
        for (i in idBtn.indices) {
            if (id == idBtn[i]) {
                val text =
                        (findViewById<View>(id) as Button).text
                                .toString()


                //append cac so vao tvMath
                if (tvMath!!.text.toString().trim { it <= ' ' } == "|") {
                    tvMath!!.text = ""
                }
                tvMath!!.append(text)

                //khi nhap du cac phep toan ket qua se mo hien ra
                try {
                    val a = tvMath!!.text.toString().toInt()
                } catch (e: Exception) {
                    e.printStackTrace()
                    //goi ham tinh ket qua
                    cal()

                }
                // return;
            }
        }

        //chech xem btn co phai la cac dau cong, tru nhan chia hay khong
        for (i in 0 until btn.size - 2) {
            if (id == btn[i]) {
                val text =
                        (findViewById<View>(id) as Button).text
                                .toString()
                if (tvMath!!.text.toString().trim { it <= ' ' } == "|") {
                    tvMath!!.text = ""
                }
                tvMath!!.append(text)
                //  return;
            }
        }

        // su kien khi click vao nut C se xoa toan bo
        if (id == R.id.btn_C) {
            Toast.makeText(this, "xoa", Toast.LENGTH_SHORT).show()
            tvMath!!.text = "|"
            tvResult.text = "0"
            // return;
        }

        //khi click vao bang goi den ham tinh ket qua
        if (id == R.id.btn_bang) {
            cal()
            tvMath!!.text = tvResult.text
            tvResult.text = ""
        }

        //khi click vao nut CE
        if (id == R.id.btn_CE) {
            val text = tvMath!!.text.toString()
            var l = text.length - 1
            //xoa ki tu la so
            while (text[l] <= '9' && text[l] >= '0') {
                l--
                // neu ki tu la - va lien truoc no la cac dau cua phep toan thi l=l-1
                if (text[l] == '-' && (text[l - 1] == '*' || text[l - 1] == '/' || text[l - 1] == '+' || text[l - 1] == '-')
                ) {
                    l--
                }
            }
            tvMath!!.text = text.substring(0, l + 1)
        }

        //su kien click vao BS xoa hang don vi
        if (id == R.id.btn_BS) {
            val text = tvMath!!.text.toString()
            if (tvMath!!.text.length == 1) {
                tvMath!!.text = "|"
            } else {
                tvMath!!.text = text.substring(0, text.length - 1)
            }
        }

        // su kien click vao nut dao dau
        if (id == R.id.btn_congtru) {
            Toast.makeText(this, "hey" , Toast.LENGTH_SHORT).show()
            if (tvMath!!.text.toString() =="|") {
                tvMath!!.text = ""
            } else {

                if (tvResult.text.toString() == "") {
                    Toast.makeText(this, "" + tvMath.text.toString(), Toast.LENGTH_SHORT).show()
                    tvMath.text = "-" + tvMath.text.toString()
                } else {
                    tvMath.text= "-"+ tvMath.text
                }
            }
        }
    }

    // ham tinh ket qua
    private fun cal() {
        val math = tvMath!!.text.toString().trim { it <= ' ' }
        if (math.length > 0) {
            val balan = Balan()
            val result = balan.valueMath(math).toString() + ""
            val error = balan.error

            //check error
            if (error != null) {
                tvResult.text = error
            } else {
                tvResult.text = result
                tvResult.setTextColor(Color.GRAY)
            }
        }
    }
}