package com.erebor.app;

import java.util.Scanner;
import java.util.InputMismatchException;
import javax.swing.*;
import java.net.URL;
import java.awt.event.*;

public class AppInterface extends JPanel implements ActionListener{
	protected static JButton left,mid,right;
	public AppInterface(){
		ImageIcon leftIcon = createImageIcon("temp.xcf"),
			midIcon = createImageIcon("temp.xcf"),
			rightIcon = createImageIcon("temp.xcf");

		left = new JButton("Disable centre button", leftIcon);
		left.setVerticalTextPosition(AbstractButton.BOTTOM);
		left.setHorizontalTextPosition(AbstractButton.LEADING);
		left.setMnemonic(KeyEvent.VK_D); //Alt-D clicks button
		left.setActionCommand("disable");
		left.setToolTipText("disable the Centre button");

		mid = new JButton("Centre button", midIcon);
		mid.setVerticalTextPosition(AbstractButton.BOTTOM);
		mid.setHorizontalTextPosition(AbstractButton.CENTER);
		mid.setMnemonic(KeyEvent.VK_M); //Alt-M clicks button
		mid.setToolTipText("Centre button");

		right = new JButton("Enable centre button", rightIcon); //Use default text position Center, Trailing(RIGHT)
		right.setMnemonic(KeyEvent.VK_E); //Alt-D clicks button
		right.setActionCommand("enable");
		right.setEnabled(false);
		left.addActionListener(this);
		right.addActionListener(this);
		right.setToolTipText("enable the Centre button");

		add(left);
		add(mid);
		add(right);
	}

	public void actionPerformed(ActionEvent e) {
		if ("disable".equals(e.getActionCommand())) {
			mid.setEnabled(false);
			left.setEnabled(false);
			right.setEnabled(true);
		} else {
			mid.setEnabled(true);
			left.setEnabled(true);
			right.setEnabled(false);
		}
	}

	private static void createAndShowGui(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Buttons Example");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
/*
		JLabel label = new JLabel("HelloWorld");
		frame.getContentPane().add(label);*/

		AppInterface buttonContentPane = new AppInterface();
		buttonContentPane.setOpaque(true); // content panes must be opaque
		frame.getRootPane().setDefaultButton(left);
		frame.setContentPane(buttonContentPane);

		frame.pack();
		frame.setVisible(true);
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

	public static void main(String[] ergs){
		System.out.println("Started");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}
}