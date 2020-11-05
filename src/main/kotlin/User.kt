data class User(
        val id: Int = 1,
        val name: String,
        var isBanned: Boolean = false
        )

data class PairOfUsers(
        val user1: User,
        val user2: User
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as PairOfUsers

                //особенность сравнения - в инвариантности (user1, user2) или (user2, user1)
                if(user1 == other.user1 && user2 == other.user2) return true
                if(user1 == other.user2 && user2 == other.user1) return true

                return false
        }

        //здесь также процесс инвариантен к последовательности пользователей
        override fun hashCode(): Int {
                return user1.hashCode() xor user2.hashCode()
        }
}



