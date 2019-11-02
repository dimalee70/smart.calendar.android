package photograd.kz.smart.presentation.view.login

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.smart.calendar.presentation.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface LoginFragmentView : BaseView {
    fun validateUserData(email: String, password: String)
    fun validateUserDataSuccessfull()
    fun validateUserDataError(email: Int, password: Int)
}
