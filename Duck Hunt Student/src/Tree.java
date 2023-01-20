import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Tree {
	private int x, y; //position of the bird
	private Image img;
	private AffineTransform tx;
	public void changePicture(String newFileName) {
		img = getImage(newFileName); 
		init(0, 0);
	}
	
	public Tree(String fileName) {
		img = getImage(fileName);
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 
	}
	
	public Tree(String fileName, int x, int y) {
		img = getImage(fileName);
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, tx, null);

	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(.5, .5);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Tree.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
