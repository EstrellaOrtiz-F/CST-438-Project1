package com.example.project1.ui.login

class LoginViewModel (
    private val userDao: UserDao
) : ViewModel() {

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = userDao.getUserByUsername(username)

            if (user == null) {
                loginState = LoginState.Error("User not found")
                return@launch
            }

            if (!PasswordHasher.matches(password, user.passwordHash)) {
                loginState = LoginState.Error("Invalid password")
                return@launch
            }

            loginState = LoginState.Success
        }
    }
}