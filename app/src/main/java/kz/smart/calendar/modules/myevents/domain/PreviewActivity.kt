package kz.smart.calendar.modules.myevents.domain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ActivityNavigator
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_preview.*
import kz.smart.calendar.R

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
        //findNavController(R.id.nav_graph_add_event).popBackStack(R.id.addEventFragment, true)
        ActivityNavigator(this).popBackStack()
        //ActivityNavigator(this)
    }
}
