import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class Background {
  	private Image bgImage;
  	private int bgImageWidth;      		// width of the background (>= panel Width)

	private Image menuBg;
	private Image menuBgLvl2;
	private int menuBgWidth;

	private JFrame window;
	private Dimension dimension;

 	private int bgX;
	private int backgroundX;
	private int backgroundX2;
	private int bgDX;			// size of the background move (in pixels)


  public Background(JFrame window, String imageFile, int bgDX) {
    
	this.window = window;
    	this.bgImage = loadImage(imageFile);
    	bgImageWidth = bgImage.getWidth(null);	// get width of the background
		this.menuBg = loadImage("images/homeScreenLevel1.png");
		this.menuBgLvl2 = loadImage("images/homeScreenLevel2.png");
    

		


	dimension = window.getSize();

	if (bgImageWidth < dimension.width)
      		System.out.println("Background width < panel width");

    	this.bgDX = bgDX;

  }


  public void moveRight() {

	if (bgX == 0) {
		backgroundX = 0;
		backgroundX2 = bgImageWidth;			
	}

	bgX = bgX - bgDX;

	backgroundX = backgroundX - bgDX;
	backgroundX2 = backgroundX2 - bgDX;

	String mess = "Right: bgX=" + bgX + " bgX1=" + backgroundX + " bgX2=" + backgroundX2;
	//System.out.println (mess);

	if ((bgX + bgImageWidth) % bgImageWidth == 0) {
		System.out.println ("Background change: bgX = " + bgX); 
		backgroundX = 0;
		backgroundX2 = bgImageWidth;
	}

  }


  public void moveLeft() {

	if (bgX == 0) {
		backgroundX = bgImageWidth * -1;
		backgroundX2 = 0;			
	}

	bgX = bgX + bgDX;
				
	backgroundX = backgroundX + bgDX;	
	backgroundX2 = backgroundX2 + bgDX;

	String mess = "Left: bgX=" + bgX + " bgX1=" + backgroundX + " bgX2=" + backgroundX2;
	//System.out.println (mess);

	if ((bgX + bgImageWidth) % bgImageWidth == 0) {
		System.out.println ("Background change: bgX = " + bgX); 
		backgroundX = bgImageWidth * -1;
		backgroundX2 = 0;
	}			
   }
 

  public void draw (Graphics2D g2) {
	g2.drawImage(bgImage, backgroundX, 0, null);
	g2.drawImage(bgImage, backgroundX2, 0, null);
  }

  public void drawMenuLvl1(Graphics2D g2){
	
	//g2.drawImage(menuBg, backgroundX, 0, null);
	g2.drawImage(menuBg, 0, 0, window.getSize().width, window.getSize().height, null);
  }

  public void drawMenuLvl2(Graphics2D g2){
	
	//g2.drawImage(menuBg, backgroundX, 0, null);
	g2.drawImage(menuBgLvl2, 0, 0, window.getSize().width, window.getSize().height, null);
  }


  public Image loadImage (String fileName) {
	return new ImageIcon(fileName).getImage();
  }

}
