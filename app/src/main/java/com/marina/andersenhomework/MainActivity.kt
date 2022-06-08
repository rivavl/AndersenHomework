package com.marina.andersenhomework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marina.andersenhomework.fragments.ContactsListFragment


class MainActivity : AppCompatActivity() {

    val contacts = ContactsDataSource().getContacts()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ContactsListFragment())
            .commit()
    }
}