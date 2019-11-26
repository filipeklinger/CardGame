package br.com.si.ufrrj.logica

class userStatus() {
    //Todo Aqui devemos salvar e recuperar o status do usuario no Game
    //Todo Acesso a Banco de dados
    //Status que devemos ter
    //Esses Vetores vao guardar o id das cartas armazenadas na API!!!
    private var cartasDisponiveis:ArrayList<Int>
    private var deckAtual:ArrayList<Int>

    init {
        cartasDisponiveis = ArrayList()
        deckAtual = ArrayList()
    }
}