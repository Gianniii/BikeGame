package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

public class DiamondCoin extends Coin {
	private final static String nomImage = "coin.diamond.png";
			
	public DiamondCoin(ActorGame game, Vector position) {
		super(game, position, nomImage, 20);
	}
}