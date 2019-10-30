package photograd.kz.smart.presentation.view.login

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.smart.calendar.presentation.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface LoginProcessView : BaseView {
    fun showHome()
    fun onLoginClick()
    fun onRegisterClick()
}
