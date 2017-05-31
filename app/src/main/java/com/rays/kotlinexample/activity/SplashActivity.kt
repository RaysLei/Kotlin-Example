package com.rays.kotlinexample.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by Rays on 2017/5/26.
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
        with(AnimatorSet()) {
            playSequentially(ObjectAnimator.ofFloat(ivLogo, "rotationY", 180f, 360f))
            duration = 2000
            start()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            })
        }
    }

}