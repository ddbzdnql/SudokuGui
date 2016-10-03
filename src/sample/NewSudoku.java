package sample;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.Scanner;
import java.util.Stack;

public class NewSudoku {
	public int[][] array; //array that stores the sudoku board.
	public Stack<Storage> toStore;//stack that solves the puzzle.
	public static int limit = 15;//we dont need this, i just forgot to delete it.
	public int space = 0;//how many blank tiles in the board.
	@FXML
	public GridPane gp00;
	//constructor, instantiate the board from user input.
	public NewSudoku(int[][] board){
		toStore = new Stack<Storage>();
		Scanner toRead = new Scanner(System.in);
		array = new int[9][9];
		for (int i = 0; i<9; i++){
			for (int j = 0; j < 9; j++) {
				array[i][j] = board[i][j];
				if (array[i][j] == 0) {
					space++;
				}
			}
		}
		toRead.close();
	}
	
	public static int[][] solve(int[][] board){
		NewSudoku test = new NewSudoku(board);//instantiate the puzzle.
		//first put the first blank space into the stack.
		int i = 0, j = 0;
		while(i<9){
			if (test.array[i][j]==0){//find the blank space.
				test.toStore.push(new Storage(i,j));//push the stack
				for (int k = 1; k<10; k++){
					if (test.check(i,j,k)){
						test.array[i][j] = k;
						test.toStore.peek().record.add(k);//record the used value of this blank.
						break;
					}
				}
				break;//because i want to break out of this loop, so i did
				//not use nested loop, but instead i used while loop.
			}
			j++;
			if (j==9){
				j=0;
				i++;
			}
		}
			final long stTime = System.currentTimeMillis();
			long diff = 0;
			do{//the main loop to solve the puzzle.
				//it basically applied depth search to the puzzle.
				//it is hard to explain, but i think it works.
				i = 0; j = 0;
				boolean flag = true;
				while(i<9&&flag==true){
					if (test.toStore.size()>=test.space){
						test.array[test.toStore.peek().y]
								[test.toStore.peek().x] = 0;
					}//this if statement serves to ensure all solutions are visited.
					if (test.array[i][j]==0){
						//the procedure of this search is to add number to the board while
						//completely following the rule of a sudoku game.
						//so that if the algorithm filled in all blank spaces,
						//it will absolutely be a set of solution.
						if (test.toStore.size()<test.space){
							test.toStore.push(new Storage(i,j));
						}
						while (flag == true&&!(test.toStore.isEmpty())){
							int x = test.toStore.peek().x;
							int y = test.toStore.peek().y;
							for (int k=1; k<10; k++){
								if (test.check(y, x, k)){
									test.array[y][x] = k;
									test.toStore.peek().record.add(k);
									flag = false;
									break;
								}
							}
							if (flag ==true){
								test.array[y][x] = 0;
								test.toStore.pop();
							}
						}
						break;
					}
					j++;
					if (j==9){
						j=0;
						i++;
					}
				}
				if (test.toStore.size()>=test.space){
					//here, if the amount of filled numbers reaches the amount of blank spaces
					//we can assume that we found a set of solution, then print it.
					for (int i1 = 0; i1<9; i1++){
						for (int j1 = 0; j1<9; j1++){
							System.out.print(test.array[i1][j1]+" ");
						}
						System.out.println();
					}
					System.out.println();
					return test.array;
				}
				long endTime = System.currentTimeMillis();
				diff = endTime - stTime;

			}while(!(test.toStore.isEmpty()) && (diff <= 20000));
		return null;
		}
	
	public boolean check(int i, int j, int value){
		//the method makes sure that every number added to the board perfectly
		//followed the rule of sudoku game.
		for (int k = 0; k<9; k++){
			if (this.array[i][k] == value){
				//every line
				return false;
			}
			if (this.array[k][j] == value){
				//every row
				return false;
			}
			if (this.array[(i/3)*3+k/3][(j/3)*3+k%3] == value){
				//every 3x3 square
				return false;
			}
		}
		if (this.toStore.peek().record.size()>0)
		for (int k1 = 0; k1<this.toStore.peek().record.size(); k1++){
			//also used numbers of one blank space cannot be used again.
			if ((this.toStore.peek().record.get(k1)) == value){
				return false;
			}
		}
		return true;
	}
}
