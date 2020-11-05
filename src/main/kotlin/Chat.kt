data class Chat(
        val id: Int = 0,
        val users: PairOfUsers,
        val chatMessages: MutableList<Message> = mutableListOf(),
        var isDeleted: Boolean = false
)
