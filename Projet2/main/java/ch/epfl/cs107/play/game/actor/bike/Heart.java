/*
s *	Author:      Elia Saquand
 *	Date:        10.10.2017
 */

package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.general.Trigger;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Vector;

public class Heart extends Trigger{
	private final static String nomImage = "heart.full.png";
			
	 Heart(ActorGame game, Vector position) {
		super(game,true , position);
		
		partBuilder();
		setGraphics(new ImageGraphics("heart.full.png", 1, 1));
	
	}

	// construit le corp physique de L'objet
	private void partBuilder() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Circle circle = new Circle(1.0f);
		partBuilder.setShape(circle);
		partBuilder.setGhost(true);
		partBuilder.build();
	}

	public void update(float deltaTime) {
		Bike bike = (Bike) getOwner().getPlayLoad();
		// if gets hit destroy itself and updates the lives
		if (isHit()) {
			((BikeGame) getOwner()).updateLives(true);
			destroy();
		} 
		super.update(deltaTime); 
	}
}
