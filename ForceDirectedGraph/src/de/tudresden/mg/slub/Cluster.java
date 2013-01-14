package de.tudresden.mg.slub;

import java.util.ArrayList;


import toxi.geom.*;
import toxi.physics2d.*;

public class Cluster 
{
	private ForceDirectedGraph parent;
	
	Cluster(ForceDirectedGraph p)
	{
		parent = p;
	}
	
	// A cluster is a grouping of nodes
	  ArrayList<Node> nodes;

	  float diameter;

	  // We initialize a Cluster with a number of nodes, a diameter, and centerpoint
	  Cluster(ForceDirectedGraph p, int numberOfNodes, float clusterDiameter, Vec2D center)
	  {
		  parent = p;

	    // Initialize the ArrayList
	    nodes = new ArrayList<Node>();

	    // Set the diameter
	    diameter = clusterDiameter;

	    // Create the nodes
	    for (int i = 0; i < numberOfNodes; i++) {
	      // We can't put them right on top of each other
	      nodes.add(new Node(p, center.add(Vec2D.randomVector())));
	    }
	    
	    // Connect all the nodes with a Spring
	    for (int i = 1; i < nodes.size(); i++) {
	      VerletParticle2D pi = (VerletParticle2D) nodes.get(i);
	      for (int j = 0; j < i; j++) {
	        VerletParticle2D pj = (VerletParticle2D) nodes.get(j);
	        // A Spring needs two particles, a resting length, and a strength
	        parent.physics.addSpring(new VerletSpring2D(pi,pj,diameter,0.01f));
	      }
	    }
	  }

	  void display() {
	    // Show all the nodes
	    for (int i = 0; i < nodes.size(); i++) {
	      Node n = (Node) nodes.get(i);
	      n.display();
	    }
	  }

	  // This function connects one cluster to another
	  // Each point of one cluster connects to each point of the other cluster
	  // The connection is a "VerletMinDistanceSpring"
	  // A VerletMinDistanceSpring is a string which only enforces its rest length if the 
	  // current distance is less than its rest length. This is handy if you just want to
	  // ensure objects are at least a certain distance from each other, but don't
	  // care if it's bigger than the enforced minimum.
	  void connect(Cluster other) {
	    ArrayList<Node> otherNodes = other.getNodes();
	    for (int i = 0; i < nodes.size(); i++) {
	      VerletParticle2D pi = (VerletParticle2D) nodes.get(i);
	      for (int j = 0; j < otherNodes.size(); j++) {
	        VerletParticle2D pj = (VerletParticle2D) otherNodes.get(j);
	        // Create the spring
	        parent.physics.addSpring(new VerletMinDistanceSpring2D(pi,pj,(diameter+other.diameter)*0.5f,0.05f));
	      }
	    }
	  }


	  // Draw all the internal connections
	  void showConnections() {
	    parent.stroke(0,150);
	    for (int i = 0; i < nodes.size(); i++) {
	      VerletParticle2D pi = (VerletParticle2D) nodes.get(i);
	      for (int j = i+1; j < nodes.size(); j++) {
	        VerletParticle2D pj = (VerletParticle2D) nodes.get(j);
	        parent.line(pi.x,pi.y,pj.x,pj.y);
	      }
	    }
	  }

	  // Draw all the connections between this Cluster and another Cluster
	  void showConnections(Cluster other) {
	    parent.stroke(0,50);
	    ArrayList<Node> otherNodes = other.getNodes();
	    for (int i = 0; i < nodes.size(); i++) {
	      VerletParticle2D pi = (VerletParticle2D) nodes.get(i);
	      for (int j = 0; j < otherNodes.size(); j++) {
	        VerletParticle2D pj = (VerletParticle2D) otherNodes.get(j);
	        parent.line(pi.x,pi.y,pj.x,pj.y);
	      }
	    }
	  }

	  ArrayList<Node> getNodes() {
	    return nodes;
	  }

}
