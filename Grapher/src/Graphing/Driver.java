package Graphing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {
		
		MySimpleGrapher panel = new MySimpleGrapher(600, 600, Color.CYAN);
		
		/*You don't really need to know what all of this does.
		* In summary, it puts the panel you made into a frame,
		* then opens that frame. 
		*/
		JFrame frame = new JFrame();   // the program itself
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);  
		frame.add(panel.ButtonPanel(), BorderLayout.NORTH);
		frame.pack(); //size it based on panel size
		frame.setVisible(true); 

	}

}
