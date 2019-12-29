package com.lutfullahkabalak.instakotlinapp.utils

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.lutfullahkabalak.instakotlinapp.Home.HomeActivity
import com.lutfullahkabalak.instakotlinapp.News.NewsActivity
import com.lutfullahkabalak.instakotlinapp.Profile.ProfileActivity
import com.lutfullahkabalak.instakotlinapp.R
import com.lutfullahkabalak.instakotlinapp.Search.SearchActivity
import com.lutfullahkabalak.instakotlinapp.Share.ShareActivity

class BottomnavigationViewHelper {



    companion object{
        fun setupBottomNavigationView(bottomnavigationViewEx: BottomNavigationViewEx){

            bottomnavigationViewEx.enableAnimation(false)
            bottomnavigationViewEx.enableItemShiftingMode(false)
            bottomnavigationViewEx.enableShiftingMode(false)
            bottomnavigationViewEx.setTextVisibility(false)

        }

        fun setupNavigation(context: Context,bottomnavigationViewEx: BottomNavigationViewEx){
            bottomnavigationViewEx.onNavigationItemSelectedListener=object :BottomNavigationView.OnNavigationItemSelectedListener{
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                     when(item.itemId){
                         R.id.ic_home ->{
                            val intent = Intent(context,HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                             context.startActivity(intent)
                             return true
                         }
                         R.id.ic_search ->{
                             val intent = Intent(context, SearchActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                             context.startActivity(intent)
                             return true
                         }
                         R.id.ic_share ->{
                             val intent = Intent(context, ShareActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                             context.startActivity(intent)
                             return true

                         }
                         R.id.ic_news ->{
                             val intent = Intent(context,NewsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                             context.startActivity(intent)
                             return true

                         }
                         R.id.ic_profile ->{
                             val intent = Intent(context, ProfileActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                             context.startActivity(intent)
                             return true

                         }
                     }
                    return true
                }

            }
        }
    }
}