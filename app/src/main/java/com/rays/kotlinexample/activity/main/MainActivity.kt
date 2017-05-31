package com.rays.kotlinexample.activity.main

import android.os.Bundle
import android.support.v4.app.Fragment
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_appbar.*
import org.jetbrains.anko.toast

/**
 * Created by Rays on 2017/5/26.
 */
class MainActivity : BaseActivity() {
    var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        tvSearch.setOnClickListener { toast("${tvSearch.text}") }
        bottomNavigation.setOnNavigationItemSelectedListener {
            println("itemId: ${it.itemId} selectedItemId: ${bottomNavigation.selectedItemId}")
            when (it.itemId) {
                R.id.item_reading -> switchFragment(R.id.item_reading)
                R.id.item_my -> switchFragment(R.id.item_my)
                else -> switchFragment(R.id.item_home)
            }
            true
        }
        bottomNavigation.setOnNavigationItemReselectedListener {
            println("itemId: ${it.itemId}")
        }

        switchFragment(R.id.item_home)
    }

    private fun switchFragment(id: Int) {
        val fragment: Fragment = supportFragmentManager.findFragmentByTag(id.toString()) ?: when (id) {
            R.id.item_reading -> ReadingFragment.newInstance()
            R.id.item_my -> MyFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
        if (currentFragment === fragment) {

        } else if (fragment.isAdded) {
            supportFragmentManager.beginTransaction().hide(currentFragment).show(fragment).commit()
        } else {
            fragment.userVisibleHint = true
            if (currentFragment == null) {
                supportFragmentManager.beginTransaction().add(R.id.frameContent, fragment, id.toString()).commit()
            } else {
                supportFragmentManager.beginTransaction().hide(currentFragment).add(R.id.frameContent, fragment, id.toString()).commit()
            }
        }
        currentFragment = fragment
    }
}
