import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.*;
import java.net.URL;

@SuppressWarnings("serial")
public class Main extends JPanel implements MouseListener, ActionListener {
	private int rectX, rectY, score,  lives;
	private double rxpm, rypm;
	private Timer tm;
	private JLabel scoreLabel, livesLabel;
	private JFrame f;
	private BufferedImage image;
	
	public void drawHeart(Graphics g, int x, int y, int width, int height) {
	    int[] triangleX = {
	            x - 2*width/18,
	            x + width + 2*width/18,
	            (x - 2*width/18 + x + width + 2*width/18)/2};
	    int[] triangleY = { 
	            y + height - 2*height/3, 
	            y + height - 2*height/3, 
	            y + height };
	    g.fillOval(
	            x - width/12,
	            y, 
	            width/2 + width/6, 
	            height/2); 
	    g.fillOval(
	            x + width/2 - width/12,
	            y,
	            width/2 + width/6,
	            height/2);
	    g.fillPolygon(triangleX, triangleY, triangleX.length);
	}

	public Main() {
		f = new JFrame("Clickt the Square!");
		
		addMouseListener(this); // Add Mouse Listener

		// Initialize Class Level Variables
		rectX = 0; rectY = 0;
		rxpm = 1; rypm = 1;
		score = 0; lives = 10;
		
		// Create an Interval for the Animation
		tm = new Timer(10, this);
		tm.start();
		
		// Set the Score Label
		scoreLabel = new JLabel("Score: " + score);
		livesLabel = new JLabel("Lives: " + lives);
		this.add(scoreLabel);
		this.add(livesLabel);
		
		// Get the Clown Image
		URL resource = getClass().getResource("clown.png");
		try {
			image = ImageIO.read(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, rectX, rectY, 50, 50, this);
		
		g.setColor(Color.RED);
		for (int i = 50; i < ((lives * 50) + 50); i += 50) {
			this.drawHeart(g, i, 50, 25, 25);
		}
	}

	public void actionPerformed(ActionEvent e) {
		rectX += rxpm; rectY += rypm;

		int panelWidth = 600;
		int panelHeight = 400;

		if (rectX <= 0 || rectX >= panelWidth - 60) {
			rxpm *= -1;
		}

		if (rectY <= 0 || rectY >= panelHeight - 85) {
			rypm *= -1;
		}

		repaint();
	}

	public void mouseClicked(MouseEvent evt) {
	    int mouseX = evt.getX();
	    int mouseY = evt.getY();
	    
	    // Check if the mouse click was inside the rectangle
	    if (mouseX >= rectX && mouseX <= rectX + 50 && mouseY >= rectY && mouseY <= rectY + 50) {
	        int randomNum = new Random().ints(1, 0, 2).findFirst().getAsInt();
	        
	        if (randomNum == 0) this.rxpm *= -1;
	        else this.rypm *= -1;
	        
	        rxpm = (rxpm < 0) ? rxpm - 0.35 : rxpm + 0.35; rypm = (rypm < 0 ? rypm - 0.35 : rypm + 0.35); score++;
	        scoreLabel.setText("Score: " + score);
	    } else {
	    	lives--;
	    	livesLabel.setText("Lives: " + lives);
	    	repaint();
	    	
	    	if (lives == 0) {
	    		tm.stop();
	    		int input = JOptionPane.showOptionDialog(null, "You Lost All Your Lives! Final Score: " + score, "Game Over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
	    		
	    		if (input == JOptionPane.OK_OPTION || input == JOptionPane.CANCEL_OPTION) {
	    			System.exit(0);
	    		}
	    	}
	    }
	}
	
	public void mousePressed(MouseEvent evt) {
		
	}
	public void mouseReleased(MouseEvent e) {
		
	}	
	public void mouseEntered(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
		
	}
	
	public void setupWindow(Main m) {		
		f.setSize(600, 400);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(m);
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		Main game = new Main();
		game.setupWindow(game);
	}
}