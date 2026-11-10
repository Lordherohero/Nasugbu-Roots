package com.mygroup.nasugburoots.model

interface Searchable {                                  //tourist spot
    fun searchByName(name: String): Boolean
    fun searchByCategory(category: String): Boolean
}