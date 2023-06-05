package com.example.appfirestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance() // Criando a variável do banco de dados que puxa as intâncias do cloud firestore
        setContent {
            MaterialTheme {
                Formulario() // Chamando a função do formulário, onde estão todos os inputs
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class) // Anotação para utilizar comandos e recursos jetpack compose material 3
    @Preview // Comando para fornecer uma visualização do código
    @Composable // Função que cria e retorna uma interface do usuário com base nos dados fornecidos
    fun Formulario() { // Criando função para o formulário


        var nome = remember { mutableStateOf("")} // Criando a váriavel nome
        var endereco = remember {mutableStateOf("")} // Criando a váriavel endereço
        var bairro = remember {mutableStateOf("")} // Criando a váriavel bairro
        var cep = remember {mutableStateOf("")} // Criando a váriavel cep
        var cidade = remember {mutableStateOf("")} // Criando a váriavel cidade
        var estado = remember {mutableStateOf("")} // Criando a váriavel estado

        // Função remember: função do jetpack compose que permite lembrar e manter o estado de uma variável ao longo do tempo.

        Column( // Cria uma coluna

            modifier = Modifier // Modificador aplicado à coluna para definir suas propriedades de layout
                .fillMaxSize() // Faz com que a coluna ocupe a tela
                .padding(16.dp), // Adiciona um espaçamento interno de 16 densidades de pixels

            verticalArrangement = Arrangement.Center, // Define alinhamento vertical dos itens como centro
            horizontalAlignment = Alignment.CenterHorizontally // Define alinhamento horizontal dos itens como centro

        )

        // Criando os inputs

        {

            // TÍTULO DO FORMULÁRIO
            Text(
                text = "CADASTRANDO DADOS", // Definindo um título
            )

            Spacer(modifier = Modifier.height(20.dp)) // Espaçamento entre os inputs

            // INPUT NOME
            TextField(
                value = nome.value, // Define o valor do Input para a variável, passando .value como valor atual do campo
                onValueChange = { nome.value = it }, // Função callback que retorna quando o valor do campo é alterado
                label = { Text("Nome") }, // Definindo uma máscara para o input
                modifier = Modifier.fillMaxWidth() // Define a ocupação do input para o layout inteiro disponível
            )

            Spacer(modifier = Modifier.height(20.dp)) // Espaçamento entre os inputs

            // INPUT ENDEREÇO
            TextField(
                value = endereco.value, // Define o valor do Input para a variável, passando .value como valor atual do campo
                onValueChange = { endereco.value = it }, // Função callback que retorna quando o valor do campo é alterado
                label = { Text("Endereço") }, // Definindo uma máscara para o input
                modifier = Modifier.fillMaxWidth() // Define a ocupação do input para o layout inteiro disponível
            )

            Spacer(modifier = Modifier.height(20.dp)) // Espaçamento entre os inputs

            // INPUT BAIRRO
            TextField(
                value = bairro.value, // Define o valor do Input para a variável, passando .value como valor atual do campo
                onValueChange = { bairro.value = it }, // Função callback que retorna quando o valor do campo é alterado
                label = { Text("Bairro") }, // Definindo uma máscara para o input
                modifier = Modifier.fillMaxWidth() // Define a ocupação do input para o layout inteiro disponível
            )

            Spacer(modifier = Modifier.height(20.dp)) // Espaçamento entre os inputs

            // INPUT CEP
            TextField(
                value = cep.value, // Define o valor do Input para a variável, passando .value como valor atual do campo
                onValueChange = { cep.value = it }, // Função callback que retorna quando o valor do campo é alterado
                label = { Text("CEP") }, // Definindo uma máscara para o input
                modifier = Modifier.fillMaxWidth() // Define a ocupação do input para o layout inteiro disponível
            )

            Spacer(modifier = Modifier.height(20.dp)) // Espaçamento entre os inputs

            // INPUT CIDADE
            TextField(
                value = cidade.value, // Define o valor do Input para a variável, passando .value como valor atual do campo
                onValueChange = { cidade.value = it }, // Função callback que retorna quando o valor do campo é alterado
                label = { Text("Cidade") }, // Definindo uma máscara para o input
                modifier = Modifier.fillMaxWidth() // Define a ocupação do input para o layout inteiro disponível
            )

            Spacer(modifier = Modifier.height(20.dp)) // Espaçamento entre os inputs

            // INPUT ESTADO
            TextField(
                value = estado.value, // Define o valor do Input para a variável, passando .value como valor atual do campo
                onValueChange = { estado.value = it }, // Função callback que retorna quando o valor do campo é alterado
                label = { Text("Estado") }, // Definindo uma máscara para o input
                modifier = Modifier.fillMaxWidth() // Define a ocupação do input para o layout inteiro disponível
            )

            Spacer(modifier = Modifier.height(20.dp)) // Espaçamento entre os inputs

            // CRIANDO BOTÃO CADASTRAR
            Button(
                onClick = { // Função que refere ao clicar no botão
                    val data = hashMapOf( // Variável que armazenará todas os valores dos inputs

                        // Armazenando os valores de cada input
                        "nome" to nome.value,
                        "endereco" to endereco.value,
                        "bairro" to bairro.value,
                        "cep" to cep.value,
                        "cidade" to cidade.value,
                        "estado" to estado.value
                    )

                    firestore.collection("formulario") // Criando uma coleção dentro do cloud firestore que armazenará todos os dados
                        .add(data) // Enviando os dados armazenados na váriavel data
                        .addOnSuccessListener { // Se caso der certo ao enviar os dados

                            // Aqui foi criado automáticamente a cada cadastro um item dentro da coleção formulário com ids diferentes
                            nome.value = ""
                            endereco.value = ""
                            bairro.value = ""
                            cep.value = ""
                            cidade.value = ""
                            estado.value = ""
                            // Após enviar todos os dados para o banco de dados, os inputs ficam vazios para novos cadastros
                        }
                        .addOnFailureListener { e -> // Se caso não der certo ao enviar dados
                        }
                },
                modifier = Modifier.padding(bottom = 150.dp) // Define um espaçamento na parte inferior do layout
            ) {
                Text("Cadastrar Dados") // Texto do botão cadastrar
            }}}}


