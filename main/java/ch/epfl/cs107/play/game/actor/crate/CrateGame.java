package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.math.Entity;

 
public class CrateGame extends ActorGame {
  private Crate crate1, crate2, crate3;

   
  public boolean begin(Window window, FileSystem fileSystem) {
	   super.begin(window, fileSystem);
	   
	   crate1 = new Crate(this, false, new Vector(0.0f, 5.0f),"box.4.png", 1, 1); 
	   crate2 = new Crate(this, false, new Vector(0.2f, 7.0f),"box.4.png", 1, 1);
	   crate3 = new Crate(this, false, new Vector(2.0f, 6.0f),"box.4.png",1, 1);
	  
		return true;
   }
}
