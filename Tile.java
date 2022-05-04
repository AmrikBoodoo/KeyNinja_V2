//816018329
import java.awt.geom.Rectangle2D;
import java.awt.geom.*;
import java.awt.Image;





public class Tile {
    
        
    private Image tile;
    private String key;


    public Tile(){

       key="temp";

    }

    public void setKey(String s){
        this.key=s;
    }
    
    public String  returnKey(){
        return key;
    }

    public Image getImage(){
        return tile;
    }

    public void setImage(Image i){
        this.tile= i;
    }

    public boolean intersectsObject(Rectangle2D.Double d, int x, int y, int offsetX, int offsetY){

        int tempw= tile.getWidth(null);
        int temph= tile.getHeight(null);

        Rectangle2D.Double tempr= new Rectangle2D.Double(x+offsetX,y+offsetY,tempw,temph);

        return tempr.intersects(d);

    }

    public boolean intersectsGroundObjectTop(Rectangle2D.Double d, int x, int y, int offsetX, int offsetY){

        int tempw= tile.getWidth(null);
        int temph= tile.getHeight(null);


        int tempX= x+offsetX;
        int tempY= y+offsetY;


        Rectangle2D.Double tempr= new Rectangle2D.Double(tempX,tempY,tempw,2);


        return tempr.intersects(d);

    }

    
}
