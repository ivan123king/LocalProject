package com.lw.notice;

import java.util.EmptyStackException;

/**
 * 类泛型化
 * @author lenovo
 *
 * @param <E>
 */
public class Stack<E> {
	private E[] elements;
	private int size = 0;
	private static final int DEFAULT_INITAL_CAPACITY = 16;
	
	public Stack(){
		//如果泛型，此处编译报错： 不能创建不可具体化的类型的数组
//		elements = new E[DEFAULT_INITAL_CAPACITY];
		
		//解决方案一：
		elements = (E[])new Object[DEFAULT_INITAL_CAPACITY];
		
		/*
		 * 解决方案二：
		 * 1. 申明elements为Object数组   Object[] elements
		 * 2. 强转  E result = (E)elements[--size] 
		 */
	}
	
	public void push(E e){
		elements[size++] = e;
	}
	
	public E pop(){
		if(size==0) throw new EmptyStackException();
		E result = elements[--size];
		elements[size] = null;
		return result;
	}
	
	public static void main(String[] args) {
		Stack<String> stack = new Stack<String>();
		String argss[] = new String[]{"a","b","c"};
		for(String arg:argss){
			stack.push(arg);
		}
		System.out.println(stack.pop().toUpperCase());
	}
}
