package kz.smart.calendar.modules.myevents.domain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.*
import kotlinx.android.synthetic.main.activity_preview.*
import kz.smart.calendar.R
import kz.smart.calendar.ui.activity.home.HomeActivity

class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
    }

    override fun onStart() {
        super.onStart()
        btn_continue.setOnClickListener {
            pop()
        }
    }

    fun pop()
    {
        ActivityNavigator(this).popBackStack()
        /*val activityNavigator = ActivityNavigator( this)
        activityNavigator.navigate(
            activityNavigator.createDestination().setIntent(
                Intent(
                   this,
                    HomeActivity::class.java
                )
            ), null, null, null
        )*/
    }
}
