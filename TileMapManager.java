//816018329
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.Image;
import javax.swing.ImageIcon;



/**
    The ResourceManager class loads and manages tile Images and
    "host" Sprites used in the game. Game Sprites are cloned from
    "host" Sprites.
*/
public class TileMapManager {

    private ArrayList<Image> tiles;


    private JFrame window;


/*
    private GraphicsConfiguration gc;

    // host sprites used for cloning
    private Sprite playerSprite;
    private Sprite musicSprite;
    private Sprite coinSprite;
    private Sprite goalSprite;
    private Sprite grubSprite;
    private Sprite flySprite;
*/

    public TileMapManager(JFrame window) {
	this.window = window;

        loadTileImages();
        


    }


    /**
        Gets an image from the images/ folder.
    */
    public Image loadImage(String name) {

	String filename = "images/" + name;

	File file = new File(filename);
	if (!file.exists()) {
		System.out.println("Image file could not be opened: " + filename);
	}
	else
		System.out.println("Image file opened: " + filename);

        return new ImageIcon(filename).getImage();
    }


    public TileMap loadMap(String filename)
        throws IOException
    {
        ArrayList<String> lines = new ArrayList<String>();
        int mapWidth = 0;
        int mapHeight = 0;

        // read every line in the text file into the list

        BufferedReader reader = new BufferedReader(
            new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            // add every line except for comments
            if (!line.startsWith("#")) {
                lines.add(line);
                mapWidth = Math.max(mapWidth, line.length());
            }
        }

        // parse the lines to create a TileMap
        mapHeight = lines.size();
        TileMap newMap = new TileMap(window, mapWidth, mapHeight);
        for (int y=0; y<mapHeight; y++) {
            String line = lines.get(y);
            for (int x=0; x<line.length(); x++) {
                char ch = line.charAt(x);

                // check if the char represents tile A, B, C etc.
                int tile = ch - 'A';
                if (tile >= 0 && tile < tiles.size()) {
                    newMap.setTile(x, y, tiles.get(tile));
                  //  newMap.setTileKey("coin");
                    
                    
                    int tempx, tempy;
                    tempx= tiles.get(tile).getHeight(null);
                    tempy= tiles.get(tile).getWidth(null);

                    if(tile==18){   // S - A = 83-65= 18 , COINS
                        newMap.setTileKey("coin",x,y);
                    }

                    else if (tile==13){ // N - A = 78-65= 13... GOLD KEY
                        newMap.setTileKey("gold",x,y);
                    }

                    else if (tile==14){ //O-A= 79-65=14.... Silver KEY
                        newMap.setTileKey("silver",x,y);
                    }

                    else if (tile == 17){ //R-A = 82-65=17  Diamond
                        newMap.setTileKey("diamond", x, y);
                    }

                    else if (tile==15 ){ // P-A =80-65= 15 Locked door
                        newMap.setTileKey("lockedDoor", x, y);
                    }

                    else if (tile==20 ){ // U-A =85-65= 20 ghost enemy
                        newMap.setTileKey("enemy", x, y);
                    }
                //    Rectangle2D.Double temp= new Rectangle2D.Double(x,y,tempx,tempy);
                    
                    
                    
                }
/*
                // check if the char represents a sprite
                else if (ch == 'o') {
                    addSprite(newMap, coinSprite, x, y);
                }
                else if (ch == '!') {
                    addSprite(newMap, musicSprite, x, y);
                }
                else if (ch == '*') {
                    addSprite(newMap, goalSprite, x, y);
                }
                else if (ch == '1') {
                    addSprite(newMap, grubSprite, x, y);
                }
                else if (ch == '2') {
                    addSprite(newMap, flySprite, x, y);
                }
*/
            }
        }

/*
        // add the player to the map
        Sprite player = (Sprite)playerSprite.clone();
        player.setX(TileMapRenderer.tilesToPixels(3));

        player.setY(0);
        newMap.setPlayer(player);
*/      
        return newMap;
    }


/*
    private void addSprite(TileMap map,
        Sprite hostSprite, int tileX, int tileY)
    {
        if (hostSprite != null) {
            // clone the sprite from the "host"
            Sprite sprite = (Sprite)hostSprite.clone();

            // center the sprite
            sprite.setX(
                TileMapRenderer.tilesToPixels(tileX) +
                (TileMapRenderer.tilesToPixels(1) -
                sprite.getWidth()) / 2);

            // bottom-justify the sprite
            sprite.setY(
                TileMapRenderer.tilesToPixels(tileY + 1) -
                sprite.getHeight());

            // add it to the map
            map.addSprite(sprite);
        }
    }

*/

    // -----------------------------------------------------------
    // code for loading sprites and images
    // -----------------------------------------------------------


    public void loadTileImages() {
        // keep looking for tile A,B,C, etc. this makes it
        // easy to drop new tiles in the images/ folder

	File file;

	System.out.println("loadTileImages called.");

        tiles = new ArrayList<Image>();
        char ch = 'A';
        while (true) {
            String filename = "images/Ground_" + ch + ".png";
	    file = new File(filename);
            if (!file.exists()) {
		System.out.println("Image file could not be opened: " + filename);
                break;
            }
	    else
		System.out.println("Image file opened: " + filename);
		Image tileImage = new ImageIcon(filename).getImage();
           	tiles.add(tileImage);
            ch++;
        }
    }

/*
    public void loadCreatureSprites() {

        Image[][] images = new Image[4][];

        // load left-facing images
        images[0] = new Image[] {
            loadImage("player1.png"),
            loadImage("player2.png"),
            loadImage("player3.png"),
            loadImage("fly1.png"),
            loadImage("fly2.png"),
            loadImage("fly3.png"),
            loadImage("grub1.png"),
            loadImage("grub2.png"),
        };

        images[1] = new Image[images[0].length];
        images[2] = new Image[images[0].length];
        images[3] = new Image[images[0].length];
        for (int i=0; i<images[0].length; i++) {
            // right-facing images
            images[1][i] = getMirrorImage(images[0][i]);
            // left-facing "dead" images
            images[2][i] = getFlippedImage(images[0][i]);
            // right-facing "dead" images
            images[3][i] = getFlippedImage(images[1][i]);
        }

        // create creature animations
        Animation[] playerAnim = new Animation[4];
        Animation[] flyAnim = new Animation[4];
        Animation[] grubAnim = new Animation[4];
        for (int i=0; i<4; i++) {
            playerAnim[i] = createPlayerAnim(
                images[i][0], images[i][1], images[i][2]);
            flyAnim[i] = createFlyAnim(
                images[i][3], images[i][4], images[i][5]);
            grubAnim[i] = createGrubAnim(
                images[i][6], images[i][7]);
        }

        // create creature sprites
        playerSprite = new Player(playerAnim[0], playerAnim[1],
            playerAnim[2], playerAnim[3]);
        flySprite = new Fly(flyAnim[0], flyAnim[1],
            flyAnim[2], flyAnim[3]);
        grubSprite = new Grub(grubAnim[0], grubAnim[1],
            grubAnim[2], grubAnim[3]);
System.out.println("loadCreatureSprites successfully executed.");

    }
*/


}
