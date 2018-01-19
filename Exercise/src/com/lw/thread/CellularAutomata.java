package com.lw.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CellularAutomata {

	/**
	 * 栅栏模型：所有线程到达栅栏位置时放行。
	 * CyclicBarrier 栅栏的类
	 * barrier初始化时设置了count这就是线程个数，
	 * await()每调用一次，初始化时的count数值减一，直到为0，表示所有线程都达到栅栏位置那么就可以放行
	 */
	private final Board mainBoard;
	private final CyclicBarrier barrier;// 栅栏
	private final Worker[] workers;

	public CellularAutomata(Board board) {
		this.mainBoard = board;
		int count = Runtime.getRuntime().availableProcessors();
		this.barrier = new CyclicBarrier(count, new Runnable() {
			public void run() {
				mainBoard.commitNewValues();
			}
		});
		this.workers = new Worker[count];
		for (int i = 0; i < count; i++) {
			workers[i] = new Worker(mainBoard.getSubBoard(count, i));
		}
	}

	private class Worker implements Runnable {

		private final Board board;

		public Worker(Board board) {
			this.board = board;
		}

		@Override
		public void run() {
			while (!board.hasConverged()) {
				for (int x = 0; x < board.getMaxX(); x++) {
					for (int y = 0; y < board.getMaxY(); y++) {
						board.setNewValue(x, y, computeValue(x, y));
						try {
							barrier.await();
						} catch (InterruptedException | BrokenBarrierException ex) {
							return;
						}
					}
				}
			}
		}

		private int computeValue(int x, int y) {
			// Compute the new value that goes in (x,y)
			return 0;
		}
	}

	public void start() {
		for (int i = 0; i < workers.length; i++) {
			new Thread(workers[i]).start();
			mainBoard.waitForConvergence();
		}
	}

	interface Board {
		int getMaxX();

		int getMaxY();

		int getValue(int x, int y);

		int setNewValue(int x, int y, int value);

		void commitNewValues();

		boolean hasConverged();

		void waitForConvergence();

		Board getSubBoard(int numPartitions, int index);
	}
}
