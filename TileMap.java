//816018329
import java.util.LinkedList;
import java.util.Iterator;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;


/**
    The TileMap class contains the data for a tile-based
    map, including Sprites. Each tile is a reference to an
    Image. Images are used multiple times in the tile map.
    map.
*/

public class TileMap {

    private static final int TILE_SIZE = 64;
    private static final int TILE_SIZE_BITS = 6;

    private Tile[][] tileObjs;
    private int mapWidth, mapHeight;


    private Player player;

    private JFrame window;
    private Dimension dimension;
    private int jumpCounter;
    private boolean playerJumping;
    private boolean jumpRight;
    private boolean jumpLeft;
    private boolean level1Complete;
    private boolean playerFalling;
    private boolean touchedFloor;




    private SoundManager soundManager;


    /**
        Creates a new TileMap with the specified width and
        height (in number of tiles) of the map.
    */

    public TileMap(JFrame window, int width, int height) {

	this.window = window;
	dimension = window.getSize();

    soundManager = SoundManager.getInstance();

	mapWidth = width;
	mapHeight = height;

    jumpCounter=0;
    jumpRight=false;
    jumpLeft=false;
    touchedFloor=false;

    
    tileObjs= new Tile[mapWidth][mapHeight];


	player = new Player (window, this);

    

    int playerHeight = 60;

    playerJumping=false;
    playerFalling=false;


	int x, y;
	// x = (dimension.width / 2) + TILE_SIZE;

	x = 192;
	y = dimension.height - (TILE_SIZE + playerHeight);

        player.setX(x);
        player.setY(y);

        level1Complete=false;
    }


    /**
        Gets the width of this TileMap (number of pixels across).
    */
    public int getWidthPixels() {
	return tilesToPixels(mapWidth);
    }


    /**
        Gets the width of this TileMap (number of tiles across).
    */
    public int getWidth() {
        return mapWidth;
    }


    /**
        Gets the height of this TileMap (number of tiles down).
    */
    public int getHeight() {
        return mapHeight;
    }


    /**
        Gets the tile at the specified location. Returns null if
        no tile is at the location or if the location is out of
        bounds.
    */
    public Image getTileImage(int x, int y) {
        if (x < 0 || x >= mapWidth ||
            y < 0 || y >= mapHeight)
        {
            return null;
        }
        else {
            

            if (tileObjs[x][y]!=null){
                return tileObjs[x][y].getImage();
            }

        }
        return null;
    }

    public Tile getTile (int x, int y){
        if (x < 0 || x >= mapWidth ||
        y < 0 || y >= mapHeight)
    {
        return null;
    }
    return tileObjs[x][y];
}


        
    


    /**
        Sets the tile at the specified location.
    */
    public void setTile(int x, int y, Image tile) {
      //  tileObjs[x][y].setImage(tile);
     Tile temp= new Tile();
     temp.setImage(tile);
     tileObjs[x][y]= temp;

     
        
    }


    /**
        Gets an Iterator of all the Sprites in this map,
        excluding the player Sprite.
    */



    /**
        Class method to convert a pixel position to a tile position.
    */

    public static int pixelsToTiles(float pixels) {
        return pixelsToTiles(Math.round(pixels));
    }


    /**
        Class method to convert a pixel position to a tile position.
    */

    public static int pixelsToTiles(int pixels) {
        return (int)Math.floor((float)pixels / TILE_SIZE);
    }


    /**
        Class method to convert a tile position to a pixel position.
    */

    public static int tilesToPixels(int numTiles) {
        return numTiles * TILE_SIZE;
    }  

    

    /**
        Draws the specified TileMap.
    */
    public void draw(Graphics2D g2)
    {
        int mapWidthPixels = tilesToPixels(mapWidth);
	    int screenWidth = dimension.width;
	    int screenHeight = dimension.height;


        // get the scrolling position of the map
        // based on player's position

        int offsetX = screenWidth / 2 -
        Math.round(player.getX()) - TILE_SIZE;
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, screenWidth - mapWidthPixels);

        // get the y offset to draw all sprites and tiles

        int offsetY = screenHeight - tilesToPixels(mapHeight);
/*
        // draw black background, if needed
        if (background == null ||
            screenHeight > background.getHeight(null))
        {
            g.setColor(Color.black);
            g.fillRect(0, 0, screenWidth, screenHeight);
        }
*/

        // draw the visible tiles

        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX +
            pixelsToTiles(screenWidth) + 1;
        for (int y=0; y<mapHeight; y++) {
            for (int x=firstTileX; x <= lastTileX; x++) {

                
                Rectangle2D.Double playerBox= new Rectangle2D.Double(Math.round(player.getX()) + offsetX,
                Math.round(player.getY()),75,60);

                //collision detection

                Tile temp= getTile(x, y);



                if (temp!=null){
                    if (temp.returnKey().equals("coin")){

                        if (temp.intersectsObject(playerBox, tilesToPixels(x), tilesToPixels(y),offsetX,offsetY)){
                            tileObjs[x][y]=null;
                            player.addCoins();
                            soundManager.playSound("coin", false);
                        }

                    }
                    
                    if (temp.returnKey().equals("silver")){
                        
                        if (temp.intersectsObject(playerBox, tilesToPixels(x), tilesToPixels(y),offsetX,offsetY)){
                            player.addSilverKeyCollected();
                            soundManager.playSound("key", false);
                            tileObjs[x][y]=null;
                            
                        }
                    }
                    
                    if (temp.returnKey().equals("gold")){

                        if (temp.intersectsObject(playerBox, tilesToPixels(x), tilesToPixels(y),offsetX,offsetY)){
                            player.addGoldKeyCollected();
                            soundManager.playSound("key", false);
                            tileObjs[x][y]=null;
                            
                        }


                    }

                    if (temp.returnKey().equals("diamond")){

                        if (temp.intersectsObject(playerBox, tilesToPixels(x), tilesToPixels(y),offsetX,offsetY)){
                            tileObjs[x][y]=null;
                            player.addDiamond();
                            soundManager.playSound("diamond", false);
                        }

                    }

                    if (temp.returnKey().equals("lockedDoor")){

                        if (temp.intersectsObject(playerBox, tilesToPixels(x), tilesToPixels(y),offsetX,offsetY)){
                            
                           
                           if (player.canEnterDoor()){
                               level1Complete= true;
                               //soundManager.playSound("mystical", false);
                               //change tile image to open door
                           }


           
                        }

                        

                    }

                    if (temp.returnKey().equals("enemy")){

                        if (temp.intersectsObject(playerBox, tilesToPixels(x), tilesToPixels(y),offsetX,offsetY)){
                            
                            player.loseLife();
                           // soundManager.playSound("hurt", false);
                           player.respawnAfterHurt();

                        }

                    }

                    if (playerFalling){
                        if (temp.returnKey().equals("Ground")){
                

                            if (temp.intersectsGroundObjectTop(playerBox, tilesToPixels(x), tilesToPixels(y),offsetX,offsetY)){
                                
                                playerFalling=false;
                                playerJumping=false;
                                player.addDiff();
                    
                            }
    
                            
                        }
                    }




                    

                }

               

                
                Image image = getTileImage(x, y);



                if (image != null) {

                        g2.drawImage(image,
                        tilesToPixels(x) + offsetX,
                        tilesToPixels(y) + offsetY,
                        null);
                }

                
            }
        
            
        
        
        
        
        
        
        
        }

        // draw player

        if(playerJumping){
            jumpUp(playerFalling);
            jumpCounter++;
        }
        if (playerFalling){
            player.moveDown();
        }
        
        
    

            player.draw(g2,Math.round(player.getX()) + offsetX,
            Math.round(player.getY()));

            


/*
        // draw sprites
        Iterator i = map.getSprites();
        while (i.hasNext()) {
            Sprite sprite = (Sprite)i.next();
            int x = Math.round(sprite.getX()) + offsetX;
            int y = Math.round(sprite.getY()) + offsetY;
            g.drawImage(sprite.getImage(), x, y, null);

            // wake up the creature when it's on screen
            if (sprite instanceof Creature &&
                x >= 0 && x < screenWidth)
            {
                ((Creature)sprite).wakeUp();
            }
        }
*/
    
    }

    public void moveLeft() {
	int x;
	x = player.getX();
	String mess = "Going left. x = " + x;
	//System.out.println(mess);

	player.moveLeft();

    }

    public void moveRight() {
	int x;
	x = player.getX();


	player.moveRight();

    int mapWidthPixels = tilesToPixels(mapWidth);
	int screenWidth = dimension.width;
	int screenHeight = dimension.height;

        // get the scrolling position of the map
        // based on player's position

        int offsetX = screenWidth / 2 -
            Math.round(player.getX()) - TILE_SIZE;
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, screenWidth - mapWidthPixels);



    

    }

    public void jump(){


        if (jumpLeft){
            moveLeft();
        }

        else if (jumpRight){
            moveRight();
        }


        if (jumpCounter<=3){
            player.moveUp();
        }

        else if (jumpCounter>=4 && jumpCounter<8){
            playerFalling=true;
            player.moveDown();
        }


        else{
            jumpCounter=-1;
            player.setJumpState(false);
            playerJumping=false;
        }
        
        jumpLeft=false;
        jumpRight=false;

    }

    public void jumpUp(boolean falling){
        

        if (!falling){
            if (jumpCounter<=3){
                player.moveUp();
            }
            else {
                playerFalling=true;
                jumpCounter=-1;
                
            }
    
        }



    }




    public void setPlayerState(int x){

        if (x==1){
            player.setIdleState(true);
            player.setRunRightState(false);
            player.setRunLeftState(false);
        }

        if (x==2){
            player.setRunLeftState(true);
            player.setIdleState(false);
            player.setRunRightState(false);
        }

        if (x==3){
            player.setRunRightState(true);
            player.setIdleState(false);
            player.setRunLeftState(false);
        }

        if (x==4){
            player.setJumpState(true);
            playerJumping=true;
        }

    }


    public boolean getJumpState() {
        return playerJumping;
    }

    public boolean getFallingState(){
        return playerFalling;
    }

    public int getPlayerScore(){
        return player.getScore();
    }

    public int getLives(){
        return player.getLives();
    }

    public void loseLife(){
        player.loseLife();
    }

    public boolean silverKeyCollected(){
        return player.silverKeyCollected();
    }

    public boolean goldKeyCollected(){
        return player.goldKeyCollected();
    }

    public void setTileKey(String s, int x, int y){
        tileObjs[x][y].setKey(s);
    }

    public boolean getLevelComplete(){
        return level1Complete;
    }



}
