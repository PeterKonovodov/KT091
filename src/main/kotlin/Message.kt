data class Message(
        val id: Int,
        val ownerId: Int,
        val text: String,
        var isRead: Boolean = false,
        var isDeleted: Boolean = false
)