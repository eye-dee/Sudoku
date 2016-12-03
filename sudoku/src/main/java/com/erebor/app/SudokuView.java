package com.erebor.app;
import java.util.Scanner;
import java.util.Vector;
import java.util.Random;
import java.util.Arrays;
import java.util.Date;
import java.lang.System;
import java.io.*;

public class SudokuView{
	private int[][] _cur = new int[Const.N][Const.N];
	private int[][] _solved = new int[Const.N][Const.N];
	private int[][][] prediction = new int[Const.N][Const.N][];
	Random r = new Random((new Date()).getTime());

	public boolean set(int i, int j, int value){
		if (_cur[i][j] != 0)
			return false;
		if (_solved[i][j] != value){
			return false;
		}

		_cur[i][j] = value;

		return true;
	}

	public void makePrediction(){
		int var[] = new int[Const.N];
		for (int i = 0; i < Const.N; ++i){
			for (int j = 0; j < Const.N; ++j){
				if (_solved[i][j] != 0)
					continue;
				int iC = i/3, jC = j/3;

				for (int k = 0; k < Const.N; ++k){
					var[k] = 0;
				}

				for (int k = 0; k < Const.N; ++k){
					if (_solved[i][k] > 0){
						var[_solved[i][k] - 1] = 1;
					}
				}

				for (int k = 0; k < Const.N; ++k){
					if (_solved[k][j] > 0){
						var[_solved[k][j] - 1] = 1;
					}
				}

				for (int k = 0; k < 3; ++k){
					for (int c = 0; c < 3; ++c){
						if (_solved[iC*3 + k][jC*3 + c] != 0){
							var[_solved[iC*3 + k][jC*3 + c] - 1]++;
						}
					}
				}

				int count = 0;
				for (int k = 0; k < Const.N; ++k){
					if (var[k] == 0){
						count++;
					}
				}

				if (count == 0){
					System.out.println("Can't make prediction");
					return;
				}
				prediction[i][j] = new int[count];
				for (int k = 0, c = 0; k < Const.N; ++k){
					if (var[k] == 0){
						prediction[i][j][c++] = k + 1; 
					}
				}
			}
		}
	}

	public boolean fromString(String str){
		Scanner sc = new Scanner(str);

		for (int i = 0; i < Const.N; ++i){
			for (int j = 0; j < Const.N; ++j){
				if (sc.hasNextInt()){
					_cur[i][j] = sc.nextInt();
				} else {
					for (int p = 0; p < Const.N; ++p)
						for (int c = 0; c < Const.N; ++c)
							_cur[p][c] = 0;

					return false;
				}
			}
		}

		return true;
	}

	public void show(){
		for (int i = 0; i < Const.N; ++i){
			System.out.println(Arrays.toString(_cur[i]));
		}
	}

	public void showSolved(){
		for (int i = 0; i < Const.N; ++i){
			System.out.println(Arrays.toString(_solved[i]));
		}
	}

	public String getHint(){
		int i = r.nextInt(Const.N), j = r.nextInt(Const.N);
		while (_cur[i][j] != 0){
			i = r.nextInt(Const.N); j = r.nextInt(Const.N);
		}

		return new String("set " + i + ' ' + j + ' ' + _solved[i][j]);
	}

	public boolean checkGame(){
		for (int i = 0; i < Const.N; ++i){
			for (int j = 0; j < Const.N; ++j){
				if (_cur[i][j] == 0)
					return false;
			}
		}
		return true;
	}

	public boolean checkSolved(){
		for (int i = 0; i < Const.N; ++i){
			for (int j = 0; j < Const.N; ++j){
				if (_solved[i][j] == 0)
					return false;
				if (!isRight(i,j)){
					return false;
				}
			}
		}
		return true;
	}

	boolean isRightString(int i, int j){
		int[] var = new int[Const.N];
		for (int k = 0; k < Const.N; ++k){
			var[k] = 0;
		}

		for (int k = 0; k < Const.N; ++k){
			if (_solved[i][k] != 0){
				var[_solved[i][k] - 1]++;
			}
		}

		for (int k = 0; k < Const.N; ++k){
			if (var[k] > 1){
				return false;
			}
		}

		return true;
	}

	boolean isRightColumn(int i, int j){
		int[] var = new int[Const.N];
		for (int k = 0; k < Const.N; ++k){
			var[k] = 0;
		}

		for (int k = 0; k < Const.N; ++k){
			if (_solved[k][j] != 0){
				var[_solved[k][j] - 1]++;
			}
		}

		for (int k = 0; k < Const.N; ++k){
			if (var[k] > 1){
				return false;
			}
		}

		return true;
	}

	boolean isRightCell(int i, int j){
		int[] var = new int[Const.N];
		for (int k = 0; k < Const.N; ++k){
			var[k] = 0;
		}

		int iC = i/3, jC = j/3;

		for (int k = 0; k < 3; ++k){
			for (int c = 0; c < 3; ++c){
				if (_solved[iC*3 + k][jC*3 + c] != 0){
					var[_solved[iC*3 + k][jC*3 + c] - 1]++;
				}
			}
		}

		for (int k = 0; k < Const.N; ++k){
			if (var[k] > 1){
				return false;
			}
		}

		return true;
	}

	boolean isRight(int i, int j){
		return isRightString(i,j) && isRightColumn(i,j) && isRightCell(i,j);
	}

	public boolean solveRecursion(int i, int j){
		if (i == Const.N){
			return true;
		}
		if (_solved[i][j] != 0){
			int nextI = i, nextJ = j + 1;
			if (nextJ == Const.N){
				nextI++;
				nextJ = 0;
			}
			return solveRecursion(nextI,nextJ);
		}
		for (int k = 0; k < prediction[i][j].length; ++k){
			_solved[i][j] = prediction[i][j][k];

			if (isRight(i,j)){
				int nextI = i, nextJ = j + 1;
				if (nextJ == Const.N){
					nextI++;
					nextJ = 0;
				}
				if (solveRecursion(nextI,nextJ))
					return true;
				else
					_solved[i][j] = 0;
			} else {
				_solved[i][j] = 0;
			}
		}

		return false;
	}

	public boolean solve(){
		for (int i = 0; i < Const.N; ++i){
			System.arraycopy(_cur[i],0, _solved[i], 0, _cur[i].length);
		}

		for (int i = 0; i < Const.N; ++i){
			for (int j = 0; j < Const.N; ++j){
				if (!isRight(i,j)){
					System.out.println("Not good start");
					return false;
				}
			}
		}

		makePrediction();

		return solveRecursion(0,0);
	}
}