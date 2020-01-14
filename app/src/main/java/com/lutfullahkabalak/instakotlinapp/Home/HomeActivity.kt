package com.lutfullahkabalak.instakotlinapp.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lutfullahkabalak.instakotlinapp.R
import com.lutfullahkabalak.instakotlinapp.utils.BottomnavigationViewHelper
import com.lutfullahkabalak.instakotlinapp.utils.HomePagerAdapter
import com.lutfullahkabalak.instakotlinapp.utils.UniversalImageLoader
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val ACTIVITY_NO=0
    private val TAG="HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initImageLoader()
        setupNavigationView()
        setupHomeViewPager()
    }



    fun setupNavigationView(){
        BottomnavigationViewHelper.setupBottomNavigationView(bottomNavigationView)
        BottomnavigationViewHelper.setupNavigation(this,bottomNavigationView)

        var menu=bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }

    fun setupHomeViewPager() {
        var homePagerAdapter=HomePagerAdapter(supportFragmentManager)
        homePagerAdapter.addFragment(CameraFragment())
        homePagerAdapter.addFragment(HomeFragment())
        homePagerAdapter.addFragment(MessageFragment())

        homeViewPager.adapter = homePagerAdapter
        homeViewPager.setCurrentItem(1)
    }

    private fun initImageLoader(){

        var universalImageLoader= UniversalImageLoader(this)
        //if (universalImageLoader != null) {
            ImageLoader.getInstance().init(universalImageLoader.config)
        //}

    }
}
