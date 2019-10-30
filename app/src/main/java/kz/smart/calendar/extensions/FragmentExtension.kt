package kz.smart.calendar.extensions

import androidx.appcompat.app.AlertDialog
import kz.smart.calendar.moxy.MvpAppCompatFragment
import kz.smart.calendar.presentation.presenter.dialogs.ErrorDialogPresenter


inline fun MvpAppCompatFragment.showErrorAlertDialog(func: ErrorDialogPresenter.() -> Unit, title: String?, message: String?): AlertDialog =
    ErrorDialogPresenter(this.context!!, title, message).apply {
        func()
    }.create(null)