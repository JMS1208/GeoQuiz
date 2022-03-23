package com.jms.a20220324_chapter1.Model

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean) {

}