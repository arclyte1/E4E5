package com.example.e4e5.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.e4e5.R
import com.example.e4e5.presentation.lobbydetails.LobbyDetailsScreen
import com.example.e4e5.presentation.lobbylist.LobbyListScreen
import com.example.e4e5.presentation.signin.SignInScreen
import com.example.e4e5.presentation.ui.theme.E4E5Theme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.app
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "success google auth")
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                e.printStackTrace()
                Log.d(TAG, "failure google auth ${e.message}")
            }
        }
    }

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        setContent {
            E4E5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SignInScreen.route
                    ) {
                        composable(
                            route = Screen.SignInScreen.route
                        ) {
                            SignInScreen(
                                googleSignInListener = ::googleSignIn
                            )
                        }
                        composable(
                            route = Screen.LobbyListScreen.route
                        ) {
                            LobbyListScreen(
                                navController = navController,
                                signOut = ::googleSignOut
                            )
                        }
                        composable(
                            route = Screen.LobbyDetailsScreen.route + "/{lobbyId}",
                            arguments = listOf(navArgument("lobbyId") {type = NavType.StringType})
                        ) {
                            LobbyDetailsScreen()
                        }
                    }
                }
            }
        }
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun googleSignOut() {
        auth.signOut()
        googleSignInClient.signOut()
        navController.navigate(Screen.SignInScreen.route)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                Log.d(TAG, "success firebase auth")
                navController.navigate(Screen.LobbyListScreen.route)
            }
            .addOnFailureListener {
                Log.d(TAG, "failure firebase auth")
            }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}