package com.example.soccerapps.view.fragment.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.soccerapps.R
import com.example.soccerapps.view.adapter.ViewPageAdapter
import com.example.soccerapps.view.fragment.favorite.LastFavoriteFragment
import com.example.soccerapps.view.fragment.favorite.NextFavoriteFragment
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        tabLayout = view.findViewById(R.id.tab_favorite)
        viewPager = view.findViewById(R.id.viewpager_favorite)
        setViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        return view
    }

    private fun setViewPager(viewpagerFavorite: ViewPager?) {
        val adapter = fragmentManager?.let { ViewPageAdapter(it) }
        val lastFavorite = LastFavoriteFragment.lastFavoriteInstance()
        adapter?.addFragment(lastFavorite, getString(R.string.last_match_text))
        val nextFavorite = NextFavoriteFragment.nextFavoriteInstance()
        adapter?.addFragment(nextFavorite, getString(R.string.next_match_text))
        viewpagerFavorite?.adapter = adapter
    }
}
