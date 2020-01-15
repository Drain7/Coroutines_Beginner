package phone.com.coroutine_beginner_example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "Result 1"
    private val RESULT_2 = "Result 2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_click.setOnClickListener {
            setNewText("Button Clicked!")

            CoroutineScope(IO).launch {
                fakeAPIRequest()
            }
        }
    }

    private suspend fun fakeAPIRequest() {
        logThread("FakeAPIRequest started!")

        val result1 = getResult1FromFakeApi()
        setTextOnMainThread("fetched : $result1")


        val result2 = getResult2FromFakeApi()
        setTextOnMainThread("fetched : $result2")


    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main) {
            setNewText(input)
        }
    }

    private fun setNewText(newText: String) {
        val newText = textView.text.toString() + "\n$newText"
        textView.text = newText
    }


    private suspend fun getResult1FromFakeApi(): String {
        logThread("Result1")
        delay(1000)
        return RESULT_1
    }

    private suspend fun getResult2FromFakeApi(): String {
        logThread("Result2")
        delay(1000)
        return RESULT_2
    }

    private fun logThread(method: String) {
        println("debug + $method : ${Thread.currentThread().name}")
    }


}
