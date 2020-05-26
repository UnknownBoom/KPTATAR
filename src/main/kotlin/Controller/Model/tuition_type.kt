package Controller.Model

enum class Tuition_type(s: String) {
    Full_Time("Full_Time"),
    Distance("Distance");
    override fun toString(): String {
        var res = StringBuilder("ENUM(")
        Tuition_type.values().iterator().forEach { res.append("'${it.name}',")}
        res.deleteCharAt(res.length-1)
        res.append(")")
        return res.toString()
    }
}