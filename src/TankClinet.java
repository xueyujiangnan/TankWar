import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class TankClinet extends Frame {
	public static final int GAMEWIDTH = 800;
	public static final int GAMEHEIGHT= 600;
	
	Tank myTank = new Tank(50,50);
	Missile m = new Missile(50, 50, Tank.Direction.R);
	Image offScreenImg = null;
	/**
	 * 重画时自己的调用
	 */
	public void paint(Graphics g) {
		m.draw(g);
		myTank.draw(g);
	}
	public void update(Graphics g) {
		if(offScreenImg == null){
			offScreenImg = this.createImage(GAMEWIDTH, GAMEHEIGHT);
		}
		Graphics goffScreen = offScreenImg.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.green);
		goffScreen.fillRect(0, 0, GAMEWIDTH, GAMEHEIGHT);
		goffScreen.setColor(c);
		
		paint(goffScreen);
		g.drawImage(offScreenImg,0,0,null);
	}
	public void lauchFrame(){
		this.setLocation(400, 300);
		this.setSize(GAMEWIDTH,GAMEHEIGHT);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setResizable(false);
		this.setTitle("TankWar");
		this.setBackground(Color.green);
		this.addKeyListener(new KeyMoniter());
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	public static void main(String[] args) {
		TankClinet TC = new TankClinet();
		TC.lauchFrame();
	}
	private class PaintThread implements Runnable{
		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	private class KeyMoniter extends KeyAdapter{
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.KeyPressed(e);
		}
		
	}
}
