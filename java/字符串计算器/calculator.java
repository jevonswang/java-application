import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {
	
	private static Stack<String> postfixStack  = new Stack<String>();//��׺ʽջ
    private static Stack<Character> opStack  = new Stack<Character>();//�����ջ
    
    private static boolean is_correct = true; //�жϱ��ʽ�Ƿ���ȷ
    private static String ErrorMessage = "";//������Ϣ
  			
    private static int getPri(char c)
    {  //��÷��ŵ�������
    	 switch(c) 
    	 {
    	 case '*':
    	 case '/':
    	 case '%':
    	  return 2;  //����ǳ˳�������2
    	 case '+':
    	 case '-':
    	  return 1;  //����ǼӼ�������1
    	 case '(':
    	 case ')':
    	  return 0;  //��������ţ�����0
    	 case ',':
    	  return -1; //ջ��Ԫ�أ�����-1
    	 default:
    	  is_correct=false;
    	  ErrorMessage="Illegal character��";
    	  return -2; //����
    	 }
    }
    
	private static boolean infixToPostfix(String expression)
	{
	   opStack.push(',');//���������ջ��Ԫ�ض��ţ��˷������ȼ����
	   int currentIndex  = 0;//��ǰ�ַ���λ��
	   int count = 0;//�ϴ����������������������������ַ��ĳ��ȱ��ڻ���֮�����ֵ
	   char currentOp,peekOp;//��ǰ��������ջ��������
	   
	   for(int i=0;i<expression.length();i++) 
	   {
		   currentOp = expression.charAt(i);
		   if(isOperator(currentOp)) //�����ǰ�ַ��������
		   {
		       if(count > 0)
		       {
		         postfixStack.push(expression.substring(currentIndex,currentIndex+count));//ȡ���������֮�������
		       }
		       
		       peekOp = opStack.peek();
		       if(currentOp == ')')
		       {//�����������������ջ�е�Ԫ���Ƴ�����׺ʽջ��ֱ������������
		         while(opStack.peek() != '(')
		         {
		               postfixStack.push(String.valueOf(opStack.pop()));
		         }
		         opStack.pop();
		       }
		       else 
		       {
		         while(currentOp != '(' && peekOp != ',' && getPri(currentOp)<=getPri(peekOp) ) 
		         {
		            postfixStack.push(String.valueOf(opStack.pop()));
		            peekOp = opStack.peek();
		         }
		         opStack.push(currentOp);
		       }
		       count = 0;
		       currentIndex = i+1;
		    } 
		    else if(Character.isDigit(currentOp))//�����ǰ�ַ�������
		    {
		        count++;
		    }
		    else 
		    {
		    	is_correct=false;
		    	ErrorMessage="Illegal character��";
		    	return false;
		    }
		}
	   
		if(count > 1 || (count == 1 && !isOperator(expression.charAt(currentIndex)))) 
		{//���һ���ַ��������Ż��������������������׺ʽջ��
		    postfixStack.push(expression.substring(currentIndex,currentIndex+count));
		} 
		          
		while(opStack.peek() != ',') 
		{
		   postfixStack.push(String.valueOf( opStack.pop()));//��������ջ�е�ʣ���Ԫ����ӵ���׺ʽջ��
		}
		
		/*
		for(int i=0;i<postfixStack.size();i++)
			System.out.print(postfixStack.elementAt(i));
		System.out.println("");
		*/
		
		return true;
    }
	
	
	
	private static boolean isOperator(char c) 
	{
	    return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' ||c == '(' ||c == ')';
    }
	
	
	
		      
	static private int GetResult(String expression)
	 {
		Stack<String> resultStack  = new Stack<String>();
		if(!infixToPostfix(expression))
		{
			if(is_correct)
			{
			is_correct=false;
			ErrorMessage="Syntax Error!";
			}
			return -1;
		}
		
		Collections.reverse(postfixStack);//����׺ʽջ��ת
		
		String ValueA,ValueB,CurrValue;//�������ĵ�һ��ֵ���ڶ���ֵ�͵�ǰ�����
		
		while(!postfixStack.isEmpty()) 
		{
		   CurrValue  = postfixStack.pop();
		   if(!isOperator(CurrValue.charAt(0))) 
		   {//����������������������ջ��
		      resultStack.push(CurrValue);
		   } 
		   else 
		   {//������������Ӳ�����ջ��ȡ����ֵ�͸���ֵһ���������
		       if(!resultStack.isEmpty())
		    	   ValueB  = resultStack.pop();
		       else {is_correct=false;ErrorMessage="Syntax Error��";return -1;}
		       
		       if(!resultStack.isEmpty())
		    	   ValueA  = resultStack.pop();
		       else {is_correct=false;ErrorMessage="Syntax Error��";return -1;}
		       
		       resultStack.push(calculate(ValueA,ValueB,CurrValue.charAt(0)));
		    }
		 }
		// return Double.valueOf(resultStack.pop());
		 return Integer.parseInt(resultStack.pop());
		 
	 }
	
	
	private static String calculate(String ValueA,String ValueB,char currOp) 
	{
         String result  = "";
     
         switch(currOp) 
         {
            case '+':
		      result = String.valueOf(Integer.parseInt(ValueA) + Integer.parseInt(ValueB));
		      break;
		    case '-':
		      result = String.valueOf(Integer.parseInt(ValueA) - Integer.parseInt(ValueB));
		      break;
		    case '%':
			      result = String.valueOf(Integer.parseInt(ValueA) % Integer.parseInt(ValueB)); 
		      break;
		    case '*':
		      result = String.valueOf(Integer.parseInt(ValueA) * Integer.parseInt(ValueB));
		      break;
		    case '/':
		      if(Math.abs(Double.valueOf(ValueB))<10e-6)
		      {
		        is_correct=false;
		        ErrorMessage="Divided by zero��";
		        result="0";
		      } else
	          result = String.valueOf(Integer.parseInt(ValueA) / Integer.parseInt(ValueB));
	          break;
		  }
         
	      return result;
	 }
	
	public static void main(String[] args) {
		 Scanner cin=new Scanner(System.in);
		  String s1;
		  String s;
		  s1=cin.nextLine();
		  s = s1.replaceAll(" ", ""); 
		  //System.out.println(s);
		  
		  while(!s.equals("EOF"))
		  {
			  is_correct=true;
			  int result;
			  if(s.charAt(0)=='-')
				  s='0'+s;
			  //System.out.println(s);
			  
			  result=GetResult(s);
			  if(is_correct)
			  {
		        System.out.println(result);
			  }
			  else System.out.println(ErrorMessage);
		      s1=cin.nextLine();
		      s = s1.replaceAll(" ",""); 
		  }
		  System.out.println("Exit successfully!");
         cin.close();
	}
}
