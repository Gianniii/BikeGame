/*
 *	Author:      Elia Saquand
 *	Date:        10.10.2017
 */

package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.general.Trigger;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;

public class Spikes extends Trigger implements Actor {
	private final static String nomImage ="spikes.png";
	private ImageGraphics graphics;
	TextGraphics message;

	public Spikes(ActorGame game, Vector position) {
		super(game, true, position);

		// set grapics for finish line
		setGraphics(new ImageGraphics(nomImage, 2.f, 1.25f));
		setDepth(-1);
		// build the part for the finish line
		partBuilder();
	}

	// construit le corps physique de L'objet
	public void partBuilder() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(2.f, 0.0f), new Vector(2.f, 1.25f),
				new Vector(0.0f, 1.25f));
		partBuilder.setShape(polygon);
		partBuilder.setGhost(true);
		partBuilder.build();
	}

	public void update(float delaTime) {
		// detection de la chute et destruction du velo object si il y a une chute
		if (isHit()) {
			((BikeGame) getOwner()).setBikeIsDead(true);
			((BikeGame) getOwner()).getPlayLoad().destroy();// destroy bike
		}
	}
}