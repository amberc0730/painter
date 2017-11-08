// �P��w   104403034  ���3b
import java.awt.BorderLayout; // �]�t�F��n�_��   
import java.awt.GridLayout;   // rows & columns
import java.awt.Color;
import java.awt.event.ItemEvent;    //combobox & radiobutton �ϥ�//
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;  //button �ϥ�//  
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
private final JPanel drawJPanel; //���A�C//  
private final JLabel name;  //ø�Ϥu��C//  
private final JComboBox <String> toolComboBox;  //ø�Ϥu��//
private static final String[] tool = {"����","���u","���","�x��","�ꨤ�x��" };

private final JLabel Size;  //����j�p//
private final JRadioButton small;
private final JRadioButton medium;
private final JRadioButton large;
private final ButtonGroup sizeRadio; 
public int brushsize;

private final JCheckBox fill;//�O�_��
public boolean filled;

private final JLabel buttontools;//�I���e�����s�C//
private final JButton backgroundJButton;
private final JButton foregroundJButton;
private final JButton clearJButton;
private final JButton previousJButton;
public Color forecolor=Color.BLUE;
private Color backcolor=Color.YELLOW;

private final JLabel statusbar;//��ܮy�Ъ����@��
private CanvasJPanel canvasJPanel;
private final JCheckBox eraser;
public boolean yeseraser;

public painterframe() {
	super("�p�e�a");
	statusbar = new JLabel("��Ц�m:(,)");
	add(statusbar,BorderLayout.SOUTH);
	
	canvasJPanel =new CanvasJPanel();
	canvasJPanel.setBackground(Color.WHITE);
	add(canvasJPanel,BorderLayout.CENTER);
	MouseHandler handlermouse=new MouseHandler();//�B�z�ƹ��A�e���Ϫ�MouseHandler
	canvasJPanel.addMouseListener(handlermouse);
	canvasJPanel.addMouseMotionListener(handlermouse);
	
	name = new JLabel("[ø�Ϥu��]");
	toolComboBox = new JComboBox<String>(tool);
	toolComboBox.addItemListener(
			new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if (event.getStateChange() == ItemEvent.SELECTED)
					{
		            	   JOptionPane.showMessageDialog(painterframe.this,"�A��F:"+tool[toolComboBox.getSelectedIndex()]);		
		          canvasJPanel.setShape((toolComboBox.getSelectedIndex())+1);
						}	   
					}
			}
		);
	
	Size = new JLabel("[����j�p]");
	small =new JRadioButton("�p",true);
	medium =new JRadioButton("��",false);
	large =new JRadioButton("�j",false);
	
	sizeRadio = new ButtonGroup();
	sizeRadio.add(small);
	sizeRadio.add(medium);
	sizeRadio.add(large);
	
	String[] size={"�p","��","�j"};//��ItemListener �[�Jradiobutton
    small.addItemListener(new sizeButtonHandler(size[0]));
	medium.addItemListener(new sizeButtonHandler(size[1]));
	large.addItemListener(new sizeButtonHandler(size[2]));
	
	fill =new JCheckBox("��");
	fill.addItemListener(
			new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if(fill.isSelected())
					{
						System.out.println("��");
						filled=true;
					}
					else
					{
						System.out.println("������");
						filled=false;
					}
					canvasJPanel.setFilled(filled);
				}
			}
);
	eraser =new JCheckBox("�����");
	eraser.addItemListener(
			new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if (eraser.isSelected()) {
						System.out.println("��ܾ����");
						yeseraser = true;}
					else
					{
						System.out.println("���������");
						yeseraser=false;
					}
					canvasJPanel.eraser(yeseraser);
				}
							}
	);
			
	buttontools= new JLabel("[�u�@��]");
	foregroundJButton=new JButton("�e����");
	foregroundJButton.setBackground(Color.BLACK);
	backgroundJButton= new JButton("�I����");
	backgroundJButton.setBackground(Color.WHITE);
	clearJButton= new JButton("�M���e��");
	previousJButton=new JButton("�W���B");
	
	ButtonHandler handler= new ButtonHandler();
	foregroundJButton.addActionListener(handler);
	backgroundJButton.addActionListener(handler);
	clearJButton.addActionListener(handler);
	previousJButton.addActionListener(handler);

	drawJPanel=new JPanel();
	drawJPanel.setLayout(new GridLayout(13,1));//��gridlayout��13�C�@��

	drawJPanel.add(name);//�@�Ӥ@�ӥ�iJPanel
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

	add(drawJPanel,BorderLayout.WEST);//��bpainterframe������
}

private class sizeButtonHandler implements ItemListener{
	private String s;
	public sizeButtonHandler(String string)//�j���pRadioButton��constructor
	{
	s=string;//�i�H��@��string�i�h������
	}
	public void itemStateChanged(ItemEvent event){	
		if(small.isSelected() ||medium.isSelected()||large.isSelected())
		{
			//�S���o�@�檺�ܡA�{���|�b�۰��I�]�U�����F��A�S�b�I�諸�ɭԦb�]�@��
			JOptionPane.showMessageDialog(painterframe.this,"�A��F:"+s);
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
		if (event.getActionCommand().equals("�e����")){
			JOptionPane.showMessageDialog(painterframe.this,"�A��F:"+event.getActionCommand());
					forecolor=JColorChooser.showDialog(painterframe.this,"�e����",forecolor);
					canvasJPanel.setForegroundColor(forecolor);
					foregroundJButton.setBackground(forecolor);
					if(forecolor==null)
					{
						forecolor=Color.BLACK;
					}
				}
				else if(event.getActionCommand().equals("�I����"))
				{
					JOptionPane.showMessageDialog(painterframe.this,"�A��F:"+event.getActionCommand());
					backcolor=JColorChooser.showDialog(painterframe.this,"�I����",backcolor);
					backgroundJButton.setBackground(backcolor);
					if(backcolor==null)
					{
						backcolor=Color.WHITE;
						backgroundJButton.setBackground(backcolor);
					}
					canvasJPanel.setBackground(backcolor);
					canvasJPanel.setBackgroundColor(backcolor);
				}
				else if(event.getActionCommand().equals("�M���e��"))
				{
					int reply = JOptionPane.showConfirmDialog(painterframe.this,"�T�w�M����?","�M���e��", JOptionPane.YES_NO_OPTION);
				    if (reply == JOptionPane.YES_OPTION)
				    {
				    	backcolor=Color.WHITE;
				    	canvasJPanel.setBackground(backcolor);
				    	backgroundJButton.setBackground(backcolor);
				    	canvasJPanel.reset();
				    }
				}
				else if(event.getActionCommand().equals("�W���B"))
				{
					int reply = JOptionPane.showConfirmDialog(painterframe.this,"�u���n�W���B?","�W���B", JOptionPane.YES_NO_OPTION);
					
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
		statusbar.setText(String.format("��Ц�m:(%d,%d)",event.getX(),event.getY()));
	}

	public void mousePressed(MouseEvent event)
	{
		statusbar.setText(String.format("��Ц�m:(%d,%d)",event.getX(),event.getY()));
	}
	
	public void mouseReleased(MouseEvent event)
	{
		statusbar.setText(String.format("��Ц�m:(%d,%d)",event.getX(),event.getY()));
	}
	
	public void mouseEntered(MouseEvent event)
	{
		statusbar.setText(String.format("��Ц�m:(%d,%d)",event.getX(),event.getY()));
	}
	
	public void mouseExited(MouseEvent event)
	{
		statusbar.setText(String.format("��Цb�~��"));
	}
	
	public void mouseDragged(MouseEvent event)
	{
		statusbar.setText(String.format("��Ц�m:(%d,%d)",event.getX(),event.getY()));	
	}
	public void mouseMoved(MouseEvent event)
	{
		statusbar.setText(String.format("��Ц�m:(%d,%d)",event.getX(),event.getY()));
		}
	}
}


