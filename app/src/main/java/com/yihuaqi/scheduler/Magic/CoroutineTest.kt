package com.yihuaqi.scheduler.Magic

import android.util.Log
import kotlinx.coroutines.experimental.*

/**
 * Created by yihuaqi on 2/5/18.
 */
class CoroutineTest {
    companion object {
        val TAG = "CoroutineTest"
        fun test() = runBlocking<Unit> {
            val job = launch {
                try {
                    repeat(1000) {
                        Log.d(TAG, "repeat $it")
                        delay(500)
                    }
                } finally {
                    run(NonCancellable) {
                        Log.d(TAG, "finally withContext")
                        delay(1000)
                        Log.d(TAG, "delay 1000")
                    }
                }
            }
            delay(1000)
            Log.d(TAG, "Try to stop the world ${Thread.currentThread()}")
            job.cancelAndJoin()
            Log.d(TAG, "Finished ${Thread.currentThread()}")
        }
    }
}
