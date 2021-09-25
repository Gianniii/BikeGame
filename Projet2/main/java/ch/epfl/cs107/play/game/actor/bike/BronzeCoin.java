package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

public class BronzeCoin extends Coin {
	private final static String nomImage ="coin.bronze.png";

	public BronzeCoin(ActorGame game, Vector position) {
		super(game, position, nomImage, 1);
	}
}