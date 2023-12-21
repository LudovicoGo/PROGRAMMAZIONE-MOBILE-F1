package com.programmazionemobile.formula1app

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.programmazionemobile.formula1app.fragment.LoginFragment
import com.programmazionemobile.formula1app.fragment.LoginFragmentDirections
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

// Aggiorna l'interfaccia per GoogleSignInClientWrapper
interface GoogleSignInClientWrapper {
    fun silentSignIn(): Task<GoogleSignInAccount>
}

// Aggiorna l'interfaccia per GoogleSignInAccountWrapper
interface GoogleSignInAccountWrapper {
    val idToken: String?
}


// Modifica la classe di test
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var navController: NavController

    @Mock
    private lateinit var googleSignInClientWrapper: GoogleSignInClientWrapper

    @Mock
    private lateinit var googleSignInAccountWrapper: GoogleSignInAccountWrapper

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private fun getGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("web_client_id")
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    @Before
    fun setUp() {
        // Mock GoogleSignInAccount
        `when`(googleSignInAccountWrapper.idToken).thenReturn("mockedIdToken")

        // Mock GoogleSignInClient
        `when`(googleSignInClientWrapper.silentSignIn()).thenReturn(Tasks.forResult(mock(GoogleSignInAccount::class.java)))

        // Set up FirebaseAuth
        val auth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(googleSignInAccountWrapper.idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                } else {
                    // Handle authentication failure
                }
            }
    }


    @Test
    fun testClickLoginButton_NavigateToAccountFragment() {
        val scenario = launchFragmentInContainer<LoginFragment>()

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.loginButton)).perform(click())

        // Verifica che la navigazione avvenga correttamente
        verify(navController).navigate(
            LoginFragmentDirections.actionAccountFragmentToAccountFragment4()
        )
    }
}
