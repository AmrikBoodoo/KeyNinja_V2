/* 816018329 Amrik Boodoo
*/

import java.awt.Graphics2D;
import javax.swing.JFrame;


public class BackgroundManager {

	private String bgImages[];

  	private int moveAmount[];  
						

  	private Background[] backgrounds;
  	private int numBackgrounds;

  	private JFrame window;			

  	public BackgroundManager(JFrame window, int moveSize, int x) {
						
    	this.window = window;
		initializeBackground(x);
  	} 


  	public void moveRight() { 
		for (int i=0; i < numBackgrounds; i++)
      			backgrounds[i].moveRight();
  	}


  	public void moveLeft() {
		for (int i=0; i < numBackgrounds; i++)
      			backgrounds[i].moveLeft();
  	}
	
	public void isLevel1(boolean t){
		if (t){
			

		}
	}

	public void initializeBackground(int x){

		if (x==1){ //level 1
			bgImages= new String[]  
			{"images/sky.png",
			"images/clouds_1.png",
	 		"images/clouds_2.png",
	 		"images/rocks.png",
	 		"images/ground.png"};

			moveAmount= new int[] {6, 6, 6, 5, 10};

			numBackgrounds = bgImages.length;
    		backgrounds = new Background[numBackgrounds];

    		for (int i = 0; i < numBackgrounds; i++) {
       			backgrounds[i] = new Background(window, bgImages[i], moveAmount[i]);
    		}
			
		}

		if (x==2){ //level 2
			bgImages= new String[]  
			{"images/lvl2sky.png",
			"images/lvl2clouds_1.png",
	 		"images/lvl2clouds_2.png",
			"images/lvl2clouds_3.png",
	 		"images/lvl2rocks_1.png",
	 		"images/lvl2rocks_2.png",
			"images/lvl2rocks_3.png",
			"images/lvl2birds.png",
			"images/lvl2pines.png"};

			moveAmount= new int[] {6, 6, 6, 6, 5, 5, 5, 9, 5};

			numBackgrounds = bgImages.length;
    		backgrounds = new Background[numBackgrounds];

    		for (int i = 0; i < numBackgrounds; i++) {
       			backgrounds[i] = new Background(window, bgImages[i], moveAmount[i]);
    		}
			
		}


	}



	



  	public void draw (Graphics2D g2) { 
		for (int i=0; i < numBackgrounds; i++)
      			backgrounds[i].draw(g2);
  	}

	public void drawMenuLvl1(Graphics2D g2){
		backgrounds[0].drawMenuLvl1(g2);
	}

	public void drawMenuLvl2(Graphics2D g2){
		backgrounds[0].drawMenuLvl2(g2);
	}

	

}

