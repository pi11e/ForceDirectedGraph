package de.tudresden.mg.slub;

import processing.core.PApplet;
import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;

public class Node extends VerletParticle2D 
{
	private PApplet parent;
	private int grayValue;
	
	Node(PApplet p, Vec2D pos) {
	    super(pos);
	    parent = p;
	    grayValue = 0;
	  }

	  // All we're doing really is adding a display() function to a VerletParticle
	  void display() {
	    parent.fill(this.grayValue,150); // first parameter is used as gray scale value; 0 is dark gray, 255 is white
	    parent.stroke(0);
	    parent.ellipse(x,y,16,16);
	  }
	  
	  /**
	   * Set a gray-scale value for the node's fill.
	   * @param gray A value between 0 (dark gray) and 255 (white)
	   */
	  public void setGrayValue(int gray)
	  {
		  this.grayValue = gray;
	  }

}
