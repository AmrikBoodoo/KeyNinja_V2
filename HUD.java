import javax.swing.*;			// need this for GUI objects
import java.awt.*;			// need this for certain AWT classes
import java.awt.image.BufferedImage;


public class HUD {

	private BufferedImage image;			// drawing area for each frame

	private Image quit1Image;			// first image for quit button
	private Image quit2Image;			// second image for quit button


	private volatile boolean isOverQuitButton = false;
	private Rectangle quitButtonArea;		// used by the quit button
	private Rectangle startButtonArea;
	private Rectangle menuButtonArea;
	private Rectangle levelButtonArea;

	private Image startImage;
	private Image startImage2;

	
	private Image menuButtonImage;
	private Image menuButtonImage2;

	
	private Image levelButtonImage;
	private Image levelButtonImage2;

	private Image lives3Image;
	private Image lives2Image;
	private Image lives1Image;
	private Image lives0Image;
	private Rectangle livesArea;

	private Image noKeysImage;
	private Image silverKeyImage;
	private Image goldKeyImage;
	private Image bothKeysImage;
	private Rectangle keysArea;
    int pWidth;



    public HUD(int pWidth){
        this.pWidth=pWidth;


    }

    public Image loadImage (String fileName) {
		return new ImageIcon(fileName).getImage();
	}


    public void intializeButtonAndInfoImages(){
	//	backgroundImage= loadImage("images/homeScreen.jpeg");

		quit1Image = loadImage("images/QuitGame2.png");
		quit2Image = loadImage("images/QuitGame1.png");

		startImage=loadImage("images/startGame2.png");
		startImage2=loadImage("images/startGame.png");

		menuButtonImage=loadImage("images/MainMenu2.png");
		menuButtonImage2=loadImage("images/MainMenu.png");

		levelButtonImage=loadImage("images/selectLevel2.png");
		levelButtonImage2=loadImage("images/selectLevel.png");

		lives0Image=loadImage("images/noLives.png");
		lives1Image=loadImage("images/1life.png");
		lives2Image=loadImage("images/2lives.png");
		lives3Image=loadImage("images/3lives.png");

		noKeysImage=loadImage("images/noKeys.png");
		silverKeyImage=loadImage("images/1key_silver.png");
		goldKeyImage=loadImage("images/1key_gold.png");
		bothKeysImage=loadImage("images/2keys.png");

		
	}

    
	public void setButtonAndInfoAreas() {
		
		//  leftOffset is the distance of a button from the left side of the window.
		//  Buttons are placed at the top of the window.

		int leftOffset = (pWidth - (5 * 150) - (4 * 20)) / 2 + 700;  //I , amrik boodoo add in the +500 for

		livesArea= new Rectangle(60,10,220,70);
		keysArea= new Rectangle (400,15,220,60);

		menuButtonArea = new Rectangle(leftOffset, 30, 160, 40);   //y changed from 55
		leftOffset = leftOffset + 170;
		
		int quitLength = quit1Image.getWidth(null);
		int quitHeight = quit1Image.getHeight(null);
		quitButtonArea = new Rectangle(leftOffset, 30, 160, 40);   //y changed from 55

		
		
	}

	public void setMenuButtonAreas() {
		
		//  leftOffset is the distance of a button from the left side of the window.
		//  Buttons are placed at the top of the window.

		int leftOffset = (pWidth - (5 * 150) - (4 * 20)) / 2 + 700;  //I , amrik boodoo add in the +730 for

		leftOffset = leftOffset + 170;
		
		int startLength = quit1Image.getWidth(null);
		int startHeight = quit1Image.getHeight(null);
		quitButtonArea = new Rectangle(leftOffset, 30, 160, 40);   //y changed from 55

		startButtonArea = new Rectangle(500, 500, 300, 55);   //y changed from 55
		levelButtonArea= new Rectangle(500,600,300,55);
		
	}

    
	public void drawButtons (Graphics g, TileMap tileMap, boolean isOverQuitButton, boolean isOverMenuStartButton, boolean isOverLevelButton,  boolean isOverMenuButton) {

		int x=5;
	

		if (isOverQuitButton){
		   g.drawImage(quit1Image, quitButtonArea.x, quitButtonArea.y, quitButtonArea.width, quitButtonArea.height, null);
		    	       
		}		
		else{
		   g.drawImage(quit2Image, quitButtonArea.x, quitButtonArea.y, quitButtonArea.width, quitButtonArea.height, null);
		    	       
		}	
		if (isOverMenuButton){
			g.drawImage(menuButtonImage, menuButtonArea.x, menuButtonArea.y,  menuButtonArea.width, menuButtonArea.height, null);
		}						   
							
		else{
			g.drawImage(menuButtonImage2, menuButtonArea.x, menuButtonArea.y, menuButtonArea.width, menuButtonArea.height, null);
		}						   				   

		
		
		
		if (tileMap.getLives()==0){
			g.drawImage(lives0Image, livesArea.x, livesArea.y, livesArea.width, livesArea.height, null);
		}

		else if (tileMap.getLives()==1){
			g.drawImage(lives1Image, livesArea.x, livesArea.y, livesArea.width, livesArea.height, null);
		}

		else if (tileMap.getLives()==2){
			g.drawImage(lives2Image, livesArea.x, livesArea.y, livesArea.width, livesArea.height, null);
		}

		else{
			g.drawImage(lives3Image, livesArea.x, livesArea.y, livesArea.width, livesArea.height, null);
		}



		if ((!tileMap.goldKeyCollected()) && (!tileMap.silverKeyCollected())){
			g.drawImage(noKeysImage, keysArea.x, keysArea.y, keysArea.width, keysArea.height, null);
		}

		else if ((!tileMap.goldKeyCollected()) && (tileMap.silverKeyCollected())){
			g.drawImage(silverKeyImage, keysArea.x, keysArea.y, keysArea.width, keysArea.height, null);
		}

		else if ((tileMap.goldKeyCollected()) && (!tileMap.silverKeyCollected())){
			g.drawImage(goldKeyImage, keysArea.x, keysArea.y, keysArea.width, keysArea.height, null);
		}

		else{
			g.drawImage(bothKeysImage, keysArea.x, keysArea.y, keysArea.width, keysArea.height, null);
		}

		       
	
	  
		//SCORING INFO
				  
		Font f = new Font ("Calibri", Font.BOLD, 40);
		g.setFont (f);
		g.setColor(Color.WHITE);
		 
		 g.drawString("Score: " + tileMap.getPlayerScore(), 700,55  );
		
		  
		  
				  
		  
			   

	}

	public void drawMenuButtons (Graphics g, boolean isOverQuitButton, boolean isOverMenuStartButton, boolean isOverLevelButton) {
	
					   
		if (isOverQuitButton){
			g.drawImage(quit1Image, quitButtonArea.x, quitButtonArea.y, 160, 40, null);
		}			
			 
		 else
			g.drawImage(quit2Image, quitButtonArea.x, quitButtonArea.y, 160, 40, null);
					

		if (isOverMenuStartButton){
			g.drawImage(startImage, startButtonArea.x, startButtonArea.y, 300, 55, null);
		}    	       
							 
		else{
			g.drawImage(startImage2, startButtonArea.x, startButtonArea.y, 300,55, null);
		}   	       

		if (isOverLevelButton){
			g.drawImage(levelButtonImage, levelButtonArea.x, levelButtonArea.y, 300, 55, null);
		}    	       
							 
		else{
			g.drawImage(levelButtonImage2, levelButtonArea.x, levelButtonArea.y, 300,55, null);
		}   
			 


	}

    public Rectangle returnQuitButtonArea(){
        return quitButtonArea;
    }

    public Rectangle returnStartButtonArea(){
        return startButtonArea;
    }

    public Rectangle returnMenuButtonArea(){
        return menuButtonArea;
    }

        
    public Rectangle returnLevelButtonArea(){
        return levelButtonArea;
    }
        
        
    
}
