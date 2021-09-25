/*
 *	Author:      Elia Saquand
 *	Date:        10.10.2017
 */

package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.general.Trigger;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;

public class Finish extends Trigger {
	private final static String nomImage = "flag.red.png";
	public Finish(ActorGame game, Vector position) {
		super(game,true, position); // queles positions mettre?, la mettre en fixed ou pas?

		setGraphics(new ImageGraphics(nomImage, 1, 1));
		partBuilder();
	}

	// construit le corps physique de l'objet
	private void partBuilder() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(1.0f, 0.0f), new Vector(1.0f, 3.0f),
				new Vector(0.0f, 3.0f));
		partBuilder.setShape(polygon);
		partBuilder.setGhost(true);
		partBuilder.build();
	}

	public void update(float deltaTime) {
		if (isHit()) {
			((BikeGame) getOwner()).setVictory();
		}
	}
}
