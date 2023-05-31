package c23.pc609.loginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var textViewRegister: TextView
    lateinit var btnLogin: Button

    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java));
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
        editEmail = findViewById(R.id.emailEt)
        editPassword = findViewById(R.id.passET)
        btnLogin = findViewById(R.id.button)
        textViewRegister =findViewById(R.id.textView_register)

        textViewRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        btnLogin.setOnClickListener{
            if (editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                processLogin()
            }else{
                Toast.makeText(this, "Silahkan isi email dan passsword", LENGTH_SHORT).show()
            }
        }
    }
    private fun processLogin(){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()
            }
    }
}