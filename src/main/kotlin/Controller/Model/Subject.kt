package Controller.Model

enum class Subject(s: String) {
    MATH("MATH"),
    RUS("RUS"),
    BIO("BIO"),
    GEO("GEO");
    override fun toString(): String {
        var res = StringBuilder("ENUM(")
        Subject.values().iterator().forEach { res.append("'${it.name}',")}
        res.deleteCharAt(res.length-1)
        res.append(")")
        return res.toString()
    }

}