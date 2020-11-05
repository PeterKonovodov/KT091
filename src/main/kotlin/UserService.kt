import java.lang.RuntimeException

object UserService : UserServiceCore() {}

open class UserServiceCore {
    val usersList = mutableListOf<User>()
    var userId = 1

    fun MutableList<User>.addUser(user: User): Int {
        if (!this.contains(user)) {
            this.add(user.copy(id = userId))
            userId++
        }
        return userId - 1
    }

    fun addUser(user: User): Int {
        return usersList.addUser(user)
    }

    fun findUser(selector: (User) -> Boolean): User {
        for (user in usersList) if (selector(user)) return user
        throw UserNotFoundException("Пользователь не найден!")
    }

    fun findUserByName(name: String): User = findUser { it.name == name }
    fun findUserById(id: Int): User = findUser { it.id == id }

}

class UserNotFoundException(message: String) : RuntimeException(message)

