package br.com.si.ufrrj

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import br.com.si.ufrrj.logica.UserStatus
import br.com.si.ufrrj.logica.ApiConect
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.Semaphore
import kotlin.collections.ArrayList

class SplashScreen : AppCompatActivity() {
    var cartasBaixadas: Stack<String> = Stack()
    var textoFeedback: TextView? = null
    private val semaforo = Semaphore(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //1 -verificar se tem internet
        //2- verificar se estatus do usuario foi definido
        //3- buscar informacoes dos ID na API backend
        //4- fazer parse dos JsonObtidos de cada ID
        textoFeedback = findViewById(R.id.txt_loader)

        if(temInternet()){
            //Iniciando status do usuario
            var usuario = UserStatus
            var refDeckAtual = usuario.deckAtualId
            val sucesso = buscaNaApi(refDeckAtual)
            if(!sucesso) textoFeedback?.text = "Ops! parece que não temos internet"

        }else{
            textoFeedback?.text = "Ops! parece que não temos internet"
        }

    }

    /**
     * Verifica se tem internet
     */
    fun temInternet(): Boolean {
        textoFeedback?.text = "Verificando Conexao"
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isWiFi: Boolean = activeNetwork?.type == ConnectivityManager.TYPE_WIFI
        if(!isWiFi){ Toast.makeText(this,"Recomendamos jogar com Wifi",Toast.LENGTH_SHORT).show() }
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun buscaNaApi(refDeckAtual:ArrayList<Int>):Boolean{
        var sucesso:Boolean = true
        //buscando as ref do deck atual na API
        val apiConect = ApiConect(this)
        textoFeedback?.text = "Baixando Cartas"
        refDeckAtual.forEach { idCard ->
            apiConect.buscarId(idCard){
                if(it == "-1") sucesso = false
                empilhaJsonCard(it)
            }
            if(!sucesso) return false //esse return vai para quem chamou a funcao buscaNaApi
        }
        return sucesso
    }

    /**
     * Armazena o json recebido
     * Deve ser sincronizado por usar multiplos threads dinamicamente enquanto baixa os arquivos
     * Usando semaforos para isso
     */
    fun empilhaJsonCard(jsonString: String?){

        try {
            semaforo.acquire()
            jsonString?.let { it -> cartasBaixadas.push(it) }
            if(cartasBaixadas.size == 10){ initParseStringsJson() }//quando chegamos na decima carta inicializa parse
        } finally {
            semaforo.release()
        }
    }

    /**
     * Busca o json referente a cada Id armazenado
     */
    fun initParseStringsJson(){
        textoFeedback?.text = "Extraindo dados"

        parseBackThread()

        //verificando se o download concluiu a cada 0.8 segundo
        GlobalScope.async {
            while (true){
                delay(800)
                if(cartasBaixadas.empty()){//como é uma pilha assim que estiver vazia é pq o job acabou
                    iniciaGame()
                    break
                }
            }
        }
    }


    fun parseBackThread(){
        // Start a coroutine
        GlobalScope.async {
            var usr = UserStatus
            while (cartasBaixadas.isNotEmpty()){
                var strcard = cartasBaixadas.pop()
                var singleCard = ApiConect.parseResponse(strcard)
                if(usr.deckAtual.size < 5) usr.deckAtual.add(singleCard) //colocando somente 5 cartas no deck atual

                usr.cartasDisponiveis.add(singleCard)
            }
            textoFeedback?.text = "Iniciando game"
        }
    }

    fun iniciaGame(){
        val intent = Intent(baseContext,MenuPrincipal::class.java)
        startActivity(intent)
        finish()
    }
}
