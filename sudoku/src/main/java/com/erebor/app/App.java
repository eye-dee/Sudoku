package com.erebor.app;

import java.util.Scanner;
import java.util.InputMismatchException;
import javax.swing.*;

public class App
{
    public static void main( String[] args )
    {
		SudokuSolver s = new SudokuSolver();
    	Scanner sc = new Scanner(System.in);

    	while (sc.hasNext()){
    		String com = sc.nextLine();

    		String[] stringList = com.split(" ");

    		if (stringList.length > 0){
	    		if (stringList[0].compareTo("read") == 0){
	    			if (stringList.length == 2){
	    				s.readFile(stringList[1]);
	    			} else {
	    				System.out.println("Непонятное количество параметров(либо слишком много, либо слишком мало)");
	    			}
    			} else if (stringList[0].compareTo("set") == 0){
	    			if (stringList.length == 4){
	    				try {
	    					int[] set = new int[3];
	    					for (int i = 0; i < 3; ++i){
	    						Scanner scIn = new Scanner(stringList[i+1]);

	    						set[i] = scIn.nextInt();
	    					}

	    					if (s.set(set[0],set[1],set[2])){
	    						System.out.println("set sucsesfully");
	    					} else {
	    						System.out.println("can't set");
	    					}
	    				} catch (InputMismatchException ime){
	    					System.out.println(ime.getMessage());
	    				}
	    			} else {
	    				System.out.println("Непонятное количество параметров(либо слишком много, либо слишком мало)");
	    			}
	    			if (s.isWin()){
	    				System.out.println("Поздравляем - игра выиграна");
	    			}
    			} else if (stringList[0].compareTo("show") == 0){
	    			s.showCurrent();
    			} else if (stringList[0].compareTo("start_new") == 0){
	    			s.chooseRandom();
    			} else if (stringList[0].compareTo("get_hint") == 0){
	    			s.showHint();
    			} else if (stringList[0].compareTo("exit") == 0) {
	    			break;
    			}
    		}
    	}
    }
}