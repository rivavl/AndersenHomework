package com.marina.andersenhomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.marina.andersenhomework.MainActivity
import com.marina.andersenhomework.R

class ContactsListFragment : Fragment() {

    private val names = mutableListOf<TextView>()
    private val numbers = mutableListOf<TextView>()
    private val tvContacts = mutableListOf<LinearLayout>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts_list, container, false)
        findViews(view)
        fillContacts(view)
        setOnClickListeners(view)
        return view
    }

    private fun findViews(view: View?) {
        names.clear()
        names.add(view?.findViewById(R.id.name_1) as TextView)
        names.add(view.findViewById(R.id.name_2) as TextView)
        names.add(view.findViewById(R.id.name_3) as TextView)
        names.add(view.findViewById(R.id.name_4) as TextView)

        numbers.clear()
        numbers.add(view.findViewById(R.id.number_1) as TextView)
        numbers.add(view.findViewById(R.id.number_2) as TextView)
        numbers.add(view.findViewById(R.id.number_3) as TextView)
        numbers.add(view.findViewById(R.id.number_4) as TextView)

        tvContacts.clear()
        tvContacts.add(view.findViewById(R.id.contact_1) as LinearLayout)
        tvContacts.add(view.findViewById(R.id.contact_2) as LinearLayout)
        tvContacts.add(view.findViewById(R.id.contact_3) as LinearLayout)
        tvContacts.add(view.findViewById(R.id.contact_4) as LinearLayout)
    }

    private fun fillContacts(view: View) {
        var i = 0
        for (contact in (requireActivity() as MainActivity).contacts) {
            names[i].text = contact.name
            numbers[i].text = contact.number
            i++
        }
    }

    private fun setOnClickListeners(view: View) {
        tvContacts[0].setOnClickListener { setOnClickListener(0) }
        tvContacts[1].setOnClickListener { setOnClickListener(1) }
        tvContacts[2].setOnClickListener { setOnClickListener(2) }
        tvContacts[3].setOnClickListener { setOnClickListener(3) }
    }

    private fun setOnClickListener(i: Int) {
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.fragment_container,
                ContactFragment.newInstance(i)
            )
            .commit()
    }
}