package com.monolith.legacy;

public class ClassA<T> {

	public T value;
	public  T getValue()
	{
		return value;
	}
	public void setValue(T t)
	{
		this.value = t;
	}
}
