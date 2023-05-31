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
import com.google.firebase.auth.ktx.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    lateinit var editFullName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var editConfPassword: EditText
    lateinit var btnRegister: Button
    lateinit var textViewLogin: TextView


    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser!=null){
            startActivity(Intent(this, LoginActivity::class.java));
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)
        editFullName = findViewById(R.id.nameEt)
        editEmail = findViewById(R.id.emailEt)
        editPassword = findViewById(R.id.passET)
        editConfPassword = findViewById(R.id.confirm_passET)
        btnRegister = findViewById(R.id.button)
        textViewLogin = findViewById(R.id.textView_login)

        textViewLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        btnRegister.setOnClickListener{
            if (editFullName.text.isNotEmpty() && editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                if (editPassword.text.toString() == editConfPassword.text.toString()){
                    //LAUNCH REGIISTER
                    processRegister()
                }else{
                    Toast.makeText(this, "Password tidak sama, ulangi lagi", LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Silahkan isi dulu semua data", LENGTH_SHORT).show()
            }
        }
    }

    private fun processRegister(){
        val fullName = editFullName.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = fullName
                    }
                    val user = task.result.user
                    user!!.updateProfile(userUpdateProfile)
                        .addOnCompleteListener {
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                        .addOnFailureListener{error2 ->
                            Toast.makeText(this, error2.localizedMessage, LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener{ error ->
                Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()
            }
    }
}