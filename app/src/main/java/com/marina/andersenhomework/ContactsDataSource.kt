package com.marina.andersenhomework

import com.marina.andersenhomework.entity.Contact

class ContactsDataSource {
    fun getContacts(): MutableList<Contact> {
        return mutableListOf(
            Contact("Amanda Seyfried", "+11111111111"),
            Contact("Alicia Vikander", "+11111111122"),
            Contact("Anne Hathaway", "+11111111133"),
            Contact("Emma Stone", "+11111111144")
        )
    }
}