package com.projetosrafaelfemina.cinemaoflix.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ThemedSpinnerAdapter.Helper
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.projetosrafaelfemina.cinemaoflix.R
import com.projetosrafaelfemina.cinemaoflix.databinding.ActivityFormCadastroBinding

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.editEmail.requestFocus()



        binding.btVamosLa.setOnClickListener {
            val email = binding.editEmail.text.toString()

            if (!email.isEmpty()) {
                binding.containerSenha.visibility = View.VISIBLE
                binding.btVamosLa.visibility = View.GONE
                binding.btContinuar.visibility = View.VISIBLE
                binding.containerEmail.helperText = ""
                binding.containerEmail.boxStrokeColor = Color.parseColor("#059700")
                binding.ContainerHeader.visibility = View.VISIBLE
                binding.txtTitulo.setText("Um Mundo de séries e filmes \n ilimitados espera por você.")
                binding.txtDescricao.setText("Crie uma conta para saber mais sobre \n o nosso CinemaoFlix")
            } else {
                binding.containerEmail.helperText = "O email é obrigatório."
                binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
            }
        }

        binding.btContinuar.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (!email.isEmpty() && !senha.isEmpty()) {
                Cadastro(email, senha)
//                Toast.makeText(this,"Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
            } else if (senha.isEmpty()) {
                binding.containerSenha.boxStrokeColor = Color.parseColor("#FF0000")
                binding.containerSenha.helperText = "A senha é obrigatória"
                binding.containerEmail.boxStrokeColor = Color.parseColor("#059700")
            } else if (email.isEmpty()) {
                binding.containerEmail.helperText = "O email é obrigatório."
                binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
            }
        }

        binding.btEntrar.setOnClickListener {
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
        }

    }

    private fun Cadastro(email: String, senha: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { cadastro ->
                if (cadastro.isSuccessful) {
                    Toast.makeText(this, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    binding.containerEmail.helperText = ""
                    binding.containerSenha.helperText = ""
                    binding.containerEmail.boxStrokeColor = Color.parseColor("#FF018786")
                    binding.containerSenha.boxStrokeColor = Color.parseColor("#FF018786")
                    val intent = Intent(this,FormLogin::class.java)
                    startActivity(intent)
                }
            }.addOnFailureListener {
            val erro = it

                when {
                    erro is FirebaseAuthWeakPasswordException -> {
                        binding.containerSenha.helperText = "Digite uma senha com no mínimo 6 caracteres"
                        binding.containerSenha.boxStrokeColor = Color.parseColor("#FF0000")
                    }
                    erro is FirebaseAuthUserCollisionException -> {
                        binding.containerEmail.helperText = "Esta conta já foi cadastrada"
                        binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                    }
                    erro is FirebaseNetworkException -> {
                        binding.containerEmail.helperText = "Sem conexão com a internet!"
                        binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                    }
                    else -> {
                        Toast.makeText(this,"Erro ao cadastrar o usuário",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

}