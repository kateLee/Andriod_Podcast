package com.katelee.podcast

/**
 * Created by Kate on 2020-04-18.
 */

object MyStringUtils {
    @JvmStatic
    fun formatTime(mills: Int) = "%02d:%02d".format(mills / 1000 / 60, mills / 1000 % 60)
}