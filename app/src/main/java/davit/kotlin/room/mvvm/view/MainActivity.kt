package davit.kotlin.room.mvvm.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import davit.kotlin.room.mvvm.R
import davit.kotlin.room.mvvm.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel

    lateinit var context: Context

    lateinit var strUsername: String
    lateinit var strPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this@MainActivity

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        btnAddLogin.setOnClickListener {

            strUsername = username_input.text.toString().trim()
            strPassword = password_input.text.toString().trim()

            if (strUsername.isEmpty()) {
                txtUsername.error = getString(R.string.username_error_text)
            } else txtUsername.error = null


            if (strPassword.isEmpty()) {
                txtPassword.error = getString(R.string.password_error_text)
            } else txtPassword.error = null

            if (strUsername.isNotEmpty() && strPassword.isNotEmpty()) {
                loginViewModel.insertData(context, strUsername, strPassword)
                lblInsertResponse.text = getString(R.string.Inserted_Successfully)
            }
        }

        btnReadLogin.setOnClickListener {

            strUsername = username_input.text.toString().trim()

            loginViewModel.getLoginDetails(context, strUsername)!!.observe(this, Observer {

                if (it == null) {
                    lblReadResponse.text = getString(R.string.data_not_found)
                    lblUseraname.text = getString(R.string.lines)
                    lblPassword.text = getString(R.string.lines)
                } else {
                    lblUseraname.text = it.Username
                    lblPassword.text = it.Password

                    lblReadResponse.text = getString(R.string.data_found_successfully)
                }
            })
        }
    }
}