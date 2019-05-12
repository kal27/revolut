package com.example.myapplication.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * [EditText.afterTextChanged] executes the provided lambda when the text was edited by user
 *
 * Assumes the client will use [EditText.setTag] to set the [EditText] tag to a non-null value before
 * changing the text property programmatically. After this the tag should be reset to null.
 *
 * @param afterTextChanged a lambda to be executed after text has been changed
 *
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (tag == null) {
                afterTextChanged(s.toString())
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}