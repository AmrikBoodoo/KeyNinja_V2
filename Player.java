import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Player {

   private static final int XSIZE = 64;	// width of the player's sprite
   private static final int YSIZE = 64;	// height of the player's sprite			

   private static final int DX = 12;	// amount of X pixels to move in one keystroke
   private static final int DY = 50;	// amount of Y pixels to move in one keystroke

   private JFrame window;		// reference to the JFrame on which player is drawn
   private TileMap tileMap;

   private int x;			// x-position of player's sprite
   private int y;			// y-position of player's sprite

   private Animation ninjaIdle, ninjaRunRight, ninjaRunLeft, ninjaJump;

   private boolean isIdle;
   private boolean isRunningRight;
   private boolean isRunningLeft;
   private boolean isJumping;

   private int score;
   private int lives;
   
   private boolean goldKeyCollected;
   private boolean silverKeyCollected;
   


   Graphics2D g2;


   private Image playerImage, playerLeftImage, playerRightImage;

   public Player (JFrame window, TileMap t) {
      this.window = window;
      tileMap = t;			// tile map on which the player's sprite is displayed

      playerLeftImage = loadImage("images/playerLeft.gif");
      playerRightImage = loadImage("images/playerRight.gif");

      playerImage = playerRightImage;

      x = (int) ((window.getWidth() - playerRightImage.getWidth(null)) / 2);
      y = 380;

      ninjaIdle= new Animation(window,x,y);
      ninjaRunRight= new Animation(window, x, y);
      ninjaRunLeft= new Animation(window, x, y);
      ninjaJump= new Animation(window, x, y);

      isIdle=true;
      isJumping=false;
      isRunningLeft=false;
      isRunningRight=false;

      loadIdleAnimation();
      loadRunAnimationRight();
      loadRunAnimationLeft();

      ninjaIdle.start();
      ninjaRunLeft.start();
      ninjaRunRight.start();

      score=0;
      lives=3;
      goldKeyCollected=false;
      silverKeyCollected=false;

   }

   public void loadIdleAnimation(){

      Image animImage1 = loadImage("images/Idle__000.png");
		Image animImage2 = loadImage("images/Idle__001.png");
		Image animImage3 = loadImage("images/Idle__002.png");
		Image animImage4 = loadImage("images/Idle__003.png");
		Image animImage5 = loadImage("images/Idle__004.png");
		Image animImage6 = loadImage("images/Idle__005.png");
		Image animImage7 = loadImage("images/Idle__006.png");
		Image animImage8 = loadImage("images/Idle__007.png");
		Image animImage9 = loadImage("images/Idle__008.png");
      Image animImage10 = loadImage("images/Idle__009.png");

		// create animation object and insert frames

		
      ninjaIdle.addFrame(animImage1, 200);
      ninjaIdle.addFrame(animImage2, 200);
      ninjaIdle.addFrame(animImage3, 200);
      ninjaIdle.addFrame(animImage4, 200);
      ninjaIdle.addFrame(animImage5, 200);
      ninjaIdle.addFrame(animImage6, 200);
      ninjaIdle.addFrame(animImage7, 200);
      ninjaIdle.addFrame(animImage8, 200);
      ninjaIdle.addFrame(animImage9, 200);
      ninjaIdle.addFrame(animImage10, 200);

      ninjaIdle.start();

   }

   public void loadRunAnimationRight(){

      Image animImage1 = loadImage("images/Run__000.png");
		Image animImage2 = loadImage("images/Run__001.png");
		Image animImage3 = loadImage("images/Run__002.png");
		Image animImage4 = loadImage("images/Run__003.png");
		Image animImage5 = loadImage("images/Run__004.png");
		Image animImage6 = loadImage("images/Run__005.png");
		Image animImage7 = loadImage("images/Run__006.png");
		Image animImage8 = loadImage("images/Run__007.png");
		Image animImage9 = loadImage("images/Run__008.png");
      Image animImage10 = loadImage("images/Run__009.png");

		// create animation object and insert frames

		
      ninjaRunRight.addFrame(animImage1, 200);
      ninjaRunRight.addFrame(animImage2, 200);
      ninjaRunRight.addFrame(animImage3, 200);
      ninjaRunRight.addFrame(animImage4, 200);
      ninjaRunRight.addFrame(animImage5, 200);
      ninjaRunRight.addFrame(animImage6, 200);
      ninjaRunRight.addFrame(animImage7, 200);
      ninjaRunRight.addFrame(animImage8, 200);
      ninjaRunRight.addFrame(animImage9, 200);
      ninjaRunRight.addFrame(animImage10, 200);

   }

   public void loadRunAnimationLeft(){

      Image animImage1 = loadImage("images/Run__000-modified.png");
		Image animImage2 = loadImage("images/Run__001-modified.png");
		Image animImage3 = loadImage("images/Run__002-modified.png");
		Image animImage4 = loadImage("images/Run__003-modified.png");
		Image animImage5 = loadImage("images/Run__004-modified.png");
		Image animImage6 = loadImage("images/Run__005-modified.png");
		Image animImage7 = loadImage("images/Run__006-modified.png");
		Image animImage8 = loadImage("images/Run__007-modified.png");
		Image animImage9 = loadImage("images/Run__008-modified.png");
      Image animImage10 = loadImage("images/Run__009-modified.png");

		// create animation object and insert frames

		
      ninjaRunLeft.addFrame(animImage1, 200);
      ninjaRunLeft.addFrame(animImage2, 200);
      ninjaRunLeft.addFrame(animImage3, 200);
      ninjaRunLeft.addFrame(animImage4, 200);
      ninjaRunLeft.addFrame(animImage5, 200);
      ninjaRunLeft.addFrame(animImage6, 200);
      ninjaRunLeft.addFrame(animImage7, 200);
      ninjaRunLeft.addFrame(animImage8, 200);
      ninjaRunLeft.addFrame(animImage9, 200);
      ninjaRunLeft.addFrame(animImage10, 200);


      

   }


   public void draw (Graphics2D g2, int x, int y) {
	//g2.drawImage (playerImage, x, y, XSIZE, YSIZE, null);

  

      if (isIdle){
         ninjaIdle.draw(g2, x, y);
         ninjaIdle.update();
      }

      else if (isRunningRight){
         ninjaRunRight.draw(g2, x, y);
         ninjaRunRight.update();
      }

      else if (isRunningLeft){
         ninjaRunLeft.draw(g2, x, y);
         ninjaRunLeft.update();
      }


   }


   public Rectangle2D.Double getBoundingRectangle() {
      return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
   }

   public boolean collidesWith(Rectangle2D.Double r){
      if (getBoundingRectangle().intersects(r)){
         return true;
      }
      return false;
   }




   public Image loadImage (String fileName) {
      return new ImageIcon(fileName).getImage();
   }


   public void moveLeft () {
      Dimension dimension;

      if (!window.isVisible ()) return;

      playerImage = playerLeftImage;

      dimension = window.getSize();

      if ((x - DX) > 0)
      	  x = x - DX;

      // check if x is outside the left side of the tile map
   }


   public void moveRight () {
      Dimension dimension;

      if (!window.isVisible ()) return;

      playerImage = playerRightImage;

      dimension = window.getSize();

      int tileMapWidth = tileMap.getWidthPixels();

      int playerWidth = playerImage.getWidth(null);

      if ((x + DX + playerWidth) <= tileMapWidth) {

	  int xTile = tileMap.pixelsToTiles(x + DX + playerWidth);
	  int yTile = tileMap.pixelsToTiles(y) - 1;

          

	  if (tileMap.getTile(xTile, yTile) == null)
	  	x = x + DX;
      }

      // check if x is outside the right side of the tile map.

   }


   public void moveUp () {

      if (!window.isVisible ()) return;

      y = y - 48;
   }


   public void moveDown () {

      if (!window.isVisible ()) return;

      y = y + 48;
   }


   public int getX() {
      return x;
   }


   public void setX(int x) {
      this.x = x;
   }


   public int getY() {
      return y;
   }


   public void setY(int y) {
      this.y = y;
   }


   public Image getImage() {
      return playerImage;
   }

   public void setIdleState(boolean b){
         isIdle=b;
   }

   public void setRunRightState(boolean b){
      isRunningRight=b;
   }

   public void setRunLeftState(boolean b){
      isRunningLeft= b;
   }

   public void setJumpState(boolean b) {
      isJumping=b;
   }

   public boolean getJumpState(){
      return isJumping;
   }

   public void addCoins(){
      score+=5;
   }

   public void addDiamond(){
      score+=25;
   }

   public int getScore(){
      return score;
   }

   public void loseLife(){
      lives-=1;
   }

   public int getLives(){
      return lives;
   }

   public void addSilverKeyCollected(){
      silverKeyCollected=true;
   }

   public void addGoldKeyCollected(){
      goldKeyCollected=true;
   }

   public boolean silverKeyCollected(){
      return silverKeyCollected;
   }

   public boolean goldKeyCollected(){
     return goldKeyCollected;
   }

   public boolean canEnterDoor(){
      if (goldKeyCollected && silverKeyCollected){
         return true;
      }
      return false;
   }

   public void addDiff(){
      y=y-44;
   }

   public void respawnAfterHurt(){
      x= x-128; //2 blocks
   }

}