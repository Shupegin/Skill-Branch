package ru.skillbranch.kotlinexample

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting


object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName : String,
        email: String,
        password : String
    ) : User{
        return User.makeUser(fullName, email = email, password = password)
            .also { user ->
                if (map[user.login] == null) {
                    map[user.login] = user
                }else { throw IllegalArgumentException("Email or phone have a blank")
                }
            }
    }


    fun registerUserByPhone(
        fullName : String,
        rawPhone : String
    ) : User {
        return User.makeUser(fullName, phone = rawPhone)
            .also { user ->
               if(map[user.login] == null){
                   map[user.login] = user
               }else{
                   throw IllegalArgumentException("Email or phone must not be null or blank")
               }
            }
    }

@SuppressLint("SuspiciousIndentation")
fun loginUser (login : String, password: String) : String?{
    val _login = login.removeSymbol()

        return map[_login.trim()]?.run {
            println(this.userInfo)
            if(checkPassword(password)) this.userInfo
            else null
        }
    }

    @SuppressLint("RestrictedApi")
    fun requestAccessCode(login : String) : Unit {
        val _login = login.removeSymbol()

        var user = map[_login]
        user?.newCode()

    }

    private fun String.removeSymbol() : String {
        return this.replace("""[^+\d]""".toRegex(), "")

    }



    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder(){
        map.clear()
    }
}

