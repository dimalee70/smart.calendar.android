package kz.smart.calendar.presentation.view

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.smart.calendar.presentation.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface MainAppView : BaseView {

}
