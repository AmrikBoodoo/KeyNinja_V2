//816018329
import javax.swing.*;			// need this for GUI objects
import java.awt.*;			// need this for certain AWT classes
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.image.BufferStrategy;	// need this to implement page flipping


public class GameWindow extends JFrame implements
				Runnable,
				KeyListener,
				MouseListener,
				MouseMotionListener
{
  	private static final int NUM_BUFFERS = 2;	// used for page flipping

	private int pWidth, pHeight;     		// width and height of screen

	private Thread gameThread = null;            	// the thread that controls the game
	private volatile boolean isRunning = false;    	// used to stop the game thread

	private BufferedImage image;			// drawing area for each frame


	private boolean finishedOff = false;		// used when the game terminates

	private volatile boolean isOverQuitButton = false;


	private volatile boolean isOverMenuStartButton= false;


	private volatile boolean isOverMenuButton= false;


	private volatile boolean isOverLevelButton=false;

	private GraphicsDevice device;			// used for full-screen exclusive mode 
	private Graphics gScr;
	private BufferStrategy bufferStrategy;

	private SoundManager soundManager;
	BackgroundManager bgManager;
	BackgroundManager bgManager2;
	BackgroundManager tempBgManager;
	TileMapManager tileManager;
	TileMap	tileMap;
	TileMap tileMap2;
	TileMap tempTileMap;


	private boolean isMenu;

	private boolean isLvl1;
	private boolean lvl1Complete;
	private boolean endGameWait;

	private HUD hud;   //Heads up display... also used for all button intialization and drawing

	public GameWindow() {
 
		super("Key Ninja ");

		soundManager = SoundManager.getInstance();

		initFullScreen();
		hud= new HUD(pWidth);
		
		hud.intializeButtonAndInfoImages();
		hud.setButtonAndInfoAreas();
		hud.setMenuButtonAreas();


		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		soundManager = SoundManager.getInstance();

		image = new BufferedImage (pWidth, pHeight, BufferedImage.TYPE_INT_RGB);

		isMenu= true;
		isLvl1= true;
		lvl1Complete=false;
		endGameWait=false;

		startGame();
		soundManager.playSound("backgroundMenu", true);
	}


	// implementation of Runnable interface

	public void run () {
		try {
			isRunning = true;
			
			while (isRunning) {
				screenUpdate();
				Thread.sleep (50);

				if (endGameWait){
					Thread.sleep(10000);
				}

			}
		}
		catch(InterruptedException e) {}

		finishOff();
	}


	/* This method performs some tasks before closing the game.
	   The call to System.exit() should not be necessary; however,
	   it prevents hanging when the game terminates.
	*/

	private void finishOff() { 
    		if (!finishedOff) {
			finishedOff = true;
			restoreScreen();
			System.exit(0);
		}
	}


	/* This method switches off full screen mode. The display
	   mode is also reset if it has been changed.
	*/

	private void restoreScreen() { 
		Window w = device.getFullScreenWindow();
		
		if (w != null)
			w.dispose();
		
		device.setFullScreenWindow(null);
	}


	public void gameUpdate () {
		
	
	}


	private void screenUpdate() { 

		try {
			gScr = bufferStrategy.getDrawGraphics();

			if (isMenu){
				menuRender(gScr);
				
			}
			else{
				
			gameRender(gScr);

			}
			gScr.dispose();
			if (!bufferStrategy.contentsLost())
				bufferStrategy.show();
			else
				System.out.println("Contents of buffer lost.");
      
			// Sync the display on some systems.
			// (on Linux, this fixes event queue problems)

			Toolkit.getDefaultToolkit().sync();
		}
		catch (Exception e) { 
			e.printStackTrace();  
			isRunning = false; 
		} 
	}


	public void gameRender (Graphics gScr) {		// draw the game objects

		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		endGameWait=false;

		if (isLvl1){
			tileMap=tempTileMap;
			bgManager.draw(imageContext);
			tileMap.draw(imageContext);
			hud.drawButtons(imageContext, tileMap, isOverQuitButton, isOverMenuStartButton, isOverLevelButton,isOverMenuButton);

		}
		else{		//IS LEVEL 2
			tileMap= tileMap2;		
			bgManager.draw(imageContext);
			tileMap.draw(imageContext);
			hud.drawButtons(imageContext, tileMap, isOverQuitButton, isOverMenuStartButton, isOverLevelButton,isOverMenuButton);

		}
		if (tileMap.getLevelComplete()){   //LEVEL COMPLETED
			if (!lvl1Complete){
				isLvl1=false;
				soundManager.stopSound("backgroundLevel1");
				soundManager.playSound("backgroundLevel2", true);
				bgManager= bgManager2;
				lvl1Complete=true;
				gameOverMessage(imageContext,true);
			}
			else{
				soundManager.stopSound("backgroundLevel2");
				soundManager.playSound("menuBackground", true);
				isMenu=true;
				restartLevels();
				gameOverMessage(imageContext,true);
			}

		}



		if (tileMap.getLives()==0){       //GAME OVER

			gameOverMessage(imageContext,false);
			restartLevels();
		}


		Graphics2D g2 = (Graphics2D) gScr;
		g2.drawImage(image, 0, 0, pWidth, pHeight, null);

		imageContext.dispose();
		g2.dispose();
	}




	public void menuRender (Graphics gScr) {		

		Graphics2D imageContext = (Graphics2D) image.getGraphics();

		bgManager.drawMenuLvl1(imageContext);
		
		if (isLvl1){
			bgManager.drawMenuLvl1(imageContext);
		}
		else{
			bgManager.drawMenuLvl2(imageContext);
		}
		
		hud.drawMenuButtons(imageContext,isOverQuitButton, isOverMenuStartButton, isOverLevelButton);			// draw the buttons
		

		Graphics2D g2 = (Graphics2D) gScr;
		g2.drawImage(image, 0, 0, pWidth, pHeight, null);
		



		imageContext.dispose();
		g2.dispose();
		
	
		

	}



	private void initFullScreen() {				// standard procedure to get into FSEM

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = ge.getDefaultScreenDevice();

		setUndecorated(true);	// no menu bar, borders, etc.
		setIgnoreRepaint(true);	// turn off all paint events since doing active rendering
		setResizable(false);	// screen cannot be resized
		
		if (!device.isFullScreenSupported()) {
			System.out.println("Full-screen exclusive mode not supported");
			System.exit(0);
		}

		device.setFullScreenWindow(this); // switch on full-screen exclusive mode

		// we can now adjust the display modes, if we wish

		showCurrentMode();

		pWidth = getBounds().width;
		pHeight = getBounds().height;
		
		System.out.println("Width of window is " + pWidth);
		System.out.println("Height of window is " + pHeight);

		try {
			createBufferStrategy(NUM_BUFFERS);
		}
		catch (Exception e) {
			System.out.println("Error while creating buffer strategy " + e); 
			System.exit(0);
		}

		bufferStrategy = getBufferStrategy();
	}


	// This method provides details about the current display mode.

	private void showCurrentMode() {

		DisplayMode dms[] = device.getDisplayModes();

		for (int i=0; i<dms.length; i++) {
			System.out.println("Display Modes Available: (" + 
                           dms[i].getWidth() + "," + dms[i].getHeight() + "," +
                           dms[i].getBitDepth() + "," + dms[i].getRefreshRate() + ")  " );			
		}

/*
		DisplayMode d = new DisplayMode (800, 600, 32, 60);
		device.setDisplayMode(d);
*/

		DisplayMode dm = device.getDisplayMode();

		System.out.println("Current Display Mode: (" + 
                           dm.getWidth() + "," + dm.getHeight() + "," +
                           dm.getBitDepth() + "," + dm.getRefreshRate() + ")  " );
  	}




	




	public Image loadImage (String fileName) {
		return new ImageIcon(fileName).getImage();
	}

		


	private void startGame() { 
		if (gameThread == null) {
			//soundManager.playSound ("background", true);

			bgManager = new BackgroundManager (this, 12,1);
			bgManager2= new BackgroundManager (this, 12, 2);
			tempBgManager= bgManager;
	
			tileManager = new TileMapManager (this);

			try {
				tileMap = tileManager.loadMap("maps/map1.txt");
				tileMap2= tileManager.loadMap("maps/map2.txt");
				tempTileMap= tileMap;
			
				int w, h;
				w = tileMap.getWidth();
				h = tileMap.getHeight();
				System.out.println ("Width of tilemap " + w);
				System.out.println ("Height of tilemap " + h);
			}
			catch (Exception e) {
				System.out.println(e);
				System.exit(0);
			}

			gameThread = new Thread(this);
			gameThread.start();			

		}
	}


	// displays a message to the screen when the user stops the game

	private void gameOverMessage(Graphics g, boolean win) {
		
		Font font = new Font("SansSerif", Font.BOLD, 48);
		FontMetrics metrics = this.getFontMetrics(font);
		String msg="";

		if (win){
			 msg = "Level Complete! Score: " + tileMap.getPlayerScore()+ "  loading....";
		}
		else{
			 msg = "Level Lost :(  Try Again! Score: " + tileMap.getPlayerScore() + "  Restarting Level....";
		}
		
		


		int x = (pWidth - metrics.stringWidth(msg)) / 2; 
		int y = (pHeight - metrics.getHeight()) / 2;

		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString(msg, x, y);
		endGameWait=true;

	}




	public void restartLevels(){
		
		bgManager = new BackgroundManager (this, 12,1);
		bgManager2= new BackgroundManager (this, 12, 2);
		tempBgManager= bgManager;

		tileManager = new TileMapManager (this);

		try {
			tileMap = tileManager.loadMap("maps/map1.txt");
			tileMap2= tileManager.loadMap("maps/map2.txt");
			tempTileMap= tileMap;
		
		}
		catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

	}










	// implementation of methods in KeyListener interface

	
	
	
	
	




	
	
	
	
	
	public void keyPressed (KeyEvent e) {

		int keyCode = e.getKeyCode();

		if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
             	   (keyCode == KeyEvent.VK_END)) {
           		isRunning = false;		// user can quit anytime by pressing
			return;				//  one of these keys (ESC, Q, END)			
         	}
		else
		if (keyCode == KeyEvent.VK_LEFT ) {
			bgManager.moveLeft();
			tileMap.moveLeft();
			tileMap.setPlayerState(2);
		}
		else
		if (keyCode == KeyEvent.VK_RIGHT ) {
			bgManager.moveRight();
			tileMap.moveRight();
			tileMap.setPlayerState(3);
			
		}
		else
		if (keyCode == KeyEvent.VK_SPACE) {
			tileMap.setPlayerState(4);
			soundManager.playSound("jump", false);

		}



	}


	public void keyReleased (KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT) {
			tileMap.setPlayerState(1);
		}
		
	}


	public void keyTyped (KeyEvent e) {

	}


	// implement methods of MouseListener interface

	public void mouseClicked(MouseEvent e) {

	}


	public void mouseEntered(MouseEvent e) {

	}


	public void mouseExited(MouseEvent e) {

	}


	public void mousePressed(MouseEvent e) {
		testMousePress(e.getX(), e.getY());
	}


	public void mouseReleased(MouseEvent e) {

	}


	// implement methods of MouseMotionListener interface

	public void mouseDragged(MouseEvent e) {

	}	


	public void mouseMoved(MouseEvent e) {
		testMouseMove(e.getX(), e.getY()); 
	}


	/* This method handles mouse clicks on one of the buttons
	   (Pause, Stop, Start Anim, Pause Anim, and Quit).
	*/

	private void testMousePress(int x, int y) {

		
			if (isOverQuitButton) {		// mouse click on Quit button
			soundManager.playSound("buttonSelect", false);
			isRunning = false;		// set running to false to terminate
		}

		if (isOverMenuStartButton) {		// mouse click on Quit button
			isMenu=false;		// set running to false to terminate
			soundManager.stopSound("backgroundMenu");
			soundManager.playSound("buttonSelect", false);
			
			if (isLvl1){
				soundManager.playSound("backgroundLevel1", true);
			}

			else if (!isLvl1){
				soundManager.playSound("backgroundLevel2", true);
			}

			
	
		}

		if (isOverMenuButton){
			isMenu=true;
			

			if (isLvl1){
				soundManager.stopSound("backgroundLevel1");
			}
	
			else if (!isLvl1){
				soundManager.stopSound("backgroundLevel2");
			}
			
			
			soundManager.playSound("buttonSelect", false);
			soundManager.playSound("backgroundMenu", true);
			restartLevels();
			
		}

		if (isOverLevelButton){
			soundManager.playSound("buttonSelect", false);
			if (isLvl1){
				bgManager= bgManager2;
				isLvl1=false;
			}
			else{
				isLvl1=true;
				bgManager= tempBgManager;
			}
			
		}
  	}


	/* This method checks to see if the mouse is currently moving over one of
	   the buttons (Pause, Stop, Show Anim, Pause Anim, and Quit). It sets a
	   boolean value which will cause the button to be displayed accordingly.
	*/

	private void testMouseMove(int x, int y) { 
		if (isRunning) {
			isOverQuitButton = hud.returnQuitButtonArea().contains(x,y) ? true : false;
			isOverMenuStartButton = hud.returnStartButtonArea().contains(x,y) ? true : false;
			isOverMenuButton= hud.returnMenuButtonArea().contains(x,y) ? true : false;
			isOverLevelButton= hud.returnLevelButtonArea().contains(x,y) ? true : false;
		}
	}

}