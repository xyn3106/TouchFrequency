package dc.xyn.touchfrequency

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class MainActivity : AppCompatActivity() {

    private var moveCount = 0
    private var highest = 0
    private val timer = Timer()
    private val records = ConcurrentLinkedQueue<String>()
    private val task: TimerTask = object : TimerTask() {
        override fun run() {
            if (moveCount != 0) {
                if (highest < moveCount) highest = moveCount
                records.add(moveCount.toString())
                if (records.size > 10)
                    records.remove()
                val str = "当前${moveCount}Hz，最高${highest}Hz\n记录:$records"
                runOnUiThread { text.text = str }
                moveCount = 0
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = title.toString() + " V"+ BuildConfig.VERSION_NAME
        tips.text = "型号: ${Build.BRAND} ${Build.MODEL} ${Build.DEVICE}\n ${tips.text}"

        timer.schedule(task, 1000, 1000)

        bg.setOnTouchListener { _, event -> test(event) }
    }


    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    @SuppressLint("SetTextI18n")
    private fun test(event: MotionEvent): Boolean {
        if (event.pointerCount == 1) {
            if (event.action == MotionEvent.ACTION_MOVE)
                moveCount++
        } else Toast.makeText(this, "请使用一根手指进行测试", Toast.LENGTH_SHORT).show()
        return true
    }
}
