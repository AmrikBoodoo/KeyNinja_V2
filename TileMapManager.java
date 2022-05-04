//816018329
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.Image;
import javax.swing.ImageIcon;




public class TileMapManager {

    private ArrayList<Image> tiles;


    private JFrame window;


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

                    else{
                        newMap.setTileKey("Ground",x,y);
                    }
                //    Rectangle2D.Double temp= new Rectangle2D.Double(x,y,tempx,tempy);
                    
                    
                    
                }

            }
        }

        return newMap;
    }




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



}
