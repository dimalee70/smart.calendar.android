package kz.smart.calendar.modules.home.data.model

//Wrapper for fragments
class NamedPagerElements(
    /**
     * Fragment's TAG
     * */
    val tag: String,
    /**
     * use categoryId for Categories from store
     * */
    val categoryId: Int? = null
)