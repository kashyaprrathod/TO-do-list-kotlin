package com.kotlin.todolist.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.prefs.Preferences

class SharedPrefHelpers {

    lateinit var sharedPref : SharedPreferences;

    var SHAREDPREFNAME = "TODOLISTPREF";
    var DARKMODE = "DARKMODE"
    var LARGEFONT = "LARGEFONT";
    var FADDEDCOLOR = "FADDEDCOLOR"

    companion object{
        var INSTANCE : SharedPrefHelpers? = null;

        fun getInstabce(context: Context) : SharedPrefHelpers{

            if(INSTANCE == null){
                INSTANCE = SharedPrefHelpers()
            }


            INSTANCE!!.createSharedPrefObject(context);

            return INSTANCE as SharedPrefHelpers;
        }

    }

    private fun createSharedPrefObject(context: Context) {
        sharedPref = context.getSharedPreferences(SHAREDPREFNAME,Context.MODE_PRIVATE)
    }

    fun setDarkMode(darkMode:Boolean){
        sharedPref.edit().putBoolean(DARKMODE,darkMode).commit();
    }

    fun getDarkMode() : Boolean{
        return sharedPref.getBoolean(DARKMODE,false);
    }

    fun setLargeFont(largeFont : Boolean){
        sharedPref.edit().putBoolean(LARGEFONT,largeFont).commit();
    }

    fun getLargeFont() : Boolean{
        return sharedPref.getBoolean(LARGEFONT,false);
    }

    fun setFaddedLine(faddedLine:Boolean){
        sharedPref.edit().putBoolean(FADDEDCOLOR,faddedLine).commit();
    }

    fun getFaddedLine():Boolean{
        return sharedPref.getBoolean(FADDEDCOLOR,false);
    }


}