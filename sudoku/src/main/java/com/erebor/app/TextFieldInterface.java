package com.erebor.app;

import java.util.Scanner;
import javax.swing.JTextField;
import java.util.InputMismatchException;
import javax.swing.*;
import java.net.URL;
import java.awt.event.*;
import java.awt.*;

public class TextFieldInterface implements KeyListener{
	int _i,_j;
	SudokuSolver _s;
	String cur;
	protected JTextField text;
	public TextFieldInterface(SudokuSolver s,int i, int j){
		_s = s;
		_i = i; _j = j;
		text = new JTextField(1);
		text.addKeyListener(this);
		text.setText("0");
	}

	public void keyPressed(KeyEvent e){
		cur = text.getText();
	}
	public void keyTyped(KeyEvent e){
	}
	public void keyReleased(KeyEvent e) {
		text.setText(cur);
		char c = e.getKeyChar();
		if (Character.isDigit(c) && text.isEditable()) {
			String str = "" + c;

			if (!_s.set(_i,_j,Integer.parseInt(str)))
				text.setText("0");
			else {
				text.setText(str);
				text.setEditable(false);
			}
		}
	}

	public JTextField getTextField(){
		return text;
	}
	public void setText(String str){
		text.setText(str);
	}
	public void setEditable(boolean ed){
		text.setEditable(ed);
	}
}

class TextFieldControler extends JPanel{
	SudokuSolver _s;
	TextFieldInterface[][] texts = new TextFieldInterface[Const.N][Const.N];
	GridLayout gridLayout = new GridLayout(Const.N, Const.N);
	TextFieldControler(SudokuSolver s){
		_s = s;
		setLayout(gridLayout);
		for (int i = 0; i < Const.N; ++i){
			for (int j = 0; j < Const.N; ++j){
				texts[i][j] = new TextFieldInterface(s,i,j);
				add(texts[i][j].getTextField());
			}
		}
	}
	public void set(int i, int j, int value){
		texts[i][j].setEditable(value == 0);
		texts[i][j].setText(String.valueOf(value));
	}
}