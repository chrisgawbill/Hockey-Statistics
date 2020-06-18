package com.example.hockeystatistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DarkModeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DarkModeFragment : Fragment(), OnDarkClickListener {

    private var param1: String? = null
    private var param2: String? = null
    private var darkList:ArrayList<String> = arrayListOf("Follow System Theme", "Light Theme", "Dark Theme")

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
        val rootView =  inflater.inflate(R.layout.fragment_dark_mode, container, false)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.dark_mode_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = DarkModeAdapter(this@DarkModeFragment, darkList)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        return rootView
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

    override fun onDarkClick(item: String, position: Int) {
        when(item){
            "Dark Theme"->{
                activity?.setTheme(R.style.DarkTheme)
                AppCompatDelegate.MODE_NIGHT_YES
            }
            "Light Theme"->{
                activity?.setTheme(R.style.AppTheme)
                AppCompatDelegate.MODE_NIGHT_NO
            }
            else->{
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        }
    }
}