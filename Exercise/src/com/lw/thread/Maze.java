package com.lw.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程实现走出迷宫
 * @author lenovo
 *
 */
public class Maze {

	int rowCount = 10;
	int colCount = 10;
	int[][] mazeArray = new int[rowCount][colCount];
	Stack<Position> stack = new Stack<Position>();
	
	public void initMaze(){
//		Random r = new Random();
//		for(int row=0;row<rowCount;row++){
//			for(int col=0;col<colCount;col++){
//				mazeArray[row][col] = r.nextInt(2);
//				if(col==0||col==colCount-1||row==0||row==rowCount-1){
//					mazeArray[row][col] = 0;
//				}
//				if(row==rowCount/3&&col==0||row==rowCount/2&&col==colCount-1){
//					mazeArray[row][col] = 1;
//				}
//			}
//		}
		String path = this.getClass().getResource("/").getPath()+
				Maze.class.getPackage().getName().replaceAll("\\.","/");
		File file = new File(path+File.separator+"maze.txt");
		try {
			int row = 0;
			int col = 0;
			BufferedReader br = new BufferedReader(new FileReader(file));
			String content = br.readLine();
			while(content!=null){
				int length = content.length();
				for(int i=0;i<length;i++){
					String c = content.substring(i,i+1);
					mazeArray[row][col++] = Integer.parseInt(c);
				}
				content = br.readLine();
				col = 0;
				row++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void printMaze(){
		for(int x=0;x<rowCount;x++){
			for(int y=0;y<colCount;y++){
				System.out.print(mazeArray[x][y]);
			}
			System.out.println();
		}
	}
	
	
	int[][] iAoWay = new int[2][2];
	volatile boolean p[][] = new boolean[rowCount][colCount];
	
	public int[][] findInWay(){
		int lastRow = 0;
		int lastCol = 0;
		int col = 0;
		int row=0;
		for(;row<rowCount;row++){
			col = 0;
			if(mazeArray[row][col]==1){//入口
				iAoWay[0][0] = row;
				iAoWay[0][1] = col;
				lastRow = row;
				lastCol = col;
				break;
			}
		}
		stack.push(new Position(row, col));
		int[][] in = new int[1][2];
		in[0][0] = row;
		in[0][1] = col;
		return in;
	}
	
	public Stack<Position> findWay(int inRow,int inCol,Stack<Position> stack){
		int row = inRow;
		int col = inCol;
		Stack<Position> stackT = (Stack<Position>) stack.clone();
		while (!stackT.empty()) {
	            if ((col+1<colCount)&&(mazeArray[row][col + 1] == 1) && (p[row][col + 1] == false)) {
	                p[row][col + 1] = true;
	                stackT.push(new Position(row, col + 1));
	                col++;
//	                final int fRow = row;
//	                final int fCol = col;
//	                final Stack<Position> stackTT = stackT;
//	                executor.execute(new Runnable(){
//	                	public void run(){
//	                		findWay(fRow,fCol,stackTT);
//	                	}
//	                });
	            } else if ((row+1<rowCount)&&(mazeArray[row + 1][col] == 1) && (p[row + 1][col] == false)) {
	                p[row + 1][col] = true;
	                stackT.push(new Position(row + 1, col));
	                row++;
//	                final int fRow = row;
//	                final int fCol = col;
//	                final Stack<Position> stackTT = stackT;
//	                executor.execute(new Runnable(){
//	                	public void run(){
//	                		findWay(fRow,fCol,stackTT);
//	                	}
//	                });
	            } else if ((col-1>=0)&&(mazeArray[row][col - 1] == 1) && (p[row][col - 1] == false)) {
	                p[row][col - 1] = true;
	                stackT.push(new Position(row, col - 1));
	                col--;
//	                final int fRow = row;
//	                final int fCol = col;
//	                final Stack<Position> stackTT = stackT;
//	                executor.execute(new Runnable(){
//	                	public void run(){
//	                		findWay(fRow,fCol,stackTT);
//	                	}
//	                });
	            } else if ((row-1>=0)&&(mazeArray[row - 1][col] == 1) && (p[row - 1][col] == false)) {
	                p[row - 1][col] = true;
	                stackT.push(new Position(row - 1, col));
	                row--;
//	                final int fRow = row;
//	                final int fCol = col;
//	                final Stack<Position> stackTT = stackT;
//	                executor.execute(new Runnable(){
//	                	public void run(){
//	                		findWay(fRow,fCol,stackTT);
//	                	}
//	                });
	            } else {
	            	stackT.pop();
	                if(stackT.empty()){
	                    break;
	                }
	                row = stackT.peek().row;
	                col = stackT.peek().col;
	            }
	            if(col>=colCount-1) break;
		}
		if(stackT.empty()){
			System.out.println("no way find!");
			return null;
		}
		else return stackT;
	}
	ExecutorService executor = Executors.newFixedThreadPool(rowCount+colCount);
	
	
	public static void main(String[] args) {
		Maze maze = new Maze();
		maze.initMaze();
//		maze.printMaze();
		int[][] in = maze.findInWay();
		final int fRow = in[0][0];
		final int fCol = in[0][1];
		final Stack<Position> stack2 = (Stack<Position>) maze.stack.clone();
		Stack<Position> s = maze.findWay(fRow,fCol,stack2);
		List<Position> pList = new ArrayList<Position>();
		if (!s.empty()) {
            for(int i=s.size()-1;i>=0;i--){
            	Position pos = maze.new Position();
            	System.out.print(s.get(i));
            	if((i-1)%5==0) System.out.println("--->");
            }
        }
//		pList.forEach(position->System.out.println(position.row+","+position.col));
//		maze.executor.execute(new Runnable() {
//			@Override
//			public void run() {
//				
//			}
//		});
		
//		String a = "adddc..ccccc..ccc..c".replaceAll("\\.", "eee");
//		System.out.println(a);
	}
	
	
	
	class Position{
	    public Position(){

	    }

	    public Position(int row, int col){
	        this.col = col;
	        this.row = row;
	    }

	    public String toString(){
	        return "(" + row + " ," + col + ")";
	    }

	    int row;
	    int col;
	}
}
