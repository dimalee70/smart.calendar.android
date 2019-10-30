package kz.smart.calendar.modules.home.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/*class HomeViewPagerAdapter(
    fm: FragmentManager,
    behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
    private var pagesCount: Int = 2,
    private var tagger: Tagger
) : FragmentStatePagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        val tag = tagger.getTag(position)

        when (tag) {
            CurrentGamesFragment.TAG -> return CurrentGamesFragment.newInstance()
            MissionsFragment.TAG -> return MissionsFragment.newInstance()
            DuelsFragment.TAG -> return DuelsFragment.newInstance()
            StoreCategoryFragment.TAG -> return StoreCategoryFragment.newInstance(id = tagger.getId(position)!!)
            RatingFragment.TAG -> return RatingFragment.newInstance()
            FriendsRatingFragment.TAG -> return FriendsRatingFragment.newInstance()
            BaseSettingsFragment.TAG -> return BaseSettingsFragment.newInstance()
            ProfileFragment.TAG -> return ProfileFragment.newInstance()
            else -> return CurrentGamesFragment.newInstance()
        }
    }

    override fun getCount(): Int = pagesCount

}*/