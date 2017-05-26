package com.rays.kotlinexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text.setText(R.string.hello_kotlin)
        text.setOnClickListener {
            toast("Hello Kotlin")
        }

        val a = 1.toLong() + 1
        when (a) {
            is Long -> println("is Long")
            else -> println("no")
        }

        val str = """
            |Tell me and I forget.
            |Teach me and I remember.
            |Involve me and I learn.
            """.trimMargin()
        println(str)

        val t = Test()
        println("name: ${t.name}, age: ${t.age}")

        t.name = "test"

        val result = t.let { it.name + it.age }[1]
        println("result: $result")

        val ss = "str"
        val isTrue = ss == "sss"

        val t2 = t.copy(age = 24)
        println("name: ${t2.name}, age: ${t2.age}")
    }
}
