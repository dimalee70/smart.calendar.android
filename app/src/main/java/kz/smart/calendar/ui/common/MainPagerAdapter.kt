package kz.smart.calendar.ui.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kz.smart.calendar.ui.fragment.BaseFragment

class MainPagerAdapter(fragment: Fragment, private val fragments:ArrayList<BaseFragment>): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}