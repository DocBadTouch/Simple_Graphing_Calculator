package Graphing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;



public class MySimpleGrapher extends JPanel implements Runnable, MouseInputListener, KeyListener {
	public CoordAdapter CoordAdapter = new CoordAdapter();
	public JTextField Input = new JTextField();
	public JTextField FxInput = new JTextField();
	public JButton Enter = new JButton("Enter");
	public JButton Plot = new JButton("Plot");
	public JButton ScaleXPos = new JButton("X +");
	public JButton ScaleXNeg = new JButton("X -");
	
	public ButtonGroup Functions = new ButtonGroup();
	public JRadioButton SinFx, PolyFx, CosFx;
	public double Fx = 0;
	public int ValX = 0;
	public boolean plot;
	public JLabel FxLabel = new JLabel("Function(X) :  ", JLabel.CENTER);
	public JLabel inputLabel = new JLabel("Input of X :  ", JLabel.CENTER);
	public JLabel Output = new JLabel("Output Y :  ", JLabel.CENTER);
	public double ScaleX = 1;
	public double ScaleY = 1;
	public MySimpleGrapher(int width, int height, Color bgColor) {
	setPreferredSize(new Dimension(width, height));
	setBackground(bgColor); //Sets the background color of the panel
	
	this.addMouseListener(this);
	this.addMouseMotionListener(this);
	
	//Starts the "runnable" thread (invoking method "run")
	Thread thr = new Thread(this);
	thr.start();
	
	int rectWidth =width/4;
	int rectHeight=rectWidth/2;
	}
	/*
	 * Function that creates a Panel of Buttons to be used in an interface.
	 */
	public JPanel ButtonPanel() {
		JPanel ButPan = new JPanel(new GridLayout(3,5));
		ButtonListener listener = new ButtonListener();
		CosFx = new JRadioButton("20Cos(X)-42");
		SinFx = new JRadioButton("20Sin(X) + 25");
		PolyFx = new JRadioButton("5x^2 - 7x + 3");
		JLabel Blank = new JLabel("");
		JLabel Blank2 = new JLabel("");
		Functions.add(CosFx);
		Functions.add(SinFx);
		Functions.add(PolyFx);
		
		Enter.addActionListener(listener);
		Plot.addActionListener(listener);
		ScaleXPos.addActionListener(listener);
		ScaleXNeg.addActionListener(listener);
		ButPan.add(inputLabel);
		ButPan.add(Input);
		ButPan.add(Blank);
		ButPan.add(Enter);
		ButPan.add(Plot);
		ButPan.add(FxLabel);
		ButPan.add(CosFx);
		ButPan.add(SinFx);
		ButPan.add(PolyFx);
		ButPan.add(ScaleXPos);
		ButPan.add(ScaleXNeg);
		ButPan.add(Output);
		ButPan.add(Blank);
		ButPan.add(Blank);
		return ButPan;
	}
	class ButtonListener implements ActionListener {

	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	String command = "";
	    	if(e.getSource() instanceof JButton) { //check if clicks are inside buttons
	    		JButton butString = (JButton) e.getSource();//assign which button was clicked
	    		command = butString.getText();//save string value of button
	    		switch(command) {
		    	case("Enter"):
		    		ValX = Integer.parseInt(Input.getText());
		    		
		    		if(CosFx.isSelected()) {
		    			Fx = 20*Math.cos(ValX)-42;
		    			
		    		}else if(SinFx.isSelected()) {
		    			Fx = 20*Math.sin(ValX)+25;
		    		}
		    		else Fx = 5*Math.pow(ValX, 2)-(7*ValX) + 3;
		    		Output.setText("Output Y :  " + Fx);
		   			break;
		   		case("Plot"):
		   			
		   			plot = true;
	    			break;
		   		case("X +"):
		   			
		   			ScaleX = (ScaleX<1) ? ScaleX/.9:ScaleX+1;
		   			System.out.println(ScaleX);
	    			break;
		   		case("X -"):
		   			
		   			ScaleX = (ScaleX <=1) ?  ScaleX*.9:ScaleX-1;
		   			System.out.println(ScaleX);
	    			break;
	    			}
	    		}
	    	}
	    }
	@Override
	protected void paintComponent(Graphics graphicHelper) {

		super.paintComponent(graphicHelper);
		double scale = 100;
		double shift = (ValX*ScaleX*3+getWidth()/2)-5; //amount to shift box that represents the point chosen.
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		Graphics2D g = (Graphics2D)graphicHelper;
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(6));
		g.drawLine(getWidth()/2, getHeight(), getWidth()/2, 0); //Draw Vertical Line for Classic Cross for Graph
		g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);//Draw Horizontal Line for Classic Cross for Graph
		for(int i = 0; i<=10; i++) {
			g.drawLine(getWidth()/20*i, getHeight()/2-5, getWidth()/20*i, getHeight()/2+5); //Draw neg X tick marks
			g.drawLine(getWidth()/20*i+getWidth()/2, getHeight()/2-5, (getWidth()/20)*i+(getWidth()/2), getHeight()/2+5); //Draw pos X tick marks
			g.drawLine(getWidth()/2-5, getHeight()/20*i, getWidth()/2+5,getHeight()/20*i); //Draw neg Y tick marks
			g.drawLine(getWidth()/2-5,getHeight()/20*i+getHeight()/2, getWidth()/2+5, (getHeight()/20)*i+(getHeight()/2)); //Draw pos Y tick marks
		}
		g.setFont(new Font("serif", Font.BOLD, 20));
		g.drawString("-"+df.format(scale/ScaleX), 0, getHeight()/2+25); //draws scales
		g.drawString("  "+df.format(scale/ScaleX), getWidth()-50, getHeight()/2+25);
		g.drawString("  -"+df.format(scale/ScaleY), getWidth()/2-80, getHeight());
		g.drawString("  "+df.format(scale/ScaleY), getWidth()/2-80, 20);
		g.drawRect( (int)shift, CoordAdapter.genBackY((int) Fx,getHeight())-5, 10, 10); // draws marker for plotted point 
		
		if (plot) {
			drawGraph(g); //plots full function if selected
		}
	}

	private void drawGraph(Graphics2D g) {
		int xp,y,y2;
		double x,x2;
		if(CosFx.isSelected()) { //plots 4 Cos()x)-42
			for(int i = 1; i<getWidth()/3; i++) { //1-200
				x =  ((i-(getWidth()/6)-1)/ScaleX);
				xp = ((3*i-3));
				y =  (int) (CoordAdapter.genBackY((int) (20*Math.cos(x)-42),getHeight())*ScaleY);
				x2 = ((i-(getWidth()/6))/ScaleX);
				y2 = (int) (CoordAdapter.genBackY((int) (20*Math.cos(x2))-42,getHeight())*ScaleY);
				//System.out.println("x: " + x + "   y: " + y + "    x2: " + x2 + "   y2: " + y2);
				g.drawLine(xp, y, (3*i), y2);
			}
			//Fx = 2*Math.cos(ValX)-42;
			
		}else if(SinFx.isSelected()) { //plots 4 Sin(x) +25
			for(int i = 1; i<getWidth()/3; i++) {
				x =  ((i-(getWidth()/6)-1)/ScaleX);
				xp = (3*i-3);
				y = (int) (CoordAdapter.genBackY((int) (20*Math.sin(x)+25),getHeight())*ScaleY);
				x2 = ((i-(getWidth()/6))/ScaleX);
				y2 = (int) (CoordAdapter.genBackY((int) (20*Math.sin(x2)+25),getHeight())*ScaleY);
				g.drawLine(xp, y,3*i, y2);
			}
			//Fx = 4*Math.sin(ValX)+25;
		}
		else 
			for(int i = 1; i<getWidth(); i++) { //plots 5x^2-7x+3
				x =  ((i-(getWidth()/6)-1)/ScaleX);
				xp = (3*i-3);
				y = (int) (CoordAdapter.genBackY((int) (5*Math.pow(x,2)-(7*x)+3),getHeight())*ScaleY);
				x2 = ((i-(getWidth()/6))/ScaleX);
				y2 = (int) (CoordAdapter.genBackY((int) (5*Math.pow(x2,2)-(7*x2)+3),getHeight())*ScaleY);
				g.drawLine(xp, y, 3*i, y2);
			}//Fx = 5*Math.pow(ValX, 2)-(7*ValX) + 3;
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis(); //time last frame was drawn
		while(true) { //repeat forever
	        //Repeat 30 times per second 
	        long requiredTime=1000/30;
	        long deltaTime=System.currentTimeMillis() - startTime;
	        if(deltaTime > requiredTime) {
		        repaint();
	        	startTime=System.currentTimeMillis();
	        }else {
	        	try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
        }
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
