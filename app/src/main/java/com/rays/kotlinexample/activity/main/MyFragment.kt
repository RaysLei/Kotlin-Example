package com.rays.kotlinexample.activity.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rays.kotlinexample.fragment.BaseFragment
import com.rays.kotlinexample.R
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * Created by Rays on 2017/5/26.
 */
class MyFragment : BaseFragment() {

    companion object {
        val TEXT = "text"
        fun newInstance(): Fragment {
            val fragment = MyFragment()
            val bundle = Bundle()
            bundle.putString(TEXT, fragment.javaClass.simpleName)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text.text = arguments.getString(TEXT)
    }
}