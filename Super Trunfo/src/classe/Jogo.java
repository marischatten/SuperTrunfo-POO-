package classe;

import java.util.Random;
import java.util.Scanner;

public class Jogo {
	
	int atributo_rodada=0;
	public int numRodada =0;
	boolean vez=true;
	boolean empatou=false;
	Carta[] monteDesempate= new Carta[32]; 
	int qtdEmpates=0;
	
	
	String nomeAtributo[] = {"ALTURA","COMPRIMENTO","PESO","TEMPO DA ESPECIE NA TERRA"};


	public void distribuirCartas(Jogador j1, Jogador j2, Carta[] cartas) {   // o metodo deve distribuir cartas de forma randomica, ja que agora nÃ£o sÃ£o geradas aleatÃ³riamente, se nÃ£o o jogo sempre sera o mesmo
		
		int indice=0;
		
		Random rand = new Random();
		for(int i=0;i<16;i++) {  
			indice=rand.nextInt(32);
			while(cartas[indice]==null) {
				indice=rand.nextInt(32);
			}
			j1.cartasMao[i] = cartas[indice];		
			cartas[indice]=null;
		} 
		for(int i=0;i<16;i++) {  
			indice=rand.nextInt(32);
			while(cartas[indice]==null) {
				indice=rand.nextInt(32);
			}
			j2.cartasMao[i] = cartas[indice];		
			cartas[indice]=null;
				} 
		
		System.out.println("AS CARTAS FORAM DISTRIBUIDAS!");
	}

	public void rodada(Jogador j1,Jogador j2){

		this.controleRodada(j1,j2);
		this.verificaGanhador(j1,j2);
		this.numRodada++;
		j1.pos--;
		j2.pos--;
		System.out.println("----FIM DA RODADA: "+this.numRodada+"----");
		System.out.println("------------------------------------------------------------------------------");	
		System.out.println("------------------------------------------------------------------------------");	
		System.out.println("------------------------------------------------------------------------------");	
		
		
	}
	

	public void controleRodada(Jogador j1,Jogador j2){
		
		Random rand = new Random();

		if(this.vez==true) { // vez J1
				System.out.println("SUA VEZ!");
                                System.out.println("SUA CARTA �:");
				j1.verCarta(j1.cartasMao[j1.pos]);
				this.atributo_rodada = j1.escolherAtributo();
                                System.out.println("CARTA DO BOT!");
				j2.verCarta(j2.cartasMao[j2.pos]);
				this.vez=false;
                                System.out.println("VOC� ESCOLHEU O ATRIBUTO: >>  "+	this.nomeAtributo[this.atributo_rodada]);
				
			}else {  //vez J2
				System.out.println("VEZ DO BOT!");
                                System.out.println("CARTA DO BOT:");
				j2.verCarta(j2.cartasMao[j2.pos+1]);
				this.atributo_rodada = rand.nextInt(3)+1;
                                System.out.println("SUA CARTA");
				j1.verCarta(j1.cartasMao[j1.pos]);
				this.vez=true;
				System.out.println("ATRIBUTO ESCOLHIDO PELO BOT: >>  "+this.nomeAtributo[this.atributo_rodada]);
				
			}
	}
	
	public void verificaGanhador(Jogador j1, Jogador j2){
		
		System.out.println("COMPARANDO CARTAS.");
				
		if(this.comparaCartas(j1.cartasMao[j1.pos],j2.cartasMao[j2.pos])==0) {  
			System.out.println("                                      OPS, EMPATE NESSA RODADA.");
			vez=true;
			empatou=true;
			this.desempatar(j1.cartasMao[j1.pos],j2.cartasMao[j2.pos]);
			this.qtdEmpates++;
		}
		if(this.comparaCartas(j1.cartasMao[j1.pos],j2.cartasMao[j2.pos])==1){
			System.out.println("                                      VOC� GANHOU ESSA RODADA!");
								
			if(empatou){
				this.recolherCartasEmpatadas(j1);
				empatou=false;
			}
			this.recolherCartas(j1, j1.cartasMao[j1.pos],j2.cartasMao[j2.pos]);
			
		}
		if(this.comparaCartas(j1.cartasMao[j1.pos],j2.cartasMao[j2.pos])==2){ // fazer comparaÃ§Ã£o da carta super trunfo
			System.out.println("                                     OPS O BOT GANHOU DESSA VEZ =( ");

			if(empatou){
				this.recolherCartasEmpatadas(j1);
				empatou=false;
			}
			this.recolherCartas(j2, j1.cartasMao[j1.pos],j2.cartasMao[j2.pos]);
		
		}
	}
	
	public int comparaCartas(Carta j1,Carta j2) {
            

		if(j1.atributo[this.atributo_rodada]==j2.atributo[this.atributo_rodada]) {
			
			return 0;
		}
		if (j1.atributo[this.atributo_rodada]>j2.atributo[this.atributo_rodada]) { //Buscar dentro da string?? /* || (j1.superTrunfo==true && j2.grupo[1]=='A')*/
			return 1;
		}
		else {
			return 2;
		}
	}
	
	public void recolherCartasEmpatadas(Jogador ganhador){
		
		for(int i=0; i<qtdEmpates*2; i++) {
		ganhador.monteCartas[ganhador.posMonte]=this.monteDesempate[i];
		
		}
		ganhador.posMonte=ganhador.posMonte+2;
		ganhador.quantidade_Cartas=ganhador.quantidade_Cartas+2;
		System.out.println("RECOLHENDO DO MONTE DE CARTAS EMPATADAS.");
		ganhador.quantidade_Cartas=this.qtdEmpates*2;                        //commit
		this.qtdEmpates=0;
	}
	
	public void recolherCartas(Jogador ganhador, Carta j1, Carta j2) {
		
		ganhador.monteCartas[ganhador.posMonte]= j1;
		ganhador.monteCartas[ganhador.posMonte++]= j2;
		ganhador.posMonte = ganhador.posMonte+2;
		ganhador.quantidade_Cartas=ganhador.quantidade_Cartas+2;
		System.out.println("RECOLHENDO CARTAS.");
	}

	public void desempatar(Carta j1, Carta j2) {

		Carta[] monteDesempate = new Carta[2];
		if(this.qtdEmpates==1) {
		monteDesempate[0] = j1;
		monteDesempate[1] = j2;
		}else {
			monteDesempate[this.qtdEmpates] = j1;
			monteDesempate[this.qtdEmpates+1] = j2;
		}
	}
	
	public void contarCarta(Jogador j1, Jogador j2) {
		
		System.out.println("FIM DA PARTIDA!");
		System.out.println("_______________________________________________________");
		System.out.println("                                                       ");
		if(j1.quantidade_Cartas > j2.quantidade_Cartas) {
			System.out.println(" PARABENS VOC� GANHOU ESSA PARTIDA");
		}else {
			if(j2.quantidade_Cartas > j1.quantidade_Cartas) {
				System.out.println(" QUE PENA O BOT GANHOU, MAS N�O DESISTA! ");
			}else {
				System.out.println("EMPATE NA PARTIDA!!");
			}
		}
		System.out.println("                                                       ");
		System.out.println("_______________________________________________________");
		System.out.println("	Numero de cartas resgatadas:    "+j1.quantidade_Cartas);
		System.out.println("	Numero de cartas resgatadas pelo bot:    "+j2.quantidade_Cartas);
	}	
	
	
	public void geradorCartas(Carta[] cartas){
		
		//Carta[] carta = new Carta[32];
		
		cartas[0] = new Carta();
		
		cartas[0].nomePersonagem = "ESTIRACOSSAURO"; 
		cartas[0].atributo[0] = 2;
		cartas[0].atributo[1] = 5;
		cartas[0].atributo[2] = 3000;
		cartas[0].atributo[3] = 77;
		cartas[0].superTrunfo = false;
		cartas[0].grupo = "3B";

		cartas[1] = new Carta();
		cartas[1].nomePersonagem= "EUOPLOCÃ‰FALO";
		cartas[1].atributo[0] = 1.8;
		cartas[1].atributo[1] = 6;
		cartas[1].atributo[2]= 2000;
		cartas[1].atributo[3] = 76;
		cartas[1].superTrunfo = false;
		cartas[1].grupo = "6A";
		
		cartas[2] = new Carta();
		cartas[2].nomePersonagem = "TOROSSAURO";
		cartas[2]. atributo[0]= 3.6;
		cartas[2]. atributo[1]= 7.5;
		cartas[2]. atributo[2]= 8000;
		cartas[2]. atributo[3]= 70;
		cartas[2].superTrunfo = false;
		cartas[2].grupo = "8D";
		
		cartas[3] = new Carta();
		cartas[3].nomePersonagem = "GALIMIMO";
		cartas[3].atributo[0]= 3;
		cartas[3].atributo[1] = 5.6;
		cartas[3].atributo[2] = 200;
		cartas[3].atributo[3] = 74;
		cartas[3].superTrunfo = false;
		cartas[3].grupo = "5D";
		
		cartas[4] = new Carta();
		cartas[4].nomePersonagem = "TENONTOSSAURO";
		cartas[4].atributo[0] = 2.1;
		cartas[4].atributo[1] = 7.2;
		cartas[4].atributo[2] = 1500;
		cartas[4].atributo[3] = 110;
		cartas[4].superTrunfo = false;
		cartas[4].grupo = "8C";
		
		cartas[5] = new Carta();
		cartas[5].nomePersonagem = "DEINONICO";
		cartas[5].atributo[0] = 1.65;
		cartas[5].atributo[1] = 3;
		cartas[5].atributo[2] = 75;
		cartas[5].atributo[3] = 100;
		cartas[5].superTrunfo = false;
		cartas[5].grupo = "3A";
		
		cartas[6] = new Carta();
		cartas[6].nomePersonagem = "GIGANOTOSSAURO";
		cartas[6].atributo[0] = 6;
		cartas[6].atributo[1] = 16;
		cartas[6].atributo[2] = 8000;
		cartas[6].atributo[3] = 100;
		cartas[6].superTrunfo = false;
		cartas[6].grupo = "5C";
		
		cartas[7] = new Carta();
		cartas[7].nomePersonagem = "CORITOSSAURO";
		cartas[7].atributo[0] = 5;
		cartas[7].atributo[1] = 8;
		cartas[7].atributo[2]  = 5000;
		cartas[7].atributo[3]  = 80;
		cartas[7].superTrunfo = false;
		cartas[7].grupo = "2D";
		
		cartas[8] = new Carta();
		cartas[8].nomePersonagem = "HETERODONTOSSAURO";
		cartas[8].atributo[0]= 0.5;
		cartas[8].atributo[1] = 1.2;
		cartas[8].atributo[2] = 10;
		cartas[8].atributo[3] = 208;
		cartas[8].superTrunfo = false;
		cartas[8].grupo = "5B";
		
		cartas[9] = new Carta();
		cartas[9].nomePersonagem = "DIMORPHODON";
		cartas[9].atributo[0] = 0.25;
		cartas[9].atributo[1] = 0.25;
		cartas[9].atributo[2] = 4;
		cartas[9].atributo[3] = 200;
		cartas[9].superTrunfo = false;
		cartas[9].grupo = "8A";
		
		cartas[10] = new Carta();
		cartas[10].nomePersonagem = "CENTROSSAURO";
		cartas[10].atributo[0] = 2;
		cartas[10].atributo[1] = 6;
		cartas[10].atributo[2] = 1000;
		cartas[10].atributo[3] = 76;
		cartas[10].superTrunfo = false;
		cartas[10].grupo = "2C";
		
		cartas[11] = new Carta();
		cartas[11].nomePersonagem = "HIPSILOFODANTE";
		cartas[11].atributo[0]= 0.8;
		cartas[11].atributo[1] = 2;
		cartas[11].atributo[2] = 50;
		cartas[11].atributo[3] = 125;
		cartas[11].superTrunfo = false;
		cartas[11].grupo = "5A";
		
		cartas[12] = new Carta();
		cartas[12].nomePersonagem = "ESTRUTIOMIMO";
		cartas[12].atributo[0]= 2;
		cartas[12].atributo[1]= 3;
		cartas[12].atributo[2] = 150;
		cartas[12].atributo[3] = 75;
		cartas[12].superTrunfo = false;
		cartas[12].grupo = "7D";
		
		cartas[13] = new Carta();
		cartas[13].nomePersonagem = "CAMARASSAURO";
		cartas[13].atributo[0] = 9;
		cartas[13].atributo[1] = 18;
		cartas[13].atributo[2] = 20000;
		cartas[13].atributo[3] = 150;
		cartas[13].superTrunfo = false;
		cartas[13].grupo = "2B";
		
		cartas[14] = new Carta();
		cartas[14].nomePersonagem = "IGUANODON";
		cartas[14].atributo[0]= 5;
		cartas[14].atributo[1]= 10;
		cartas[14].atributo[2]= 4500;
		cartas[14].atributo[3]= 135;
		cartas[14].superTrunfo = false;
		cartas[14].grupo = "4D";
		
		cartas[15] = new Carta();
		cartas[15].nomePersonagem = "TROODONTE";
		cartas[15].atributo[0]= 1.8;
		cartas[15].atributo[1]= 2;
		cartas[15].atributo[2]= 60;
		cartas[15].atributo[3]= 76;
		cartas[15].superTrunfo = false;
		cartas[15].grupo = "7C";
		
		cartas[16] = new Carta();
		cartas[16].nomePersonagem = "BAROSSAURO";
		cartas[16].atributo[0] = 26;
		cartas[16].atributo[1] = 26;
		cartas[16].atributo[2] = 40000;
		cartas[16].atributo[3] = 156;
		cartas[16].superTrunfo = false;
		cartas[16].grupo = "2A";
		
		cartas[17] = new Carta();
		cartas[17].nomePersonagem = "MAIASSAURO";
		cartas[17].atributo[0]= 2.5;
		cartas[17].atributo[1] = 9;
		cartas[17].atributo[2] = 3000;
		cartas[17].atributo[3] = 80;
		cartas[17].superTrunfo = false;
		cartas[17].grupo = "4C";
		
		cartas[18] = new Carta();
		cartas[18].nomePersonagem = "SAUROLOFO";
		cartas[18].atributo[0]  = 4.2;
		cartas[18].atributo[1]  = 10.5;
		cartas[18].atributo[2]  = 4000;
		cartas[18].atributo[3]  = 70;
		cartas[18].superTrunfo = false;
		cartas[18].grupo = "7B";
		
		cartas[19] = new Carta();
		cartas[19].nomePersonagem = "ARCHAEOPTERYX";
		cartas[19].atributo[0] = 0.3;
		cartas[19].atributo[1] = 0.3;
		cartas[19].atributo[2] = 6;
		cartas[19].atributo[3] = 150;
		cartas[19].superTrunfo = false;
		cartas[19].grupo = "1D";
		
		cartas[20] = new Carta();
		cartas[20].nomePersonagem = "MAMENQUISSAURO";
		cartas[20].atributo[0] = 9;
		cartas[20].atributo[1] = 24.6;
		cartas[20].atributo[2] = 25000;
		cartas[20].atributo[3] = 150;
		cartas[20].superTrunfo = false;
		cartas[20].grupo = "4B";
		
		cartas[21] = new Carta();
		cartas[21].nomePersonagem = "PROTOCERÃ�TOPO";
		cartas[21].atributo[0] = 1.5;
		cartas[21].atributo[1] = 2;
		cartas[21].atributo[2] = 400;
		cartas[21].atributo[3] = 72;
		cartas[21].superTrunfo = false;
		cartas[21].grupo = "7A";
		
		cartas[22] = new Carta();
		cartas[22].nomePersonagem = "MEGALOSSAURO";
		cartas[22].atributo[0] = 3;
		cartas[22].atributo[1] = 6.9;
		cartas[22].atributo[2] = 1000;
		cartas[22].atributo[3] = 175;
		cartas[22].superTrunfo = false;
		cartas[22].grupo = "4A";
		
		cartas[23] = new Carta();
		cartas[23].nomePersonagem= "POLACANTO";
		cartas[23].atributo[0]  = 1.5;
		cartas[23].atributo[1]  = 3.9;
		cartas[23].atributo[2]  = 1300;
		cartas[23].atributo[3]  = 120;
		cartas[23].superTrunfo = false;
		cartas[23].grupo = "6D";
		
		cartas[24] = new Carta();
		cartas[24].nomePersonagem = "ANQUILOSSAURO";
		cartas[24].atributo[0] = 3;
		cartas[24].atributo[1] = 9.9;
		cartas[24].atributo[2] = 6000;
		cartas[24].atributo[3] = 70;
		cartas[24].superTrunfo = false;
		cartas[24].grupo = "1B";
		
		cartas[25] = new Carta();
		cartas[25].nomePersonagem = "ORNITOMIMO";
		cartas[25].atributo[0]= 2.1;
		cartas[25].atributo[1] = 3.9;
		cartas[25].atributo[2] = 90;
		cartas[25].atributo[3] = 75;
		cartas[25].superTrunfo = false;
		cartas[25].grupo = "3D";
		
		cartas[26] = new Carta();
		cartas[26].nomePersonagem = "PENTACERÃ�TOPO";
		cartas[26].atributo[0] = 3;
		cartas[26].atributo[1] = 8;
		cartas[26].atributo[2] = 8000;
		cartas[26].atributo[3] = 75;
		cartas[26].superTrunfo = false;
		cartas[26].grupo = "6C";
		
		cartas[27] = new Carta();
		cartas[27].nomePersonagem = "ANQUISSAURO";
		cartas[27].atributo[0] = 0.6;
		cartas[27].atributo[1] = 2.4;
		cartas[27].atributo[2] = 27;
		cartas[27].atributo[3] = 200;
		cartas[27].superTrunfo = false;
		cartas[27].grupo = "1A";
		
		cartas[28] = new Carta();
		cartas[28].nomePersonagem = "PAQUICEFALOSSAURO";
		cartas[28].atributo[0] = 6;
		cartas[28].atributo[1] = 8;
		cartas[28].atributo[2]= 3000;
		cartas[28].atributo[3] = 70;
		cartas[28].superTrunfo = false;
		cartas[28].grupo = "3C";
		
		cartas[29] = new Carta();
		cartas[29].nomePersonagem = "PAQUIRINOSSAURO";
		cartas[29].atributo[0]= 3.5;
		cartas[29].atributo[1]= 7;
		cartas[29].atributo[2]= 3000;
		cartas[29].atributo[3] = 70;
		cartas[29].superTrunfo = false;
		cartas[29].grupo = "6B";
		
		cartas[30] = new Carta();
		cartas[30].nomePersonagem = "ARGENTINOSAURO";
		cartas[30].atributo[0]= 22;
		cartas[30].atributo[1]= 35;
		cartas[30].atributo[2]= 100000;
		cartas[30].atributo[3] = 99;
		cartas[30].superTrunfo = false;
		cartas[30].grupo = "6B";
		
		cartas[31] = new Carta();
		cartas[31].nomePersonagem = "TYRANNOSAURUS REX";
		cartas[31].atributo[0]= 6.1;
		cartas[31].atributo[1]= 12;
		cartas[31].atributo[2]= 9000;
		cartas[31].atributo[3] = 250;
		cartas[31].superTrunfo = true;
		cartas[31].grupo = "6B";
		
	}
}
