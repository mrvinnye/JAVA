
public class CaracterIndevido extends Exception{
	/*
	 * 
	 */
	
	private static final long serialVersionUID=1L;
	private String excecao;
	public CaracterIndevido (String excecao){
		
		System.out.println("Car�cter inv�lido digitado \n "+excecao+"\n");
	}
	public String getExcecao(){
		return excecao;
	}
}
