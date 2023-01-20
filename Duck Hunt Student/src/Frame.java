
import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
	Duck d = new Duck();
	Duck d2 = new Duck();
	boolean d2Exists = false;
	Tree background = new Tree("/Background/background-static-drawing-1600x1600.png");
	Tree crosshair = new Tree("/Crosshair/crosshair-384x384.png");
	Tree foreground = new Tree("/Foreground/duckhunt-foreground.png");
	Duck astronaut = new Duck("/Astronaut/astronaut-holding-alien-400x400.png");
	int shots = 0;
	int round = 1;
	int roundsPlayed = 1;
	boolean failure = false;
	int numTimesFail = 0;
	long failTime = 0;
	long roundStartTime = 0;
	boolean failAnimationDone = true;
	boolean roundStartAnimationDone = false;
	int numsTimesRoundStart = 0;
	int score = 0;
	int timesThudPlayed = 0;
	int timesThudPlayed2 = 0;
	static boolean gameOver = false;
	static int classScore = 0;
	static int classRound = 1;
	
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		background.paint(g);
		classScore = this.score;
		classRound = this.round;
		
		if (gameOver) {
			d.setXY(1200, 1200);
			d2.setXY(1200, 1200);
			d.paint(g);
			d2.paint(g);
			crosshair.setXY(1200, 1200);
			
			Font myFont = new Font ("Courier New", 1, 50);
			g.setColor(Color.WHITE);
			g.setFont(myFont);
			classScore = score + 1000 * round;
			g.drawString("Game Over", 255, 325);
			g.drawString("Final Score: " + classScore, 125, 375);
			g.setColor(Color.YELLOW);
			if (classScore == 26000) {
				g.drawString("P e r f e c t   G a m e", 50, 425);
			} else if (classScore > 26000 || classScore < 1000) {
				g.drawString("     C h e a t e r     ", 50, 425);
			}
		}
		
		if (roundStartAnimationDone && !gameOver) {
			d.paint(g);
			if (d2Exists) {
				d2.paint(g);
			} else {
				d2.setShot(true);
			}
		
			if (shots > 2) {
				failure = true;
			}
		
			if (failure && numTimesFail == 0) {
				failure();
			} else if (failure && !failAnimationDone) {
			astronaut.setXY(300, astronaut.getY());
			astronaut.setVY(-5);
			if (System.nanoTime() - failTime >= 1000000000) {
				astronaut.setVY(5);
			}
			if (System.nanoTime() - failTime >= 2000000000) {
				failAnimationDone = true;
			}
			astronaut.paint(g);
			}
			
			Point p = MouseInfo.getPointerInfo().getLocation();
			int x = p.x - 96;
			int y = p.y - 120;
			crosshair = new Tree("/Crosshair/crosshair-384x384.png", x, y);
			crosshair.paint(g);
			
			if (numTimesFail != 0 && failAnimationDone) {
				reset();
			}
			if (d.isShot() && d.getY() >= 900) {
				if (timesThudPlayed == 0) {
					timesThudPlayed++;
					Music m = setMusic("DuckHunt-Thud.wav", false);
					m.play();
				}
				if (d2Exists) {
					if (d2.getY() >= 900 && d2.isShot() && timesThudPlayed2 == 0) {
					if (timesThudPlayed2 == 0) {	
							timesThudPlayed2++;
							Music m2 = setMusic("DuckHunt-Thud.wav", false);
							m2.play();
					}
						reset();
					}
				} else {
					reset();
				}
			}
		} else if (!roundStartAnimationDone && !gameOver) {
			if (numsTimesRoundStart == 0) {
				roundStart();
			}
			astronaut.setXY(300, astronaut.getY());
			astronaut.setVY(-5);
			if (System.nanoTime() - roundStartTime >= 1000000000) {
				astronaut.setVY(5);
			}
			if (System.nanoTime() - roundStartTime >= 2000000000) {
				roundStartAnimationDone = true;
			}
			astronaut.paint(g);
		}
		
		foreground.paint(g);
		
		if (!gameOver) {		
			Font myFont = new Font ("Courier New", 1, 50);
			g.setColor(Color.WHITE);
			g.setFont(myFont);
			g.drawString("Round: " + round, 10, 650);
			g.drawString("Score: " + score, 10, 700);
			g.drawString("Bullets: " + (3 - shots), 10, 750);
		}
	}
	
	public static void main(String[] arg) {
		Frame f = new Frame();
		
	}
	
	public Frame() {
		if ((int)(Math.random() * 11) >= 7) {
			d2Exists = true;
		}
		
		JFrame f = new JFrame("Duck Hunt");
		f.setSize(new Dimension(800, 800));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (!failure && roundStartAnimationDone && failAnimationDone) {
			
			Music m = setMusic("DuckHunt-Bang.wav", false);
			m.play();
			shots++;
			
			Point p = MouseInfo.getPointerInfo().getLocation();
			int x = p.x - 40;
			int y = p.y - 60;
			if (!d.isShot()) {
				if (x >= d.getX() + 10 && x <= d.getX() + 150) {
					if (y >= d.getY() + 50 && y <= d.getY() + 225) {
						d.setShot(true);
						shots--;
						score += 100 * (4 - shots) * (4 - shots);
						if (shots < 0) {
							shots = 0;
						}
					}
				}
			}
			if (!d2.isShot()) {
				if (x >= d2.getX() + 10 && x <= d2.getX() + 150) {
					if (y >= d2.getY() + 50 && y <= d2.getY() + 225) {
						d2.setShot(true);
						shots--;
						score += 100 * (4 - shots) * (4 - shots);
						if (shots < 0) {
							shots = 0;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void failure() {
		
		failTime = System.nanoTime();
		failAnimationDone = false;
		astronaut = new Duck("/Astronaut/astronaut-got-shot-400x400.png");
		astronaut.setXY(300, 650);
		numTimesFail++;
		Music m = setMusic("DuckHunt-Haha.wav", false);
		m.play();
		
	}
	
	public void roundStart() {
		
		roundStartTime = System.nanoTime();
		roundStartAnimationDone = false;
		astronaut = new Duck("/Astronaut/astronaut-holding-alien-400x400.png");
		astronaut.setXY(300, 650);
		numsTimesRoundStart++;
		
	}
	
	public void reset() {
		if (shots < 3 && d.isShot() && d2.isShot()) {
			round++;
		}
		roundStartAnimationDone = false;
		failAnimationDone = true;
		failTime = 0;
		roundStartTime = 0;
		numsTimesRoundStart = 0;
		numTimesFail = 0;
		timesThudPlayed = 0;
		timesThudPlayed2 = 0;
		
		d = new Duck();
		d2 = new Duck();
		d2Exists = false;
		if ((int)(Math.random() * 11) >= 7) {
			d2Exists = true;
		}
		
		background = new Tree("/Background/background-static-drawing-1600x1600.png");
		crosshair = new Tree("/Crosshair/crosshair-384x384.png");
		foreground = new Tree("/Foreground/duckhunt-foreground.png");
		
		failure = false;
		shots = 0;
		
		d.setDifficulty(5 + round * 2);
		d2.setDifficulty(5 + round * 2);
		
		classScore = this.score;
		classRound = this.round;
		
		if (roundsPlayed >= 10) {
			gameOver = true;
		}

		roundsPlayed++;
	}
	
	public Music setMusic(String fileName, boolean loop) {
		Music m = null;
		try {
			Path folder = FileSystems.getDefault().getPath(new String("./")).toAbsolutePath().getParent();
			String musicPath = folder + "/src/SFX/" + fileName;
			m = new Music(musicPath, loop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}
	
	
}
