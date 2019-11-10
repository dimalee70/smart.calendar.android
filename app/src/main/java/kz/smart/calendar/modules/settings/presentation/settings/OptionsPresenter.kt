package kz.smart.calendar.modules.settings.presentation.settings

import androidx.lifecycle.MutableLiveData
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.api.response.BaseResponse
import kz.smart.calendar.api.response.ListResponse
import kz.smart.calendar.models.objects.Option
import kz.smart.calendar.modules.settings.view.settings.CategoriesView
import kz.smart.calendar.modules.settings.view.settings.OptionsView
import kz.smart.calendar.presentation.presenter.BasePresenter
import javax.inject.Inject

@InjectViewState
class OptionsPresenter: BasePresenter<OptionsView>(){

    var liveOptionsResponse = MutableLiveData<BaseResponse<ListResponse<Option>>>()

    @Inject
    lateinit var client: ApiManager

    init {
        App.appComponent.inject(this)
    }

    private var disposable: Disposable? = null

    fun getOptions(){
        viewState.showProgress()
        disposable = client.getOptions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
                run{
                    viewState.hideProgress()
                    liveOptionsResponse.value = result
                }
            },
            {
                    error ->
                run {
                    viewState.hideProgress()
                    viewState.showError(error)
                }
            })
    }

    fun observeOptionsResponseBoundary(): MutableLiveData<BaseResponse<ListResponse<Option>>>{
        return liveOptionsResponse
    }
}