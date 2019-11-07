package kz.smart.calendar.modules.settings.view.settings

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.smart.calendar.presentation.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface CategoriesView: BaseView {
}