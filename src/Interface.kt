interface Searchable {                                  //tourist spot
    fun searchByName(name: String): Boolean
    fun searchByCategory(category: String): List<String>
}