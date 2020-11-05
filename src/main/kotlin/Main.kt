fun main() {


    UserService.addUser(User(name = "Вася"))
    UserService.addUser(User(name = "Гиви"))
    UserService.addUser(User(name = "Дональд"))
    UserService.addUser(User(name = "Барак"))
    UserService.addUser(User(name = "Ангела"))

    val vasya = UserService.findUserByName("Вася")
    val givi = UserService.findUserByName("Гиви")
    val donald = UserService.findUserByName("Дональд")
    val obama = UserService.findUserByName("Барак")
    val angela = UserService.findUser{ user -> user.id == UserService.findUser{it.name == "Ангела"}.id}


    ChatService.createMessage(vasya, givi, "Превед!")
    ChatService.createMessage(givi, vasya, "Сам превед!")
    ChatService.createMessage(donald, angela, "Меркелиха, дарова!")
    ChatService.createMessage(angela, donald, "Драссьте!")
    ChatService.createMessage(givi, vasya, "Чо замолчал?")
    ChatService.createMessage(donald, angela, "Обама тебе привет передавал с пенсии!")
    ChatService.createMessage(angela, donald, "Дада, что там с выборами?")
    ChatService.createMessage(donald, angela, "*ё$&!!!")
    ChatService.createMessage(angela, donald, "Так все плохо?")
    ChatService.createMessage(angela, obama, "Ваш рыжий там совсем куку? Держи банан!")
    ChatService.createMessage(donald, angela, "завзята боротьба йде, якщо програю найдти де сховатися!")
    ChatService.createMessage(angela, donald, "сез аны татар телендә эшли аласызмы?")


    println("\nВсего чатов: ${ChatService.getChatsNumber()}")
    println("Из них нечитанных: ${ChatService.getUnreadChatsCount()}")

    ChatService.deleteChat(ChatService.getChatByUsers(givi, vasya))
    println("\nПосле удаления одного из чатов:")
    println("Всего чатов: ${ChatService.getChatsNumber()}")
    println("Из них нечитанных: ${ChatService.getUnreadChatsCount()}")

    println("Количество чатов пользователя ${angela.name}: ${angela.getChatsList().size} ")

    println("\nСообщения пользователей ${angela.name} и ${donald.name}:\n${ChatService.getMessagesStringByUsers(donald, angela)}")

}

