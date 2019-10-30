package kz.smart.calendar.modules.home.presentation

interface Tagger {

    fun getTag(position: Int): String?

    fun getId(position: Int): Int?
}