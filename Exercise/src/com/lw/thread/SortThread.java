package com.lw.thread;

import java.util.ArrayList;

/**
 * 排序线程
 * @author lenovo
 *
 */
public class SortThread extends Thread{

	private double[] array;
	public SortThread(double[] array){
		this.array = array;
	}
	
	@Override
	public void run() {
		/*
		 * 对数组排序
		 */
		double temp = 0.0d;
		for(int i=0;i<array.length;i++){
			for(int j=i+1;j<array.length;j++){
				if(array[i]>array[j]){
					temp = array[j];
					array[j] = array[i];
					array[i] = temp;
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		double[] array = new double[100];
		for(int i=0;i<array.length;i++){
			array[i] = Math.random();
		}
		SortThread st = new SortThread(array);
		st.start();
		try{
			st.join();//阻塞调用线程，等待SortThread线程排序结束
			System.out.println("Minimum:"+array[0]);
			System.out.println("Median:"+array[array.length/2]);
			System.out.println("Maximum:"+array[array.length-1]);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}

}
