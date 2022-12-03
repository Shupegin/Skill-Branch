package ru.skillbranch.kotlinexample

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.annotation.VisibleForTesting
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.log


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
        val email = isEmailValid_1(login)
        return if (email){
            map[login]?.run {
                println(this.userInfo)
                if(checkPassword(password)) this.userInfo
                else null
            }

        }else{
            val _login = login.removeSymbol()
            map[_login]?.run {
                println(this.userInfo)
                if(checkPassword(password)) this.userInfo
                else null
            }
        }




    }

    fun isEmailValid_1(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    @SuppressLint("RestrictedApi")
    fun requestAccessCode(login : String) : Unit {
        val _login = login.removeSymbol()

        var user = map[_login]
        user?.newCode()

    }

    private fun String.removeSymbol() : String {

        return  this.replace("""[^+\d]""".toRegex(), "")

    }






    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder(){
        map.clear()
    }
}


