package com.rays.kotlinexample.entity

/**
 * Created by Rays on 2017/5/27.
 */
data class HttpResult<T>(var error: Boolean? = null, var results: T? = null)