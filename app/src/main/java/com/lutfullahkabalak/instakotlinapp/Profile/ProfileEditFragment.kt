package com.lutfullahkabalak.instakotlinapp.Profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lutfullahkabalak.instakotlinapp.R
import com.lutfullahkabalak.instakotlinapp.utils.UniversalImageLoader
import com.nostra13.universalimageloader.core.ImageLoader
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileEditFragment : Fragment() {

    lateinit var circleProfileImage: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)

        circleProfileImage = view.circleProfileImage


        setupProfilePicture()

        view.imgClose.setOnClickListener {

            activity?.onBackPressed()

        }

        return view
    }



    private fun setupProfilePicture() {
        //https://www.technopat.net/wp-content/uploads/2013/01/google-android-maskot.png

        var imgURL = "www.technopat.net/wp-content/uploads/2013/01/google-android-maskot.png"
        UniversalImageLoader.setImage(imgURL, circleProfileImage,null,"https://")
    }


}
