package com.marina.andersenhomework.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.marina.andersenhomework.ContactListAdapter
import com.marina.andersenhomework.MainActivity
import com.marina.andersenhomework.R


class ContactsListFragment : Fragment() {

    private lateinit var contactListAdapter: ContactListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        contactListAdapter.submitList((requireActivity() as MainActivity).contacts)
        println("=============================================================")
        println(contactListAdapter.itemCount)
    }

    private fun setupRecyclerView() {
        val rvContactList = view?.findViewById<RecyclerView>(R.id.rv)
        with(rvContactList) {
            contactListAdapter = ContactListAdapter()
            this?.adapter = contactListAdapter
        }

        setupLongClickListener()
        setupClickListener()
    }

    private fun setupClickListener() {
        contactListAdapter.onContactClickListener = { contact ->
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.fragment_container,
                    ContactFragment.newInstance(contact)
                )
                .commit()
        }
        contactListAdapter.submitList((requireActivity() as MainActivity).contacts)
    }

    private fun setupLongClickListener() {
        contactListAdapter.onContactLongClickListener = {
            (requireActivity() as MainActivity).contacts.remove(it)
            contactListAdapter.notifyDataSetChanged()
        }
    }
}