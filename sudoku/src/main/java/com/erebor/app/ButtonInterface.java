package com.erebor.app;

import java.util.Scanner;
import java.util.InputMismatchException;
import javax.swing.*;
import java.net.URL;
import java.awt.event.*;

public class ButtonInterface implements ActionListener{
	SudokuSolver _s;
	JFrame frame;
	TextFieldControler tfc;
	private JLabel hello = new JLabel("Привет, Это Судоку");
	private final int N = 4;
	public int getN(){
		return N;
	}
	protected JButton[] buttons = new JButton[N];
	public ButtonInterface(SudokuSolver s, JFrame f, TextFieldControler t){
		_s = s;
		frame = f;
		tfc = t;
		ImageIcon icon = createImageIcon("/home/igor/java/Sudoku/sudoku/temp.xcf");

		buttons[0] = new JButton("Начать заново", icon);
		buttons[0].setMnemonic(KeyEvent.VK_D); //Alt-D clicks button
		buttons[0].setActionCommand("start_new");
		buttons[0].setToolTipText("Начать новую игру");

		buttons[1] = new JButton("Дать подсказку", icon);
		buttons[1].setMnemonic(KeyEvent.VK_M); //Alt-M clicks button
		buttons[1].setActionCommand("get_hint");
		buttons[1].setToolTipText("Выдать подсказочку в консоль");

		buttons[2] = new JButton("Считать из файла", icon); //Use default text position Center, Trailing(RIGHT)
		buttons[2].setMnemonic(KeyEvent.VK_S); //Alt-E clicks button
		buttons[2].setActionCommand("read");
		buttons[2].setToolTipText("Считать из файда \"file\" всю информацию");

		buttons[3] = new JButton("Выход", icon); //Use default text position Center, Trailing(RIGHT)
		buttons[3].setMnemonic(KeyEvent.VK_E); //Alt-E clicks button
		buttons[3].setActionCommand("exit");
		buttons[3].setToolTipText("Выход из программы");
		/*for (i : buttons){
			i.addActionListener(this);
		}*/
		for (int i = 0; i < N; ++i){
			buttons[i].addActionListener(this);
		}
	}

	public JButton[] getButton(){
		return buttons;
	}
	public JLabel getLabel(){
		return hello;
	}

	public void actionPerformed(ActionEvent e) {
		if ("start_new".equals(e.getActionCommand())){
			if (_s.chooseRandom()) {
				for (int i = 0; i < Const.N; ++i){
					for (int j = 0; j < Const.N; ++j){
						tfc.set(i,
							j,
							_s.get(i,j));
					}
				}
			}
		} else if ("get_hint".equals(e.getActionCommand())){
			_s.showHint();
		} else if ("read".equals(e.getActionCommand())){
			_s.readFile("file");
		} else if ("exit".equals(e.getActionCommand())){
			frame.dispose();
		}
	}

	protected static ImageIcon createImageIcon(String path){
		URL imgURL = AppInterface.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find image int system : " + path);
			return null;
		}
	}
}

class ButtonControler extends JPanel{
	JFrame frame;
	ButtonInterface bi;
	JButton[] buttons;
	SudokuSolver _s;
	TextFieldControler tfc;
	ButtonControler(SudokuSolver s, JFrame f, TextFieldControler t){
		_s = s;
		frame = f;
		tfc = t;
		bi = new ButtonInterface(s,f,t);
		buttons = bi.getButton();

		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

		add(bi.getLabel());
		for (int i = 0; i < bi.getN(); ++i){
			add(buttons[i]);
		}
	}
}