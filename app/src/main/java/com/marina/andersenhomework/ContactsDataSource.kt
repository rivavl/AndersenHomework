package com.marina.andersenhomework

import com.marina.andersenhomework.entity.Contact
import io.github.serpro69.kfaker.Faker
import kotlin.random.Random

class ContactsDataSource {

    private val faker = Faker()

    fun getContacts(): MutableList<Contact> {
        val contactsList = mutableListOf<Contact>()
        var name = ""
        var number = ""
        var photoUrl = ""
        var contact: Contact

        for (i in 0..200) {
            name = faker.name.firstName() + " " + faker.name.lastName()
            number = faker.phoneNumber.phoneNumber()
            photoUrl = "https://picsum.photos/id/${Random.nextInt(500)}/400"
            contact = Contact(i, name, number, photoUrl)
            contactsList.add(contact)
        }
        return contactsList
    }
}