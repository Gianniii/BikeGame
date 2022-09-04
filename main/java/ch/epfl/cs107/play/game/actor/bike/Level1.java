package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Level;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.general.Background;
import ch.epfl.cs107.play.game.actor.general.Bascule;
import ch.epfl.cs107.play.game.actor.general.Decor;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.general.Trigger;
import ch.epfl.cs107.play.game.actor.general.WreckingBall;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Level1 extends Level {
	private Coin coin1, coin2, coin3, coin4, coin5;
	private Terrain terrain;
	private Bike bike;
	private Bascule bascule;
	private Trigger finish;
	private ActorGame game;
	private Spikes spikes1, spikes2, spikes3;
	private Polyline polyline;
	private WreckingBall wreckingBall;
	private Background background;
	private Decor bat1, bat2, torche1, torche2;

	public Level1(ActorGame game) {
		super(game);
		this.game = game;
	}

	// créer les object dur monde simulé
	public void createAllActors() {
		generateTerrainShape();
		coin1 = new GoldCoin(game, new Vector(49.5f, 1.5f));
		coin2 = new SilverCoin(game, new Vector(60.5f, 0.0f));
		coin3 = new SilverCoin(game, new Vector(72.8f, -4.2f));
	    coin4 = new DiamondCoin(game, new Vector(82.5f, -2.f));
		coin5 = new GoldCoin(game, new Vector(9.0f, 4.0f));
		finish = new Finish(game, new Vector(85.0f, -2.0f));
		bike = new Bike(game, new Vector(5.0f, 4.0f));
		terrain = new Terrain(game, polyline, Color.darkGray, Color.GRAY);
		spikes1 = new Spikes(game, new Vector(47.0f, -6.0f));
		spikes2 = new Spikes(game, new Vector(49.0f, -6.0f));
		spikes3 = new Spikes(game, new Vector(51.0f, -6.0f));
		bascule = new Bascule(game, new Vector(72.f, -6.7f));
		wreckingBall = new WreckingBall(game, new Vector(68.0f, 3.7f));
		bat1 = new Decor(game, true, new Vector(75f, 4.0f), "bat.left.dead.png", 1, 1);
		bat2 = new Decor(game, true, new Vector(80f, 2.0f), "bat.right.dead.png", 1, 1);
		// background = new Background(game);
		torche1 = new Decor(game, true, new Vector(55f, 4.0f), "torch.lit.1.png", 1, 1);
		torche2 = new Decor(game, true, new Vector(20f, 4.0f), "torch.lit.2.png", 1, 1);

		game.setViewCandidate(bike);
		game.setPlayLoad(bike);
	}

	// creates the polyline that we will use to for the construction of the terrain
		private void generateTerrainShape() {
			polyline = new Polyline(
			   -30.0f, -30.0f,
			   -30.0f, -11.0f,
			   4.0f, 30.f,
			   100.f, 6.5f,
			   4.0f, 6.5f,
			   4.0f, 1.0f,
			   15.0f, 3.0f,
			   16.0f, 3.0f,
			   25.0f, 1.0f,
			   35.0f, -5.0f,
			   40.0f, -5.0f,
			   47.0f, 0.0f,
			   47.0f, -6.0f,
			   53.0f, -6.0f,
			   53.0f, 0.0f,
			   58.0f, 0.0f,
			   66.0f,-6.0f,
			   78.0f, -6.0f,
			   80.0f, -2.0f,
			   90.0f, -2.0f,
			   90.0f,10.0f,
			   150.0f, -30.0f
			   );
		}
}
