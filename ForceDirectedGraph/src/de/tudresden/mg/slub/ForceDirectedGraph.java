package de.tudresden.mg.slub;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import processing.core.*;
import toxi.geom.*;
import toxi.physics2d.*;

@SuppressWarnings("serial")
public class ForceDirectedGraph extends PApplet 
{
	private int amountOfClusters = 3;
	private Cluster nearestCluster = null;
	
	// Reference to physics world
	public VerletPhysics2D physics;

	// A list of cluster objects
	ArrayList<Cluster> clusters;

	// Boolean that indicates whether we draw connections or not
	boolean showPhysics = true;
	boolean showParticles = true;

	// Font
	PFont f;
	
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", "de.tudresden.mg.slub.ForceDirectedGraph" });
	  }


	public void setup() {
	  size(600,600);
	  smooth();
	  frameRate(30);
	  f = createFont("Georgia",12,true);

	  // Initialize the physics
	  physics=new VerletPhysics2D();
	  physics.setWorldBounds(new Rect(10,10,width-20,height-20));

	  // Spawn a new random graph
	  newGraph();
	}

	// Spawn a new random graph
	private void newGraph() {

	  // Clear physics
	  physics.clear();

	  // Create new ArrayList (clears old one)
	  clusters = new ArrayList<Cluster>();

	  // Create 8 random clusters
	  for (int i = 0; i < amountOfClusters; i++) 
	  {
	    Vec2D center = new Vec2D(width/2,height/2);
	    
	    
	    clusters.add(new Cluster(this, (int)random(3,8),(float)random(20,100),center));
	  }

	  //	All clusters connect to all clusters	
	  for (int i = 0; i < clusters.size(); i++) {
	    for (int j = i+1; j < clusters.size(); j++) {
	      Cluster ci = (Cluster) clusters.get(i);
	      Cluster cj = (Cluster) clusters.get(j);
	      ci.connect(cj);
	    }
	  }
	}

	public void draw() {

	  // Update the physics world
	  physics.update();

	  background(255);

	  // Display all points
	  if (showParticles) {
	    for (int i = 0; i < clusters.size(); i++) {
	      Cluster c = (Cluster) clusters.get(i);
	      c.display();
	    }
	  }

	  // If we want to see the physics
	  if (showPhysics) {
	    for (int i = 0; i < clusters.size(); i++) {
	      // Cluster internal connections
	      Cluster ci = (Cluster) clusters.get(i);
	      ci.showConnections();

	      // Cluster connections to other clusters
	      for (int j = i+1; j < clusters.size(); j++) {
	        Cluster cj = (Cluster) clusters.get(j);
	        ci.showConnections(cj);
	      }
	    }
	  }

	  // Instructions
	  fill(0);
	  textFont(f);
	  text("'p' to display or hide particles\n'c' to display or hide connections\n'n' for new graph\n'2'-'8' to create new graph with specified no. of clusters",10,20);
	}

	// Key press commands
	public void keyPressed() 
	{
		if(key == '2' || key == '3' || key == '4' || key == '5' || key == '6' || key == '7' || key == '8')
		{
			amountOfClusters = Integer.parseInt(Character.toString(key));
			newGraph();
			//System.out.println("amount of clusters changed to " + amountOfClusters);
		}
		else if (key == 'c') {
		  showPhysics = !showPhysics;
		  if (!showPhysics) showParticles = true;
		} 
		else if (key == 'p') {
		  showParticles = !showParticles;
		  if (!showParticles) showPhysics = true;
		} 
		else if (key == 'n') {
		  newGraph();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseMoved(e);
		//findNearestCluster(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		findNearestCluster(e);
	}
	
	private void findNearestCluster(MouseEvent e)
	{
		// lazy init with cluster 0
		if(nearestCluster == null)
			nearestCluster = this.clusters.get(0);
		
		int nearestClusterIndex = -1;
		float distanceToNearestCluster = Float.MAX_VALUE;
		
		for(int i = 0; i < this.clusters.size(); i++)
		{
			Cluster c = this.clusters.get(i);
			
			
			
			float distanceToClusterCenter = dist(c.getCenter().x, c.getCenter().y, e.getX(), e.getY());
			System.out.println("Distance to cluster " + i + " = " + distanceToClusterCenter);
			if(distanceToClusterCenter < distanceToNearestCluster)
			{
				nearestCluster = c;
				nearestClusterIndex = i;
				//System.out.println("Nearest cluster index = " + nearestClusterIndex);
				

			}
		}
		
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			// color nearest cluster white, all others gray
			for(Cluster c : clusters)
			{
				for(Node n : c.getNodes())
				{
					n.setGrayValue(0);
				}
			}
			
			for(Node n : clusters.get(nearestClusterIndex).getNodes())
			{
				n.setGrayValue(255);
			}
		}

		
		
		//System.out.println("mouse moved; position = (" + e.getX() + "," + e.getY() + ")");
	}
	
	
}
