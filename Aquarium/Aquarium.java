import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Aquarium extends Frame implements Runnable {

    MediaTracker tracker;
    Image aquariumImage, memoryImage;
    Graphics memoryGraphics;
    Image[] fishImages = new Image[2];
    Thread thread;
    int numberFish = 8;
    int sleepTime = 110;
    Vector<Fish> fishes = new Vector<Fish>();
    boolean runOk = true;
    
    public Aquarium(){
        this.setTitle("The Aquarium");

        tracker = new MediaTracker(this);

        fishImages[0] = Toolkit.getDefaultToolkit().getImage("fish1.gif"); //fish1.gif");
        tracker.addImage(fishImages[0], 0);

        fishImages[1] = Toolkit.getDefaultToolkit().getImage("fish2.gif");//fish2.gif");
        tracker.addImage(fishImages[1], 0);

        aquariumImage = Toolkit.getDefaultToolkit().getImage("bubbles.jpg"); //bubbles.jpg");
        tracker.addImage(aquariumImage, 0);

        try{
            tracker.waitForID(0);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        this.setSize(aquariumImage.getWidth(this), aquariumImage.getHeight(this));

        this.setResizable(false);
        this.setVisible(true);

        memoryImage = createImage(getSize().width, getSize().height);
        memoryGraphics = memoryImage.getGraphics();

        thread = new Thread(this);
        thread.start();

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent windowEvent){
                runOk = false;
                System.exit(0);
            }
          }
        );

        /* using java 8 lambda notation */
        // this.addWindowListener((windowEvent)-> System.exit(0)); 
    }

    @Override
    public void run(){

       for(int loopIndex =0; loopIndex < numberFish; loopIndex++){
        Rectangle edges = new Rectangle(0 + getInsets().left, 0
                + getInsets().top, getSize().width - (getInsets().left
                + getInsets().right), getSize().height - (getInsets().top
                + getInsets().bottom));
        

            fishes.add(new Fish(fishImages[0], fishImages[1], edges, this));
            try {
                Thread.sleep(20);
            }
            catch(Exception exp){

                System.out.println(exp.getMessage());
            }

        }        

        Fish fish;

        while(runOk){

            for(int loopIndex = 0; loopIndex < numberFish; loopIndex++){
                fish = (Fish) fishes.elementAt(loopIndex);
                fish.swim();
            }
            
            try{
                Thread.sleep(sleepTime);

            }
            catch(Exception exp){

                System.out.println(exp.getMessage());
            }

            repaint();
        }
    }

    @Override
    public void update(Graphics g){
        memoryGraphics.drawImage(aquariumImage, 0,0, this);
        for(int loopIndex =0; loopIndex < numberFish; loopIndex++){
            ((Fish)fishes.elementAt(loopIndex)).drawFishImage(memoryGraphics);
        }

        g.drawImage(memoryImage,0,0,this);
    }

    public static void main(String[] args){
        new Aquarium();
    }

}


