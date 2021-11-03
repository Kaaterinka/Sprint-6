package com.example.qwer

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class Service {
    private var addressBook = ConcurrentHashMap<Int, AddressBook>()
    private var index = 0


    fun addAddress(address: AddressBook) {
        addressBook.putIfAbsent(index++, address)
    }

    fun showAll(query: Map<String, String>): ConcurrentHashMap<Int, AddressBook> {
        return if (query.isEmpty())
            addressBook
        else {
            val result = ConcurrentHashMap<Int, AddressBook>()
            val id = query["id"]!!.toInt()
            addressBook[id]?.let { result.put(id, it) }
            result
        }
    }

    fun findById(id: Int): AddressBook? {
        return addressBook[id]
    }

    fun update(id: Int, address: AddressBook) {
        addressBook[id]=address
    }

    fun delete(id:Int){
        addressBook.remove(id)
    }
}