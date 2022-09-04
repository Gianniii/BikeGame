package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.general.Trigger;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

abstract public class Coin extends Trigger {
	private final int pointValue;


	public Coin(ActorGame game, Vector position, String image, int value) {
		super(game,true, position);

		//set the value for the collectable
		pointValue = value;
		
		partBuilder();
		setGraphics(new ImageGraphics(image, 1, 1));
	
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
		// if gets hit destroy itself and updates the score
		if (isHit()) {
			 bike.rise();
			((BikeGame) getOwner()).updateScore(pointValue);
			destroy();
		} 
		super.update(deltaTime); 
	}
}
