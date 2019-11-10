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
import kz.smart.calendar.api.response.CategoriesResponce
import kz.smart.calendar.api.response.ListResponse
import kz.smart.calendar.models.objects.Category
import kz.smart.calendar.modules.settings.view.settings.CategoriesView
import kz.smart.calendar.presentation.presenter.BasePresenter
import javax.inject.Inject

@InjectViewState
class CategoriesPresenter: BasePresenter<CategoriesView>() {

    var liveCategoriesResponse = MutableLiveData<BaseResponse<ListResponse<Category>>>()

    @Inject
    lateinit var client: ApiManager

    init {
        App.appComponent.inject(this)
    }

    private var disposable: Disposable? = null

    fun getCategories(){
        viewState.showProgress()
        disposable = client.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    result ->
                    run{
                        viewState.hideProgress()
                        liveCategoriesResponse.value = result
                    }
                },
                {
                    error ->
                    run {
                        viewState.hideProgress()
                        viewState.showError(error)
                    }
                }
            )
    }

    fun observeCategoryResponseBoundary(): MutableLiveData<BaseResponse<ListResponse<Category>>>{
        return liveCategoriesResponse
    }
}