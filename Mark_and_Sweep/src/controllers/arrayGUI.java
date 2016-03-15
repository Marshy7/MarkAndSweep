package controllers;

/**
 * Author: Alan Marsh
 * Date: 07/03/16
 * Version: 2.4
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import models.blueFish;
import models.redFish;
import models.yellowFish;
import utils.compactPanel;
import utils.createFishPanel;
import utils.linkPanel;
import utils.markSweepPanel;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class arrayGUI extends JFrame {

	private JPanel contentPane, middlePanel, bottomPanel, viewCreateButtons, viewLinkButtons, viewMarkButtons, viewCompactButtons;
	JButton btnCreateObject, btnLinkObject, btnMarkSweep, btnCompactHeap;
	private createFishPanel createObject;
	private linkPanel linkObject;
	private markSweepPanel markObject;
	private compactPanel compactObject;
	private garbageCollector gc;
	private JTextField txtStartPos, txtLinkPos, txtRootNo;
	private int handlePoolSize;
	
	

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		
		garbageCollector gc = new garbageCollector();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					arrayGUI frame = new arrayGUI(gc);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public arrayGUI(garbageCollector gc) {
		
		this.setTitle("Mark and Sweep Simulator by Alan Marsh");
		
		this.gc = gc;
		handlePoolSize = gc.HANDLER_SIZE;
		int height = 550;
		int width = 850;
		createObject = new createFishPanel(gc, width, height);
		createObject.setBackground(Color.WHITE);
		linkObject = new linkPanel(gc, width, height);
		linkObject.setBackground(Color.WHITE);
		markObject = new markSweepPanel(gc, width, height);
		markObject.setBackground(Color.WHITE);
		compactObject = new compactPanel(gc, width, height);
		compactObject.setBackground(Color.WHITE);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 904, 710);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
				
		middlePanel = new JPanel();		
		middlePanel.setBackground(Color.WHITE);
		middlePanel.add(createObject.getPanel());
		middlePanel.add(linkObject.getPanel());
		middlePanel.add(markObject.getPanel());
		middlePanel.add(compactObject.getPanel());

		contentPane.add(middlePanel, BorderLayout.CENTER);		
		
		bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout(0, 0));
		
		viewCreateButtons = createButtonPanel();
		bottomPanel.add(viewCreateButtons, BorderLayout.NORTH);
		viewLinkButtons = linkButtonPanel();
		viewMarkButtons = markButtonPanel();
		viewCompactButtons = compactButtonPanel();
		
		JPanel mainButtons = new JPanel();
		mainButtons.setBackground(Color.WHITE);
		bottomPanel.add(mainButtons, BorderLayout.SOUTH);
		
		btnCreateObject = new JButton("Create Object");
		btnCreateObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeView(btnCreateObject);
			}
		});
		mainButtons.add(btnCreateObject);
		
		btnLinkObject = new JButton("Link Objects");
		btnLinkObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeView(btnLinkObject);
			}
		});
		mainButtons.add(btnLinkObject);
		
		btnMarkSweep = new JButton("Mark and Sweep");
		btnMarkSweep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeView(btnMarkSweep);
			}
		});
		mainButtons.add(btnMarkSweep);
		
		btnCompactHeap = new JButton("Compact Heap");
		btnCompactHeap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeView(btnCompactHeap);
			}
		});
		mainButtons.add(btnCompactHeap);
		
		changeView(btnCreateObject);
		

	}
	
	public JPanel createButtonPanel(){
		
		JPanel btnCreatePanel = new JPanel();
		btnCreatePanel.setBackground(Color.WHITE);
		
		JButton btnRedFish = new JButton("Add Red Fish");
		btnRedFish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc.addFish(new redFish());
				createObject.reDraw();
				changeView(btnCreateObject);
			}
		});
		btnCreatePanel.add(btnRedFish);
		
		JButton btnBlueFish = new JButton("Add Blue Fish");
		btnBlueFish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc.addFish(new blueFish());
				createObject.reDraw();
				changeView(btnCreateObject);
			}
		});
		btnCreatePanel.add(btnBlueFish);
		
		JButton btnYellow = new JButton("Add Yellow Fish");
		btnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc.addFish(new yellowFish());
				createObject.reDraw();
				changeView(btnCreateObject);
			}
		});
		btnCreatePanel.add(btnYellow);
		
		return btnCreatePanel;
		
	}
	
	public JPanel linkButtonPanel(){
		
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		
		JButton btnLink = new JButton("Link Fish");
		btnLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 
				int linkPos = Integer.parseInt(txtLinkPos.getText());
				int startPos = Integer.parseInt(txtStartPos.getText());
										
				if(startPos < handlePoolSize && linkPos < handlePoolSize){
					gc.linkFish(startPos, linkPos);
				}
				changeView(btnLinkObject);
				
			}
		});
		btnPanel.setLayout(new GridLayout(0, 4, 0, 0));
		btnPanel.add(btnLink);
		
		JButton btnUnlink = new JButton("Remove Link");
		btnUnlink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				int linkPos = Integer.parseInt(txtLinkPos.getText());
				
				if(txtStartPos.getText() == "red root" && gc.handlePool[linkPos].fishType.objectSize == 12){
					gc.redRoot = handlePoolSize;
				}
				else if(txtStartPos.getText() == "blue root" && gc.handlePool[linkPos].fishType.objectSize == 8){
					gc.blueRoot = handlePoolSize;
				}
				else if(txtStartPos.getText() == "yellow root" && gc.handlePool[linkPos].fishType.objectSize == 4){
					gc.yellowRoot = handlePoolSize;
				}
				else{
					int startPos = Integer.parseInt(txtStartPos.getText());
										
					if(startPos < handlePoolSize && linkPos < handlePoolSize)
					gc.removeLink(startPos, linkPos);
				}
				changeView(btnLinkObject);
			}
		});
		btnPanel.add(btnUnlink);
		
		txtStartPos = new JTextField();
		txtStartPos.setText("Starting Fish(0 - " + handlePoolSize + ")");
		btnPanel.add(txtStartPos);
		txtStartPos.setColumns(10);
		
		txtLinkPos = new JTextField();
		txtLinkPos.setText("Linked fish(0 - " + handlePoolSize + ")");
		btnPanel.add(txtLinkPos);
		txtLinkPos.setColumns(10);
		
		JLabel label = new JLabel("");
		btnPanel.add(label);
		
		JButton btnSetRoot = new JButton("Set Root");
		btnSetRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(gc.handlePool[Integer.parseInt(txtRootNo.getText())].fishType.objectSize == 12){
					gc.redRoot = Integer.parseInt(txtRootNo.getText());
				}
				else if(gc.handlePool[Integer.parseInt(txtRootNo.getText())].fishType.objectSize == 8){
					gc.blueRoot = Integer.parseInt(txtRootNo.getText());
				}
				else if(gc.handlePool[Integer.parseInt(txtRootNo.getText())].fishType.objectSize == 4){
					gc.yellowRoot = Integer.parseInt(txtRootNo.getText());
				}
				changeView(btnLinkObject);
				
			}
			
		});
		btnPanel.add(btnSetRoot);
		
		txtRootNo = new JTextField();
		txtRootNo.setText("Root No");
		btnPanel.add(txtRootNo);
		txtRootNo.setColumns(10);
		
		JLabel label_1 = new JLabel("");
		btnPanel.add(label_1);
		
		return btnPanel;
	}
	
	public JPanel markButtonPanel(){
		
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		
		JButton btnRedMark = new JButton("Mark Red Fish");
		btnRedMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc.markFish(gc.redRoot);
				changeView(btnMarkSweep);
			}			
		});
		btnPanel.add(btnRedMark);
		
		JButton btnBlueMark = new JButton("Mark Blue Fish");
		btnBlueMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc.markFish(gc.blueRoot);
				changeView(btnMarkSweep);
			}			
		});
		btnPanel.add(btnBlueMark);
		
		JButton btnYellowMark = new JButton("Mark Yellow Fish");
		btnYellowMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc.markFish(gc.yellowRoot);
				changeView(btnMarkSweep);
			}			
		});
		btnPanel.add(btnYellowMark);
		
		JButton btnSweep = new JButton("Sweep Fish");
		btnSweep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc.sweepFish();
				changeView(btnMarkSweep);
			}			
		});
		btnPanel.add(btnSweep);
		
		return btnPanel;
	}
	
	public JPanel compactButtonPanel(){
		
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		
		JButton btnCompact = new JButton("Compact Heap");
		btnCompact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc.compactFish();
				changeView(btnCompactHeap);
			}			
		});
		btnPanel.add(btnCompact);
		
		return btnPanel;
	}
	
	public void changeView(JButton btn){
		switch(btn.getText()){
		 	case "Create Object" :
		 		clearView();
		 		bottomPanel.add(viewCreateButtons, BorderLayout.NORTH);
		 		createObject.reDraw();
		 		createObject.setVisible(true);		 		
		       break;
		 	case "Link Objects" :
		 		clearView();
		 		bottomPanel.add(viewLinkButtons, BorderLayout.NORTH);
		 		linkObject.reDraw();
		 		linkObject.setVisible(true);
		       break;
		 	case "Mark and Sweep" :
		 		clearView();
		 		bottomPanel.add(viewMarkButtons, BorderLayout.NORTH);
		 		markObject.reDraw();
		 		markObject.setVisible(true);
		 		break;
		 	case "Compact Heap" :
		 		clearView();
		 		bottomPanel.add(viewCompactButtons, BorderLayout.NORTH);
		 		compactObject.reDraw();
		 		compactObject.setVisible(true);
		 		break;
		    default :
		    	clearView();
		    	bottomPanel.add(viewCreateButtons, BorderLayout.NORTH);
		    	createObject.setVisible(true);
			
		}
		middlePanel.setBackground(Color.WHITE);
		contentPane.revalidate();
		contentPane.repaint();
	}
	
	public void clearView(){
		createObject.setVisible(false);
		linkObject.setVisible(false);
		markObject.setVisible(false);
		compactObject.setVisible(false);
		
		bottomPanel.remove(viewCreateButtons);
		bottomPanel.remove(viewLinkButtons);
		bottomPanel.remove(viewMarkButtons);
		bottomPanel.remove(viewCompactButtons);
		
	}
	

	
	

}
