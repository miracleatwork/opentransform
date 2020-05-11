/**
 * This is a sample Dependee Class which has methods consumed by various Dependent classes.
 * We have included different types like Generic and Complex Object types as parameters to cover broad scenarios.
 * We have included methods with various modifiers to demonstrate the capabilities and limitations of Transform.
 * 
 */
package com.monolith.legacy;

public class DependeeClass {

	private int  prInt =1;
	public int puInt = 2;
	protected int proInt = 3;
	private int intI=0;
	private String b="";
	private float f=0f;
	private char c='c';
	private Double bd=0d;
	

	private ClassA<String> a = new ClassA<String>();
		public ClassA<String> getA()
		{
			return a;
		}
		public void setA(ClassA<String> a)
		{
			this.a = a;
		}
		
		public int getInt()
		{
			
			return puInt;
		}
		
		public void setInt(int i)
		{
			puInt = i;
		}
		
		public void setInternals(int i, String b, float f, char c, Double bd )
		{
			intI=i;
			this.b = b;
			this.f = f;
			this.c = c;
			this.bd = bd;
		}
		
		public String getInternals(int i)
		{
			if (i==intI)
			{
				return ""+this.intI+","+this.b+","+this.f+","+this.c+","+this.bd;
						
			}
			else
			{
				return "Value of i didn't match";
			}
		}
		
		public String getString()
		{
			return "String from  "+ this.toString();
		}
		
		private int getPrivateInt()
		{
			// no need to process private methods
			return getInt();
		}
		
		protected int getProtectedInt()
		{
			return getInt();
		}
		
	
	

}
