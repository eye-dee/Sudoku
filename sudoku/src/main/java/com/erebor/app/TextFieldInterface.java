package com.erebor.app;

import java.util.Scanner;
import javax.swing.JTextField;
import java.util.InputMismatchException;
import javax.swing.*;
import java.net.URL;
import java.awt.event.*;
import java.awt.*;

public class TextFieldInterface implements KeyListener, FocusListener{
	int _i,_j;
	SudokuSolver _s;
	String cur;
	boolean hinted;
	protected JTextField text;
	public TextFieldInterface(SudokuSolver s,int i, int j){
		_s = s;
		_i = i; _j = j;
		text = new JTextField(1);
		text.addKeyListener(this);
		text.addFocusListener(this);
		text.setText("0");
		cur = "0";
	}

	public void keyPressed(KeyEvent e){
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
				if (hinted) {
					hinted = false;
				}
				text.setText(str);
				text.setEditable(false);
				text.setBackground(Color.GREEN);
			}
		}
	}
	public void setHint(boolean h){
		hinted = h;
		if (h){
			text.setBackground(Color.ORANGE);
		} else {
			text.setBackground(UIManager.getColor("TextField.background"));
		}
	}

	public JTextField getTextField(){
		return text;
	}
	public void setText(String str){
		text.setText(str);
		if (str.equals("0")){
			text.setBackground(Color.WHITE);
			text.setEditable(true);
		} else {
			text.setBackground(Color.GREEN);
			text.setEditable(false);
		}
	}
	public void setEditable(boolean ed){
		text.setEditable(ed);
		if (!ed){
			text.setBackground(Color.GREEN);
		}
	}
	@Override
	public void focusGained(FocusEvent e) {
		e.getComponent().setBackground(Color.MAGENTA);
	}
	@Override
	public void focusLost(FocusEvent e) {
		if (hinted) {
			e.getComponent().setBackground(Color.ORANGE);
		} else {
			if (text.isEditable()) {
				e.getComponent().setBackground(UIManager.getColor("TextField.background"));
			} else {
				e.getComponent().setBackground(Color.GREEN);
			}
		}
	}
}

class TextFieldControler extends JPanel{
	SudokuSolver _s;
	TextFieldInterface[][] texts = new TextFieldInterface[Const.N][Const.N];
	GridLayout gridLayout = new GridLayout(Const.N, Const.N);
	Integer hintI, hintJ;
	TextFieldControler(SudokuSolver s){
		_s = s;
		hintI = new Integer(-1);
		hintJ = new Integer(-1);
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

	void setHint(int i, int j){
		if (hintI > -1 && hintJ < 10 && hintJ > -1 && hintJ < 10){
			texts[hintI][hintJ].setHint(false);
		}
		hintI = i;
		hintJ = j;
		if (hintI > -1 && hintJ < 10 && hintJ > -1 && hintJ < 10){
			texts[hintI][hintJ].setHint(true);
		}
	}
}