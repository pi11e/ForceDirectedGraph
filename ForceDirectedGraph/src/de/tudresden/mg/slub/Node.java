package de.tudresden.mg.slub;

import processing.core.PApplet;
import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;

public class Node extends VerletParticle2D 
{
	private PApplet parent;
	
	
	Node(PApplet p, Vec2D pos) {
	    super(pos);
	    parent = p;
	  }

	  // All we're doing really is adding a display() function to a VerletParticle
	  void display() {
	    parent.fill(0,150);
	    parent.stroke(0);
	    parent.ellipse(x,y,16,16);
	  }

}
