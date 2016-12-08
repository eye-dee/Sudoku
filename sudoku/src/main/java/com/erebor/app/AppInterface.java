package com.erebor.app;

import java.util.Scanner;
import java.util.InputMismatchException;
import javax.swing.*;
import java.net.URL;
import java.awt.event.*;
import java.awt.*;

public class AppInterface{
	private static SudokuSolver s;
	private static void createAndShowGui(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Buttons Example");

		frame.setLayout(new FlowLayout());
		frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		s = new SudokuSolver();
		TextFieldControler textContent = new TextFieldControler(s);
		ButtonControler buttonContentPane = new ButtonControler(s,frame,textContent);
		buttonContentPane.setOpaque(true); // content panes must be opaque
		frame.add(buttonContentPane);
		frame.add(textContent);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] ergs){
		System.out.println("Started");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}
}