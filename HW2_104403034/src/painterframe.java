// 周芷安   104403034  資管3b
import java.awt.BorderLayout; // 包含東西南北中   
import java.awt.GridLayout;   // rows & columns
import java.awt.Color;
import java.awt.event.ItemEvent;    //combobox & radiobutton 使用//
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;  //button 使用//  
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;   
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;    
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;

public class painterframe extends JFrame{
private final JPanel drawJPanel; //狀態列//  
private final JLabel name;  //繪圖工具列//  
private final JComboBox <String> toolComboBox;  //繪圖工具//
private static final String[] tool = {"筆刷","直線","橢圓","矩形","圓角矩形" };

private final JLabel Size;  //筆刷大小//
private final JRadioButton small;
private final JRadioButton medium;
private final JRadioButton large;
private final ButtonGroup sizeRadio; 
public int brushsize;

private final JCheckBox fill;//是否填滿
public boolean filled;

private final JLabel buttontools;//背景前景按鈕列//
private final JButton backgroundJButton;
private final JButton foregroundJButton;
private final JButton clearJButton;
private final JButton previousJButton;
public Color forecolor=Color.BLUE;
private Color backcolor=Color.YELLOW;

private final JLabel statusbar;//顯示座標的那一欄
private CanvasJPanel canvasJPanel;
private final JCheckBox eraser;
public boolean yeseraser;

public painterframe() {
	super("小畫家");
	statusbar = new JLabel("游標位置:(,)");
	add(statusbar,BorderLayout.SOUTH);
	
	canvasJPanel =new CanvasJPanel();
	canvasJPanel.setBackground(Color.WHITE);
	add(canvasJPanel,BorderLayout.CENTER);
	MouseHandler handlermouse=new MouseHandler();//處理滑鼠再畫部區的MouseHandler
	canvasJPanel.addMouseListener(handlermouse);
	canvasJPanel.addMouseMotionListener(handlermouse);
	
	name = new JLabel("[繪圖工具]");
	toolComboBox = new JComboBox<String>(tool);
	toolComboBox.addItemListener(
			new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if (event.getStateChange() == ItemEvent.SELECTED)
					{
		            	   JOptionPane.showMessageDialog(painterframe.this,"你選了:"+tool[toolComboBox.getSelectedIndex()]);		
		          canvasJPanel.setShape((toolComboBox.getSelectedIndex())+1);
						}	   
					}
			}
		);
	
	Size = new JLabel("[筆刷大小]");
	small =new JRadioButton("小",true);
	medium =new JRadioButton("中",false);
	large =new JRadioButton("大",false);
	
	sizeRadio = new ButtonGroup();
	sizeRadio.add(small);
	sizeRadio.add(medium);
	sizeRadio.add(large);
	
	String[] size={"小","中","大"};//把ItemListener 加入radiobutton
    small.addItemListener(new sizeButtonHandler(size[0]));
	medium.addItemListener(new sizeButtonHandler(size[1]));
	large.addItemListener(new sizeButtonHandler(size[2]));
	
	fill =new JCheckBox("填滿");
	fill.addItemListener(
			new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if(fill.isSelected())
					{
						System.out.println("填滿");
						filled=true;
					}
					else
					{
						System.out.println("取消填滿");
						filled=false;
					}
					canvasJPanel.setFilled(filled);
				}
			}
);
	eraser =new JCheckBox("橡皮擦");
	eraser.addItemListener(
			new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if (eraser.isSelected()) {
						System.out.println("選擇橡皮擦");
						yeseraser = true;}
					else
					{
						System.out.println("取消橡皮擦");
						yeseraser=false;
					}
					canvasJPanel.eraser(yeseraser);
				}
							}
	);
			
	buttontools= new JLabel("[工作區]");
	foregroundJButton=new JButton("前景色");
	foregroundJButton.setBackground(Color.BLACK);
	backgroundJButton= new JButton("背景色");
	backgroundJButton.setBackground(Color.WHITE);
	clearJButton= new JButton("清除畫面");
	previousJButton=new JButton("上ㄧ步");
	
	ButtonHandler handler= new ButtonHandler();
	foregroundJButton.addActionListener(handler);
	backgroundJButton.addActionListener(handler);
	clearJButton.addActionListener(handler);
	previousJButton.addActionListener(handler);

	drawJPanel=new JPanel();
	drawJPanel.setLayout(new GridLayout(13,1));//用gridlayout來13列一行

	drawJPanel.add(name);//一個一個丟進JPanel
	drawJPanel.add(toolComboBox);
	drawJPanel.add(Size);
	drawJPanel.add(small);
	drawJPanel.add(medium);
	drawJPanel.add(large);
	drawJPanel.add(fill);
	drawJPanel.add(buttontools);
	drawJPanel.add(foregroundJButton);
	drawJPanel.add(backgroundJButton);
	drawJPanel.add(clearJButton);
	drawJPanel.add(previousJButton);
	drawJPanel.add(eraser);

	add(drawJPanel,BorderLayout.WEST);//放在painterframe的左邊
}

private class sizeButtonHandler implements ItemListener{
	private String s;
	public sizeButtonHandler(String string)//大中小RadioButton的constructor
	{
	s=string;//可以丟一個string進去等等用
	}
	public void itemStateChanged(ItemEvent event){	
		if(small.isSelected() ||medium.isSelected()||large.isSelected())
		{
			//沒有這一行的話，程式會在自動點跑下面的東西，又在點選的時候在跑一次
			JOptionPane.showMessageDialog(painterframe.this,"你選了:"+s);
		}
		if(small.isSelected())
		{
			brushsize=1;
		}
		else if(medium.isSelected())
		{
			brushsize=2;
		}
		else if(large.isSelected())
		{
			brushsize=3;
		}
		canvasJPanel.setBrush(brushsize);
	}
}

private class ButtonHandler implements ActionListener{
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("前景色")){
			JOptionPane.showMessageDialog(painterframe.this,"你選了:"+event.getActionCommand());
					forecolor=JColorChooser.showDialog(painterframe.this,"前景色",forecolor);
					canvasJPanel.setForegroundColor(forecolor);
					foregroundJButton.setBackground(forecolor);
					if(forecolor==null)
					{
						forecolor=Color.BLACK;
					}
				}
				else if(event.getActionCommand().equals("背景色"))
				{
					JOptionPane.showMessageDialog(painterframe.this,"你選了:"+event.getActionCommand());
					backcolor=JColorChooser.showDialog(painterframe.this,"背景色",backcolor);
					backgroundJButton.setBackground(backcolor);
					if(backcolor==null)
					{
						backcolor=Color.WHITE;
						backgroundJButton.setBackground(backcolor);
					}
					canvasJPanel.setBackground(backcolor);
					canvasJPanel.setBackgroundColor(backcolor);
				}
				else if(event.getActionCommand().equals("清除畫面"))
				{
					int reply = JOptionPane.showConfirmDialog(painterframe.this,"確定清除嗎?","清除畫面", JOptionPane.YES_NO_OPTION);
				    if (reply == JOptionPane.YES_OPTION)
				    {
				    	backcolor=Color.WHITE;
				    	canvasJPanel.setBackground(backcolor);
				    	backgroundJButton.setBackground(backcolor);
				    	canvasJPanel.reset();
				    }
				}
				else if(event.getActionCommand().equals("上ㄧ步"))
				{
					int reply = JOptionPane.showConfirmDialog(painterframe.this,"真的要上ㄧ步?","上ㄧ步", JOptionPane.YES_NO_OPTION);
					
				    if (reply == JOptionPane.YES_OPTION)
				    {
				
				    	canvasJPanel.previousStep();
				    }
				}
			}
		}
	
private class MouseHandler extends MouseAdapter{
	public void mouseClicked(MouseEvent event)
	{
		statusbar.setText(String.format("游標位置:(%d,%d)",event.getX(),event.getY()));
	}

	public void mousePressed(MouseEvent event)
	{
		statusbar.setText(String.format("游標位置:(%d,%d)",event.getX(),event.getY()));
	}
	
	public void mouseReleased(MouseEvent event)
	{
		statusbar.setText(String.format("游標位置:(%d,%d)",event.getX(),event.getY()));
	}
	
	public void mouseEntered(MouseEvent event)
	{
		statusbar.setText(String.format("游標位置:(%d,%d)",event.getX(),event.getY()));
	}
	
	public void mouseExited(MouseEvent event)
	{
		statusbar.setText(String.format("游標在外面"));
	}
	
	public void mouseDragged(MouseEvent event)
	{
		statusbar.setText(String.format("游標位置:(%d,%d)",event.getX(),event.getY()));	
	}
	public void mouseMoved(MouseEvent event)
	{
		statusbar.setText(String.format("游標位置:(%d,%d)",event.getX(),event.getY()));
		}
	}
}


