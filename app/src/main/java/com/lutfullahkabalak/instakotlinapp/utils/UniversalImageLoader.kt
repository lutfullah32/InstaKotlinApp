package com.lutfullahkabalak.instakotlinapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.lutfullahkabalak.instakotlinapp.R
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener

class UniversalImageLoader(val mContext:Context) {

    val config: ImageLoaderConfiguration?
        get(){

            var defaultOptions = DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .cacheOnDisk(true).cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(FadeInBitmapDisplayer(400)).build()

            return ImageLoaderConfiguration.Builder(mContext)
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(WeakMemoryCache())
                    .diskCacheSize(100*1024*1024).build()

        }

    companion object {
        private val defaultImage = R.drawable.ic_profile

        fun setImage(imgURL:String,imageView:ImageView,mProgressbar:ProgressBar?,ilkKisim:String){

            val imageLoader=ImageLoader.getInstance()
            imageLoader.displayImage(ilkKisim+imgURL,imageView,object :ImageLoadingListener{
                override fun onLoadingComplete(
                    imageUri: String?,
                    view: View?,
                    loadedImage: Bitmap?
                ) {
                    if(mProgressbar!=null){
                        mProgressbar.visibility=View.GONE
                    }
                }

                override fun onLoadingStarted(imageUri: String?, view: View?) {
                    if(mProgressbar!=null){
                        mProgressbar.visibility=View.VISIBLE
                    }
                }

                override fun onLoadingCancelled(imageUri: String?, view: View?) {
                    if(mProgressbar!=null){
                        mProgressbar.visibility=View.GONE
                    }
                }

                override fun onLoadingFailed(
                    imageUri: String?,
                    view: View?,
                    failReason: FailReason?
                ) {
                    if(mProgressbar!=null){
                        mProgressbar.visibility=View.GONE
                    }

                }

            })

        }

    }

}