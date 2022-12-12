import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Duck{
	
	//add location attributes
	private int x,y; 	//position of the bird
	private int vx, vy; //for movement
	private Image img; 	
	private AffineTransform tx;

	public Duck() {
		img = getImage("/imgs/duck.gif"); //load the image for Duck
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				//initialize0 the location of the image
									//use your variables
		
		//give the duck a random non-zero velocity between -3 and 3
		// in both x and y direction
		int max = 3;
		int min = -3;
		
		vx = (int)(Math.random() * (max-min+1))+min;
		vy = (int)(Math.random() * (max-min+1))+min;
		
		//what happens if vx or vy were initialized to 0?
		//how to random their value?
		while(vx == 0) {
			//retry until value generated is not 0!
			vx = (int)(Math.random() * (max-min+1))+min;
		}
		
		//while loop says to keep trying until vy is non-zero
		while(vy == 0) {
			//retry until value generated is not 0!
			vy = (int)(Math.random() * (max-min+1))+min;
		}
	}
	
	//include a constructor that allows specifying the file name of
	//the image!
	public Duck(String fileName) {
		img = getImage("/imgs/" + fileName); //load the image for Duck
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				//initialize0 the location of the image
									//use your variables
	}
	
	
	
	
	
	public void changePicture(String newFileName) {
		img = getImage(newFileName);
		init(x, y);
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		if(this.x > 900) {
			x--;
		}
		x++;
		//call update to update the actual picture location
		update();
		g2.drawImage(img, tx, null);

	}
	
	/* update the picture variable location */
	private void update() {
		tx.setToTranslation(x, y);
		tx.scale(.5, .5);
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

}
