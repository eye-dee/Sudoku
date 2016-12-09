package com.erebor.app;
import java.util.Scanner;
import java.util.Vector;
import java.util.Random;
import java.util.Arrays;
import java.util.Date;
import java.lang.System;
import java.io.*;
import java.lang.*;

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

	public String showHint(){
		String res;
		if (cur == -1){
			System.out.println("No current game");
			res = new String("No current game");
			return res;
		}

		res = new String(vsv.elementAt(cur).getHint());
		System.out.println(res);

		return res;
	}

	public boolean set(int i,int j, int value){
		if (cur == -1){
			return false;
		}
		try {
			return vsv.elementAt(cur).set(i,j,value);
		} catch (ArrayIndexOutOfBoundsException aioobe){
			System.out.println(aioobe.getMessage());

			return false;
		}
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

	public int get(int i, int j){
		return vsv.elementAt(cur).get(i,j);
	}

	public boolean chooseRandom() {
		if (vsv.size() == 0){
			System.out.println("No games");
			return false;
		}
		cur = r.nextInt(vsv.size());
		if (vsv.elementAt(cur).solve()){
			System.out.println(vsv.elementAt(cur).checkSolved() ? "solved good" : "solved bad");
			return true;
		} else {
			System.out.println("can't solve");
			cur = -1;
			return false;
		}
	}
}
