package classe;

import java.util.Scanner;




public class Jogador {
	
	int quantidade_Cartas=0;
	Carta[] cartasMao = new Carta[17];
	Carta[] monteCartas= new Carta[35]; 
	int posMonte=0;
	int pos=15;
	
	public void verCarta(Carta carta) {
		
                System.out.println("-------------------");
 		System.out.println(carta.nomePersonagem);
		for(int i=0;i<4;i++) { 
			System.out.println(carta.atributo[i]);
		}
                System.out.println("-------------------");
                
	}
	
	public int escolherAtributo() {
		
		Scanner input = new Scanner(System.in);  
		System.out.println("ESCOLHA UM ATRIBUTO");
        System.out.println("[ 1 ] ALTURA");
        System.out.println("[ 2 ] COMPRIMENTO");
        System.out.println("[ 3 ] PESO");
        System.out.println("[ 4 ] TEMPO DA ESPECIE NA TERRA");
        System.out.println("-------------------");
        System.out.println(">>");
		int atributo = input.nextInt();
        while(atributo>4 || atributo<1){
		//while(atributo<4 && atributo>1);{
        	System.out.println("Atributo inválido, escolha novamente:");
        	atributo = input.nextInt();
        }
		return atributo -1;
	}
	

	
}