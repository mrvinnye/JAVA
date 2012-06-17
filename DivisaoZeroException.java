public class DivisaoZeroException extends Exception{
	/*
	 * 
	 */
	private static final long serialVersionUID=1L;
	private SuperInt denominador;
	
	public DivisaoZeroException(SuperInt denominador){
		this.denominador=denominador;
		System.out.println("Nao e possivel efetuar divisao por zero \n");
		
	}
	public SuperInt getOperando(){
		return denominador;
	}
}