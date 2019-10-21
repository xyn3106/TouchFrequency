# TouchFrequency


## 设置时间间隔1秒

timer.schedule(task, 1000, 1000)


## 计算单指触摸次数

private fun test(event: MotionEvent): Boolean {
        if (event.pointerCount == 1) {
            if (event.action == MotionEvent.ACTION_MOVE)
                moveCount++
        } else Toast.makeText(this, "请使用一根手指进行测试", Toast.LENGTH_SHORT).show()
        return true
    }

    
## 读取每秒内的触摸次数

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
