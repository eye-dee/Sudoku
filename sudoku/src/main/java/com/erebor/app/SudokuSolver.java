package com.erebor.app;
import java.util.Scanner;
import java.util.Vector;
import java.util.Random;
import java.util.Arrays;
import java.util.Date;
import java.lang.System;
import java.io.*;

class Const{
	public static final int N = 9; 
}

public class SudokuSolver{
	private Vector<SudokuView> vsv = new Vector<>();
	private int cur = -1;
	Random r = new Random((new Date()).getTime());
	public boolean readFile(String file){
		int prevSize = vsv.size();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch( FileNotFoundException fnfe){
			System.out.println(fnfe.getMessage());
			return false;
		} catch( IOException ioe){
			System.out.println(ioe.getMessage());
			return false;
		}

		try {
			String line = br.readLine();
			while (line != null) {
			    SudokuView temp = new SudokuView();
			    if (temp.fromString(line))
			   		vsv.add(temp);

			   	line = br.readLine();
		   	}
		} catch( FileNotFoundException fnfe){
			System.out.println(fnfe.getMessage());
			return false;
		} catch( IOException ioe){
			System.out.println(ioe.getMessage());
		}

		System.out.println(vsv.size());
		if (vsv.size() != prevSize)
			return true;
		else
			return false;
	}

	public void showHint(){
		if (cur == -1){
			System.out.println("No current game");
			return;
		}

		System.out.println(vsv.elementAt(cur).getHint());
	}

	public boolean set(int i,int j, int value){
		if (cur == -1){
			return false;
		}

		return vsv.elementAt(cur).set(i,j,value);
	}

	public void showCurrent(){
		if (cur != -1) {
			vsv.elementAt(cur).show();
		} else {
			System.out.println("No current game");
		}
	}

	public boolean isWin(){
		if (cur != -1){
			return vsv.elementAt(cur).checkGame();
		} else {
			System.out.println("No current game");
		}
		return false;
	}

	public void chooseRandom() {
		cur = r.nextInt(vsv.size());
		if (vsv.elementAt(cur).solve()){
			System.out.println(vsv.elementAt(cur).checkSolved() ? "solved good" : "solved bad");
		} else {
			System.out.println("can't solve");
			cur = -1;
		}
	}
}
