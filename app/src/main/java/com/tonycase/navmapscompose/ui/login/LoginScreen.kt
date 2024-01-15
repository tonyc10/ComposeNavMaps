package com.tonycase.navmapscompose.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tonycase.navmapscompose.ui.RaceAppScreen
import com.tonycase.navmapscompose.ui.RacesTitle
import com.tonycase.navmapscompose.ui.theme.RaceAppTheme
import kotlinx.coroutines.launch

/**
 * Much of this class adopted from https://medium.com/@WhiteBatCodes/simple-login-page-in-jetpack-compose-9c92af690234
 */
fun NavGraphBuilder.loginScreen(
   onLogin: () -> Unit
) {
   composable(
      RaceAppScreen.Login.name,
   ) {
       val viewModel: LoginViewModel = hiltViewModel()
       val loginState by viewModel.uiState.collectAsState()

      val scope = rememberCoroutineScope()
      val context = LocalContext.current

      // can add events handled by VM here.
      LoginScreen(
         uiState = loginState,
         onUserNameChange = { username -> viewModel.usernameUpdated(username) },
         onPasswordChange = { passwd -> viewModel.passwordUpdated(passwd) },
         onSubmit = {
            scope.launch {
               if (viewModel.submitCredentials()) {
                  onLogin()
               } else {
                  Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show();
               }
            }
         },
      )
   }
}

@Composable
fun LoginScreen(
   uiState: Credentials,
   onUserNameChange: (String) -> Unit = { },
   onPasswordChange: (String) -> Unit = { },
   onSubmit: () -> Unit = { },
) {
//   Surface {
      Column(
         verticalArrangement = Arrangement.Top,
         horizontalAlignment = Alignment.CenterHorizontally,
         modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 24.dp)
      ) {
         RacesTitle(text = "Login or Create Account")
         Spacer(modifier = Modifier.height(16.dp))
         LoginField(
            value = uiState.login,
            onChange = onUserNameChange,
            modifier = Modifier.fillMaxWidth()
         )
         PasswordField(
            value = uiState.pwd,
            onChange = onPasswordChange,
            submit = onSubmit,
            modifier = Modifier.fillMaxWidth()
         )
         Spacer(modifier = Modifier.height(10.dp))
         Button(
            onClick = onSubmit,
            enabled = uiState.isNotEmpty(),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth()
         ) {
            Text("Login")
         }
      }
//   }
}

@Composable
private fun LoginField(
   value: String,
   onChange: (String) -> Unit,
   modifier: Modifier = Modifier,
   label: String = "Login",
   placeholder: String = "Enter your Login"
) {

   val focusManager = LocalFocusManager.current
   val leadingIcon = @Composable {
      Icon(
         Icons.Default.Person,
         contentDescription = "",
         tint = MaterialTheme.colorScheme.primary
      )
   }

   TextField(
      value = value,
      onValueChange = onChange,
      modifier = modifier,
      leadingIcon = leadingIcon,
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
      keyboardActions = KeyboardActions(
         onNext = { focusManager.moveFocus(FocusDirection.Down) }
      ),
      placeholder = { Text(placeholder) },
      label = { Text(label) },
      singleLine = true,
      visualTransformation = VisualTransformation.None
   )
}

@Composable
fun PasswordField(
   value: String,
   onChange: (String) -> Unit,
   submit: () -> Unit,
   modifier: Modifier = Modifier,
   label: String = "Password",
   placeholder: String = "Enter your Password"
) {

   var isPasswordVisible by remember { mutableStateOf(false) }

   val leadingIcon = @Composable {
      Icon(
         Icons.Default.Key,
         contentDescription = "",
         tint = MaterialTheme.colorScheme.primary
      )
   }
   val trailingIcon = @Composable {
      IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
         Icon(
            if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
         )
      }
   }

   TextField(
      value = value,
      onValueChange = onChange,
      modifier = modifier,
      leadingIcon = leadingIcon,
      trailingIcon = trailingIcon,
      keyboardOptions = KeyboardOptions(
         imeAction = ImeAction.Done,
         keyboardType = KeyboardType.Password
      ),
      keyboardActions = KeyboardActions(
         onDone = { submit() }
      ),
      placeholder = { Text(placeholder) },
      label = { Text(label) },
      singleLine = true,
      visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
   )
}

@Preview
@Composable
fun LoginPreview() {
   RaceAppTheme {
      LoginScreen(uiState = Credentials(login = "test1229@t.com", pwd = "test1234"))
   }
}