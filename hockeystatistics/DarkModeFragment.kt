package com.example.hockeystatistics

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_dark_mode.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DarkModeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DarkModeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        activity?.setTitle("Dark Mode")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dark_mode, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var dark  = view.findViewById<RadioButton>(R.id.dark_theme)
        var light = view.findViewById<RadioButton>(R.id.light_theme)
        var system = view.findViewById<RadioButton>(R.id.system_default)
        val pref: SharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)!!
        val editor: SharedPreferences.Editor = pref.edit()

        when(pref.getInt("themeValue", 0)){
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> system_default.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> light_theme.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES ->  dark_theme.isChecked = true
            else -> system_default.isChecked = true
        }
        dark.setOnClickListener {
            editor.putInt("themeValue", AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            editor.commit()
        }
        light.setOnClickListener {
            editor.putInt("themeValue", AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            editor.commit()
        }
        system_default.setOnClickListener{
            editor.putInt("themeValue", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            editor.commit()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DarkModeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DarkModeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    /*
    override fun onDarkClick(item: String, position: Int) {
        val pref: SharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)!!
        val editor: SharedPreferences.Editor = pref.edit()
        when(item){
            "Dark Theme"->{
                editor.putInt("themeValue", AppCompatDelegate.MODE_NIGHT_YES)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            "Light Theme"->{
                editor.putInt("themeValue", AppCompatDelegate.MODE_NIGHT_NO)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else->{
                editor.putInt("themeValue", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
        editor.commit()
    }
    */

}