package kz.smart.calendar.extensions

import android.widget.EditText

inline fun EditText.getIntValue(): Int
{
    return this.text.toString().toInt()
}