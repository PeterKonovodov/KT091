import java.lang.NullPointerException
import java.lang.RuntimeException

object ChatService : ChatServiceCore()


open class ChatServiceCore {

    val allChats: HashMap<Int, Chat> = hashMapOf()
    private var chatId = 1
    private var messageId = 1


    private fun createChat(user1: User, user2: User): Chat? {
        val pairOfUsers = PairOfUsers(user1 = user1, user2 = user2)

        for (chatItem in allChats) {
            if (chatItem.value.users == pairOfUsers) return chatItem.value
        }

        allChats[chatId] = Chat(chatId, users = pairOfUsers)
        chatId++
        println("\n-=Пользователи ${user1.name} и ${user2.name} открыли чат=-")
        return allChats[chatId - 1]
    }

    fun deleteChatByUsers(user1: User, user2: User) {
        deleteChat(getChatByUsers(user1, user2))
    }

    fun deleteChat(chat: Chat) {
        for (chatItem in allChats) {
            if (chatItem.value == chat) chatItem.value.isDeleted = true
        }
    }

    fun createMessage(userFrom: User, userTo: User, messageText: String): Int {
        val chat = createChat(userFrom, userTo)
        chat!!.chatMessages.add(Message(messageId, ownerId = userFrom.id, text = messageText))
        messageId++
        return messageId - 1
    }

    fun readMessage(id: Int) {
        getMessageById(id).isRead = true
    }

    fun deleteMessage(id: Int) {
        getMessageById(id).isDeleted = true
        val chat = getChatById(getChatIdByMessageId(id))
        var deletedMessagesCount = 0
        for (message in chat.chatMessages) if (message.isDeleted) deletedMessagesCount++
        if (deletedMessagesCount == chat.chatMessages.size) chat.isDeleted = true
    }

    private fun getChatIdByMessageId(id: Int): Int {
        for (chatItem in allChats) {
            for (message in chatItem.value.chatMessages) {
                if (message.id == id) return chatItem.key
            }
        }
        throw MessageNotFoundException("Message id=$id not found!")
    }

    fun getMessageById(id: Int): Message {
        for (chatItem in allChats) {
            for (message in chatItem.value.chatMessages) {
                if (message.id == id) {
                    return message
                }
            }
        }
        throw MessageNotFoundException("Message id=$id not found!")
    }


    fun getChatById(id: Int): Chat {
        try {
            return allChats[id]!!
        } catch (e: NullPointerException) {
            throw ChatNotFoundException("Chat id $id not found!")
        }
    }

    fun getChatByUsers(user1: User, user2: User): Chat {
        val pairOfUsers = PairOfUsers(user1 = user1, user2 = user2)
        for (chatItem in allChats) {
            if (chatItem.value.users == pairOfUsers) return chatItem.value
        }
        throw ChatNotFoundException("Chat for users ${user1.name} and ${user2.name} not found!")
    }

    fun getUnreadChatsCount(): Int {
        var chatCount = 0
        allChats.all { chatItem -> if (!chatItem.value.isDeleted && chatItem.value.chatMessages.any { !it.isRead }) chatCount++; true }
        return chatCount
    }


    fun getMessagesStringByUsers(user1: User, user2: User): String {
        var outString = ""
        getChatByUsers(user1, user2).chatMessages.all { message ->
            outString += "${if (message.ownerId == user1.id) user1.name else user2.name}: ${message.text}\n"; true
        }
        return outString
    }


    fun getChatsNumber(): Int {
        var chatsNumber = 0
        allChats.all { if (!it.value.isDeleted) chatsNumber++; true }
        return chatsNumber
    }
}


fun User.getChatsList(): MutableList<Int> {
    val chatList: MutableList<Int> = mutableListOf()
    for (chatItem in ChatService.allChats) {
        if (chatItem.value.users.contains(this)) chatList.add(chatItem.value.id)
    }
    return chatList
}


fun PairOfUsers.contains(user: User): Boolean {
    if (this.user1 == user) return true
    if (this.user2 == user) return true
    return false
}


class ChatNotFoundException(message: String) : RuntimeException(message)
class MessageNotFoundException(message: String) : RuntimeException(message)
