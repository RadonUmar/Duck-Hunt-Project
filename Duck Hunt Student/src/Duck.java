import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
public class Duck{
	private Image img; 	
	private AffineTransform tx;
	private int x, y; //position of the bird
	private int vx, vy; //for movement
	private boolean shot = false;;
	private int difficulty = 0;
	private boolean astronaut = false;
	
	public Duck() {
		img = getImage("/Alien/alien-static.png"); //load the image for Tree
		
		difficulty = 5;
		
		x = (int)(Math.random() * 401) + 100;
		y = (int)(Math.random() * 401) + 100;
		
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				//initialize the location of the image
									//use your variables
	}
	
	public void changePicture(String newFileName) {
		img = getImage(newFileName);
		init(x, y);
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		if (!shot && !astronaut) {
			
			// Sporatic Movement
			/*
			vy = difficulty * (2 * ((int) (Math.random() * 2)) - 1);
			vx = difficulty * (2 * ((int) (Math.random() * 2)) - 1);
			*/
			
			
			// Smooth Movement
			if(vx > difficulty) {
				vx = difficulty;
			}else {
				if(vx < -difficulty) {
					vx = -difficulty;
				}else {
					vx += (int) (Math.random() * difficulty * 2) - difficulty - 1;
				}
			}
			if(vy > difficulty) {
				vy = difficulty;
			}else {
				if(vy < -difficulty) {
					vy = -difficulty;
				}else {
					vy += (int) (Math.random() * difficulty * 2) - difficulty - 1;
				}
			}
			
			// Boundaries
			if((x < 0 && vx < 0) || (x > 675 && vx > 0)) {
				vx *= -1;
			}
			if((y < 0 && vy < 0) || (y > 545 && vy > 0)) {
				vy *= -1;
			}
			
		} else if (shot && !astronaut) {
			vx = 0;
			vy = 10;
			img = getImage("/Alien/shot-alien-static.png");
		}
		
		x += vx;
		y += vy;
		update();
		
		g2.drawImage(img, tx, null);
	}
	
	public void update() {
		tx.setToTranslation(x, y);
		tx.scale(0.5, 0.5);
	}
	
	//include a constructor that allows for specifying the file name of the image
	public Duck(String fileName) {
		if (fileName.indexOf("Astronaut") != -1 || fileName.indexOf("astronaut") != -1) {
			astronaut = true;
		}
		
		img = getImage(fileName);
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	public Duck(String fileName, int x, int y) {
		if (fileName.indexOf("Astronaut") != -1 || fileName.indexOf("astronaut") != -1) {
			astronaut = true;
		}
		
		img = getImage(fileName);
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(.5, .5);
	}
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Duck.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}


	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setVX(int vx) {
		this.vx = vx;
	}
	
	public void setVY(int vy) {
		this.vy = vy;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isShot() {
		return shot;
	}
	
	public void setShot(boolean shot) {
		this.shot = shot;
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	
}
