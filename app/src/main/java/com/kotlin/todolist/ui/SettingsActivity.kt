package com.kotlin.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import com.kotlin.todolist.R
import com.kotlin.todolist.utils.SharedPrefHelpers
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    var largeFont = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        largeFont = SharedPrefHelpers.getInstabce(MainActivity@this).getLargeFont()

        changeTheme();

        setUi();

        setContentView(R.layout.activity_settings)

        setUpUi();

        Listners();

    }

    private fun changeTheme() {
        if(SharedPrefHelpers.getInstabce(MainActivity@this).getDarkMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setUi() {
        if(largeFont)
            setTheme(R.style.smallfont)
        else
            setTheme(R.style.largefont)
    }

    private fun setUpUi() {

        sw_darkmod.isChecked = SharedPrefHelpers.getInstabce(SettingsActivity@ this).getDarkMode()
        sw_largefont.isChecked =
            SharedPrefHelpers.getInstabce(SettingsActivity@ this).getLargeFont()
        sw_fadded.isChecked = SharedPrefHelpers.getInstabce(SettingsActivity@ this).getFaddedLine()

    }

    private fun Listners() {
        sw_darkmod.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isPressed) {
                SharedPrefHelpers.getInstabce(SettingsActivity@ this).setDarkMode(b)
                changeTheme()
            }
        })

        sw_largefont.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isPressed) {
                SharedPrefHelpers.getInstabce(SettingsActivity@ this).setLargeFont(b)
                largeFont = b;
                setUi()
            }
        })

        sw_fadded.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isPressed) {
                SharedPrefHelpers.getInstabce(SettingsActivity@ this).setFaddedLine(b)
            }
        })
    }
}