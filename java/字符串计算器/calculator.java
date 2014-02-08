import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {
	
	private static Stack<String> postfixStack  = new Stack<String>();//后缀式栈
    private static Stack<Character> opStack  = new Stack<Character>();//运算符栈
    
    private static boolean is_correct = true; //判断表达式是否正确
    private static String ErrorMessage = "";//错误信息
  			
    private static int getPri(char c)
    {  //获得符号的优先性
    	 switch(c) 
    	 {
    	 case '*':
    	 case '/':
    	 case '%':
    	  return 2;  //如果是乘除，返回2
    	 case '+':
    	 case '-':
    	  return 1;  //如果是加减，返回1
    	 case '(':
    	 case ')':
    	  return 0;  //如果是括号，返回0
    	 case ',':
    	  return -1; //栈底元素，返回-1
    	 default:
    	  is_correct=false;
    	  ErrorMessage="Illegal character！";
    	  return -2; //错误
    	 }
    }
    
	private static boolean infixToPostfix(String expression)
	{
	   opStack.push(',');//运算符放入栈底元素逗号，此符号优先级最低
	   int currentIndex  = 0;//当前字符的位置
	   int count = 0;//上次算术运算符到本次算术运算符的字符的长度便于或者之间的数值
	   char currentOp,peekOp;//当前操作符和栈顶操作符
	   
	   for(int i=0;i<expression.length();i++) 
	   {
		   currentOp = expression.charAt(i);
		   if(isOperator(currentOp)) //如果当前字符是运算符
		   {
		       if(count > 0)
		       {
		         postfixStack.push(expression.substring(currentIndex,currentIndex+count));//取两个运算符之间的数字
		       }
		       
		       peekOp = opStack.peek();
		       if(currentOp == ')')
		       {//遇到右括号则将运算符栈中的元素移除到后缀式栈中直到遇到左括号
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
		    else if(Character.isDigit(currentOp))//如果当前字符是数字
		    {
		        count++;
		    }
		    else 
		    {
		    	is_correct=false;
		    	ErrorMessage="Illegal character！";
		    	return false;
		    }
		}
	   
		if(count > 1 || (count == 1 && !isOperator(expression.charAt(currentIndex)))) 
		{//最后一个字符不是括号或者其他运算符的则加入后缀式栈中
		    postfixStack.push(expression.substring(currentIndex,currentIndex+count));
		} 
		          
		while(opStack.peek() != ',') 
		{
		   postfixStack.push(String.valueOf( opStack.pop()));//将操作符栈中的剩余的元素添加到后缀式栈中
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
		
		Collections.reverse(postfixStack);//将后缀式栈反转
		
		String ValueA,ValueB,CurrValue;//参与计算的第一个值，第二个值和当前运算符
		
		while(!postfixStack.isEmpty()) 
		{
		   CurrValue  = postfixStack.pop();
		   if(!isOperator(CurrValue.charAt(0))) 
		   {//如果不是运算符则存入操作数栈中
		      resultStack.push(CurrValue);
		   } 
		   else 
		   {//如果是运算符则从操作数栈中取两个值和该数值一起参与运算
		       if(!resultStack.isEmpty())
		    	   ValueB  = resultStack.pop();
		       else {is_correct=false;ErrorMessage="Syntax Error！";return -1;}
		       
		       if(!resultStack.isEmpty())
		    	   ValueA  = resultStack.pop();
		       else {is_correct=false;ErrorMessage="Syntax Error！";return -1;}
		       
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
		        ErrorMessage="Divided by zero！";
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
