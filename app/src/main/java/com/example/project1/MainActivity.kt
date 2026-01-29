import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project1.database.AppDatabase
import com.example.project1.ui.login.LoginScreen
import com.example.project1.ui.login.LoginState
import com.example.project1.ui.login.LoginViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDao = AppDatabase.getDatabase(applicationContext).userDao()

        val factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                    return LoginViewModel(userDao) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        val loginViewModel: LoginViewModel by viewModels { factory }

        setContent {
            val state = loginViewModel.loginState

            LoginScreen { username, password ->
                loginViewModel.login(username, password)
            }

            LaunchedEffect(state) {
                when (state) {
                    is LoginState.Success -> {
                        Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                        loginViewModel.reset()

                        // TODO: Navigate to your next screen here (card list)
                    }
                    is LoginState.Error -> {
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                        loginViewModel.reset()
                    }
                    else -> Unit
                }
            }
        }
    }
}