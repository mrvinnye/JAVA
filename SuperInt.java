import java.util.Scanner;

public class SuperInt
{
    public static Scanner scan = new Scanner(System.in);
    private int x[];
    private int numDigitos;
    private boolean negativo;

    public SuperInt()
    {
        numDigitos = 1;
        x = new int[100];
        negativo = false;
    }

    private SuperInt (int v[], int tam, boolean negat)
    {
        x = v;
        numDigitos = tam;
        negativo = negat;
    }

    public SuperInt (String t)
    {
        x = new int [100];
        numDigitos = t.length();

        for ( int i = 0; i < numDigitos; ++i )
        {
            x[numDigitos-i-1] = ((int)t.charAt(i)) - 48;


        }
        if ( ( x[numDigitos-1] < 0 ) || ( x[numDigitos-1] > 9 ) )
        {
            if ( x[numDigitos-1] == -3 ) negativo = true;
            --numDigitos;
        }

    }

    public SuperInt soma (SuperInt operando)
    {
        if ( ( !this.negativo ) && ( operando.negativo ) )
        {
            operando.negativo = false;
            SuperInt res = this.subtrai(operando);
            operando.negativo = true;
            return res;
        }
        if ( ( this.negativo ) && ( !operando.negativo ) )
        {
            this.negativo = false;
            SuperInt res = operando.subtrai(this);
            this.negativo = true;
            return res;
        }
        if ( ( this.negativo ) && ( operando.negativo ) )
        {
            this.negativo = false;
            operando.negativo = false;
            SuperInt res = this.soma(operando);
            res.negativo = true;
            this.negativo = true;
            operando.negativo = true;
            return res;
        }

        int aux, vaiUm = 0;
        int maiorTam = ( numDigitos > operando.numDigitos ) ? numDigitos : operando.numDigitos;
        int menorTam = ( numDigitos < operando.numDigitos ) ? numDigitos : operando.numDigitos;
        int res[] = new int[100];
        for ( int i = 0; i < maiorTam; ++i )
        {
            if ( i < menorTam ) aux = x[i] + operando.x[i] + vaiUm;
            else if ( maiorTam == numDigitos ) aux = x[i] + vaiUm;
            else aux = operando.x[i] + vaiUm;
            res[i] = aux % 10;
            vaiUm = aux / 10;
        }

        res[maiorTam] = vaiUm;
        return new SuperInt(res, maiorTam+vaiUm, false);
    }

    public SuperInt subtrai (SuperInt operando)
    {
        if ( ( !this.negativo ) && ( operando.negativo ) )
        {
            operando.negativo = false;
            SuperInt res = this.soma(operando);
            operando.negativo = true;
            return res;
        }
        if ( ( this.negativo ) && ( !operando.negativo ) )
        {
            this.negativo = false;
            SuperInt res = this.soma(operando);
            this.negativo = true;
            res.negativo = true;
            return res;
        }
        if ( ( this.negativo ) && ( operando.negativo ) )
        {
            this.negativo = false;
            operando.negativo = false;
            SuperInt res = operando.subtrai(this);
            this.negativo = true;
            operando.negativo = true;
            return res;
        }

        //declaro as variaveis maior e menor para armazenarem referencias ao maior e o menor SuperInt, respectivamente
        //assumo a principio que o objeto que invocou o metodo eh o maior entre os dois, depois verifica a validade da afirmacao
        int aux = 0, vemUm = 0;
        boolean negat = false;
        SuperInt maior = this;
        SuperInt menor = operando;

        if ( numDigitos < operando.numDigitos ) //caso o maior seja o operando que foi passado como parametro
        {
            menor = this;
            maior = operando;
            negat = true;
        }
        else if ( numDigitos == operando.numDigitos )
        {
            int i = numDigitos-1;
            while ( i >= 0 )
            {
                if ( x[i] < operando.x[i] ) //caso o maior seja o operando que foi passado como parametro
                {
                    menor = this;
                    maior = operando;
                    negat = true;
                    break;
                }
                else if ( x[i] > operando.x[i] ) break;
                else --i;
            }
        }

        int res[] = new int[100];
        for ( int i = 0; i < maior.numDigitos; ++i )
        {
            if ( i < menor.numDigitos ) aux = maior.x[i] - menor.x[i] - vemUm;
            else aux = maior.x[i] - vemUm;

            if ( aux < 0 )
            {
                aux += 10;
                vemUm = 1;
            }
            else vemUm = 0;
            res[i] = aux;
        }

        if ( aux == 0 ) vemUm = 1;
        return new SuperInt(res, maior.numDigitos-vemUm, negat);

    }

    private SuperInt multiplicaUmDigito (int digito)
    {
        int aux, vaiUns = 0;
        int res[] = new int [100];

        for ( int i = 0; i < numDigitos; ++i )
        {
            aux = ( x[i] * digito ) + vaiUns;
            res[i] = aux % 10;
            vaiUns = aux / 10;
        }
        res[numDigitos] = vaiUns;
        int numDigitosRes = ( vaiUns > 0 ) ? numDigitos+1 : numDigitos;
        return new SuperInt(res, numDigitosRes, false);
    }

    private void deslocaDigitosEsquerda( int quant )
    {
        for ( int i = numDigitos+quant; i >= quant; --i ) x[i] = x[i-quant];
        for ( int i = 0; i < quant; ++i ) x[i] = 0;
        numDigitos += quant;
    }

    public SuperInt multiplica (SuperInt operando)
    {
        SuperInt aux;
        SuperInt res = operando.multiplicaUmDigito(x[0]);
        for ( int i = 1; i < numDigitos; ++i )
        {
            aux = operando.multiplicaUmDigito(x[i]);
            aux.deslocaDigitosEsquerda(i);
            res = res.soma(aux);
        }
        if ( ( ( this.negativo ) && ( !operando.negativo ) ) || ( ( !this.negativo ) && ( operando.negativo ) ) )
        {
            res.negativo = true;
        }
        return res;
    }

    private void somaUm()
    {
        int aux, i = 0, vaiUm = 1;
        do
        {
            aux = x[i] + vaiUm;
            x[i] = aux % 10;
            vaiUm = aux / 10;
            ++i;
        }
        while( vaiUm > 0);
        if ( i == ( numDigitos + 1 ) ) ++numDigitos;
    }

    public SuperInt divide (SuperInt operando)throws DivisaoZeroException
    {
        boolean negOp1 = this.negativo;
        boolean negOp2 = operando.negativo;
        this.negativo = false;
        operando.negativo = false;
        String auxi=new String();
        int soma=0;
        
        SuperInt aux = this;
        SuperInt res = new SuperInt();
        SuperInt aux2=null;
        try{
        	for(int i=0; i< this.numDigitos; i++)
        	soma=soma+operando.x[i];
        	   		
            if(soma==0)//    if((operando.x[0]==divi.x[0])&&(operando.numDigitos==divi.numDigitos))
            {//Verifica se algarismo em SuperInt é equivalente ao número 0 comparando  a um 
            //auxiliar já existente que equivale a 0 , e caso seja igual joga uma exceção
            	throw new DivisaoZeroException(operando);
            }
            	
            else{
            while ( true )
            {
                aux = aux.subtrai(operando);
                if ( !aux.negativo ) res.somaUm();
                else break;
            }
            this.negativo = negOp1;
            operando.negativo = negOp2;
            if ( ( ( this.negativo ) && ( !operando.negativo ) ) || ( ( !this.negativo ) && ( operando.negativo ) ) )
            {
                res.negativo = true;
            }
        }
        }
        catch(DivisaoZeroException e)
        {//Responsavel por tratar o erro atraves de recursividade;
            auxi=Atribui();
            aux2=new SuperInt(auxi);
            res=this.divide(aux2);
        };
        return res;
    }

    public String toString()
    {
        int jump = ( negativo ) ? 1 : 0; //variavel para controlar a exibicao do SuperInt caso ele seja inteiro
        byte temp[] = new byte[numDigitos+jump];
        for ( int i = 0; i < numDigitos; ++i ) temp[numDigitos-i-1+jump] = (byte)(x[i] + 48);
        if ( jump > 0 ) temp[0] = '-';
        String s = new String(temp);
        return s;
    }
      private static String Atribui(){
        //Funcao criada somente para facilitar o tratamento de exceções durante a inserção de dados ;
    	//Ela retorn o String correto , sem letras ou qualquer outro caractere 
    	//que não seja número;  
    	String msg=new String();
    	try{
    	System.out.println("Digite um valor inteiro: ");
        msg = scan.next();
        if(msg.matches("^[0-9]*$"))
        	System.out.println("\n");
        
   else
	   throw new CaracterIndevido(msg);}
   catch(CaracterIndevido e){
	   msg=Atribui();
   };

return msg;
   };

    public static void main(String args[]) throws CaracterIndevido, DivisaoZeroException
    {

        String t=new String();
        String s=new String();
        SuperInt op1=  null;         
        SuperInt op2=  null;
        t=Atribui();//String é atribuido pelo método Atribui pois assim foi possível
                   //aplicar a recursão para inserção dos dados novamente durante o tratamento de exceções;
        s=Atribui();
        op1=new SuperInt(t);      
        op2=new SuperInt(s);    

        System.out.println("Soma dos inteiros = " + op1.soma(op2));
        System.out.println("SubtraÃ§Ã£o dos inteiros = " + op1.subtrai(op2));
        System.out.println("Multiplicacao dos inteiros = " + op1.multiplica(op2));
        System.out.println("DivisÃ£o dos inteiros = " + op1.divide(op2));
    }

}

