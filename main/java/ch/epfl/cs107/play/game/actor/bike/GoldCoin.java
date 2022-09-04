package ch.epfl.cs107.play.game.actor.bike;

	import ch.epfl.cs107.play.game.actor.ActorGame;
	import ch.epfl.cs107.play.math.Transform;
	import ch.epfl.cs107.play.math.Vector;

	public class GoldCoin extends Coin {
		private final static String nomImage = "coin.gold.png";
		
		public GoldCoin(ActorGame game, Vector position) {
			super(game, position, "coin.gold.png", 10);
		}
}