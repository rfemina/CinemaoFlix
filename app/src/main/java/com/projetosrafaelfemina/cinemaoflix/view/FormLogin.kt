package com.projetosrafaelfemina.cinemaoflix.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.projetosrafaelfemina.cinemaoflix.R
import com.projetosrafaelfemina.cinemaoflix.databinding.ActivityFormLoginBinding

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.editEmail.requestFocus()

        binding.btEntrar.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            when {
                email.isEmpty() -> {
                    binding.containerEmail.helperText = "Preencha o Email!"
                    binding.containerEmail.boxStrokeColor = Color.parseColor("#FF9800")
                }

                senha.isEmpty() -> {
                    binding.containerSenha.helperText = "Preencha a Senha!"
                    binding.containerSenha.boxStrokeColor = Color.parseColor("#FF9800")
                }

                else -> {
                    Autenticacao(email, senha)
                }
            }
        }

        binding.txtTelaCadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

    }

    private fun Autenticacao(email: String, senha: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { autenticacao ->
                if (autenticacao.isSuccessful) {
                    Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show()
                    NavegarTelaPrincipal()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao efetuar o login do usuário!", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun NavegarTelaPrincipal() {
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null) {
            NavegarTelaPrincipal()
        } else {

        }
    }

}