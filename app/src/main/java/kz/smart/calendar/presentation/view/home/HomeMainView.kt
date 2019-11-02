package kz.smart.calendar.presentation.view.home

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.smart.calendar.presentation.BaseView

@StateStrategyType(SkipStrategy::class)
interface HomeMainView : BaseView {
    fun setBottomViewVisibility(isVisible: Boolean)
}
