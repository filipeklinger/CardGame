package br.com.si.ufrrj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.com.si.ufrrj.logica.UserStatus
import br.com.si.ufrrj.logica.apiConect
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.Semaphore

class SplashScreen : AppCompatActivity() {
    var cartasBaixadas: Stack<String> = Stack()
    var textoFeedback: TextView? = null
    private val sharedCounterLock = Semaphore(1)

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
            //buscando as 10 ref do deck atual
            var apiConect = apiConect(this)
            textoFeedback?.text = "Baixando Cartas"
            refDeckAtual.forEach { idCard ->
                apiConect.buscarId(idCard){
                armazenaJsonCards(it)
            } }
        }

    }

    /**
     * Verifica se tem internet
     */
    fun temInternet(): Boolean {
        textoFeedback?.text = "Verificando Conexao"
        return true
    }

    /**
     * Armazena o json recebido
     * Deve ser sincronizado por usar multiplos threads dinamicamente enquanto baixa os arquivos
     * Usando semaforos para isso
     */
    fun armazenaJsonCards(jsonString: String?){
        try {
            sharedCounterLock.acquire()
            jsonString?.let { it -> cartasBaixadas.push(it) }
            if(cartasBaixadas.size == 10){ initParseStringsJson() }//quando chegamos na decima carta inicializa parse
        } finally {
            sharedCounterLock.release()
        }
    }

    /**
     * Busca o json referente a cada Id armazenado
     */
    fun initParseStringsJson(){
        textoFeedback?.text = "Extraindo dados"
        parseBackThread()

        //verificando se o download concluiu a cada 1 segundo
        //as vezes o inicia game nao funciona dentro de parse =(
        GlobalScope.async {
            while (true){
                delay(1000)
                if(cartasBaixadas.empty()){
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
                println(strcard)
                var singleCard = apiConect.parseResponse(strcard)
                usr.deckAtual.add(singleCard)
            }
            println("Parse finalizado")
            textoFeedback?.text = "Iniciando game"
        }
    }

    fun iniciaGame(){
        val intent = Intent(baseContext,MenuPrincipal::class.java)
        startActivity(intent)
        finish()
    }
}
