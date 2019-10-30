package photograd.kz.smart.presentation.presenter.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.smart.calendar.models.requests.LoginRequestModel
import photograd.kz.smart.presentation.view.login.LoginFragmentView

@InjectViewState
class LoginPresenter : MvpPresenter<LoginFragmentView>() {
    val user = LoginRequestModel("",_password = "")
}
