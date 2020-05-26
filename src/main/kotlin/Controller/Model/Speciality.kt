package Controller.Model

enum class Speciality(s: String) {
    IT("IT"),
    GEO("GEO"),
    ARCH("ARCH"),
    BUILD("BUILD");

    override fun toString(): String {
        var res = StringBuilder("ENUM(")
        Speciality.values().iterator().forEach { res.append("'${it.name}',")}
        res.deleteCharAt(res.length-1)
        res.append(")")
        return res.toString()
    }


}