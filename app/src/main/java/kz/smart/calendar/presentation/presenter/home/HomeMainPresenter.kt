package kz.smart.calendar.presentation.presenter.home

import android.content.SharedPreferences
import androidx.databinding.ObservableBoolean
import com.arellomobile.mvp.InjectViewState
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.presentation.presenter.BasePresenter
import kz.smart.calendar.presentation.view.home.HomeMainView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class HomeMainPresenter(private var router: Router) : BasePresenter<HomeMainView>() {
    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    var isLoading = ObservableBoolean()

    init {
        App.appComponent.inject(this)

    }

    fun showEvent(id: Int){
        //router.navigateTo(Screens.ProductShowScreen(productId, productName))
    }

    fun reloadData() = reloadData(showRefresh = true)

    fun reloadData(showRefresh: Boolean) {
        isLoading.set(showRefresh)

       /* disposables.add(
            client.getProductsByContact(DataHolder.user!!.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                            result ->
                        run {
                            liveProducttResponse.value = result
                        }

                        disposables.add(client.getCategories()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                {
                                        result ->
                                    if (result.resultObject != null) {
                                        lookupDao.insertAll(result.resultObject)
                                    }
                                },
                                {
                                        error ->
                                    run {
                                        viewState?.showError(error)
                                    }
                                },
                                {
                                    isLoading.set(false)
                                }
                            ))
                    },
                    {
                        error ->
                        run {
                            isLoading.set(false)
                           viewState?.showError(error)
                        }
                    }
                )
        )
*/
    }
}
