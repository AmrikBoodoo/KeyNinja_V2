import java.awt.Image;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
    The Animation class manages a series of images (frames) and
    the amount of time to display each frame.
*/

public class Animation {

    private static int XSIZE = 75;		// width of image for player animation
    private static int YSIZE = 60;		// height of image for player animation

    private int dx = 10;
    private int dy = -3;

    private int x;
    private int y;

    private Dimension dimension;

    private JFrame window;			// JFrame on which animation is being displayed
    private ArrayList<AnimFrame> frames;	// collection of frames for animation
    private int currFrameIndex;			// current frame being displayed
    private long animTime;			// time that the animation has run for already
    private long startTime;			// start time of the animation or time since last update
    private long totalDuration;			// total duration of the animation
 
    private int active;

    private SoundManager soundManager;		// reference to SoundManager to play clip


    /**
        Creates a new, empty Animation.
    */

    public Animation(JFrame window, int x, int y) {
	this.window = window;
        frames = new ArrayList<AnimFrame>();	// animation is a collection of frames        	totalDuration = 0;
	active = 0;				// keeps track of how many animations have completed
	soundManager = SoundManager.getInstance();	
    this.x=x;
    this.y=y;
						// get reference to Singleton instance of SoundManager
    
    }


    /**
        Adds an image to the animation with the specified
        duration (time to display the image).
    */

    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }


    /**
        Starts this animation over from the beginning.
    */

    public synchronized void start() {

	active = 1;				// 1 indicates first animation sequence
    animTime = 0;				// reset time animation has run for, to zero
    currFrameIndex = 0;			// reset current frame to first fram,e
	startTime = System.currentTimeMillis();	// reset start time to current time
    
    }


    /**
        Updates this animation's current image (frame), if
        neccesary.
    */

    public synchronized void update() {


        long currTime = System.currentTimeMillis();
						// find the current time
	long elapsedTime = currTime - startTime;	
						// find how much time has elapsed since last update
	startTime = currTime;			// set start time to current time

        if (frames.size() > 1) {
            animTime += elapsedTime;		// add elapsed time to time animation has run for
            if (animTime >= totalDuration) {			
		active = active + 1;
	
						// if the time animation has run for > total duration
                animTime = animTime % totalDuration;	
						//    reset time animation has run for
                currFrameIndex = 0;		//    reset current frame to first frame
            }

            while (animTime > getFrame(currFrameIndex).endTime) {
                currFrameIndex++;		// set frame corresponding to time animation has run for
            }
        }

        dimension = window.getSize();


    }


    /**
        Gets this Animation's current image. Returns null if this
        animation has no images.
    */

    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        }
        else {
            return getFrame(currFrameIndex).image;
        }
    }


    public void draw (Graphics2D g2,int x,int y) {		
	if (active == 0)
		return;

        g2.drawImage(getImage(), x, y, XSIZE, YSIZE, null);
    }


    public int getNumFrames() {			
	return frames.size();
    }


    private AnimFrame getFrame(int i) {		
        return frames.get(i);
    }


    private class AnimFrame {			// inner class for the frames of the animation

        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }


    public void playSound() {
	soundManager.playSound("birdSound", true);
    }


    public void stopSound() {
	soundManager.stopSound("birdSound");
    }

}
