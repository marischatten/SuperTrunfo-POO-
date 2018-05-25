package control;

import classe.Carta;
import classe.Jogador;
import classe.Jogo;

public class Main{
	
	public static void main(String[] args) {
		
		Jogo play = new Jogo();
		Carta[] cartas = new Carta[32]; // colocar
		Jogador j1= new Jogador(); //true 
		Jogador j2= new Jogador();//false   o bot
		
		play.geradorCartas(cartas); //constroi cartas
		
		play.distribuirCartas(j1,j2,cartas); //distribuição aleatória>>embaralhar
		
		while(play.numRodada<16){ // criterio de fim do jogo nao definido pelo numero de rodada estaticamente.//as cartas não  vão para baixo do monte do jogador.
			play.rodada(j1,j2);
		}		
		play.contarCarta(j1,j2); //verifica ganhador do jogo
                
		} 
	}
