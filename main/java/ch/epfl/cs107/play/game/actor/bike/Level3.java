package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.AngrySun;
import ch.epfl.cs107.play.game.actor.general.Background;
import ch.epfl.cs107.play.game.actor.general.Bascule;
import ch.epfl.cs107.play.game.actor.general.Cloud;
import ch.epfl.cs107.play.game.actor.general.SlipperyBlock;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.general.WreckingBall;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

public class Level3 extends Level {
	private Crate crate1, crate2, crate3;
	private Coin coin1, coin2, coin3, coin4, coin5, coin6;
	private Terrain terrain;
	private Bike bike;
	private Finish finish;
	private ActorGame game;
	private Polyline polyline;
	private SlipperyBlock slipperyBlock;
	private WreckingBall wreckingBall1, wreckingBall2, wreckingBall3;
	private Vector bikePosition;
	private Bascule bascule1, bascule2;
	private Background background;
	private Cloud cloud1, cloud2, cloud3, cloud4, cloud5, cloud6, cloud7, cloud8, cloud9, cloud10;
	private Spikes spikes1, spikes2, spikes3, spikes4, spikes5, spikes6, spikes7, spikes8, spikes9;
	private AngrySun angrySun1, angrySun2, angrySun3, angrySun4;
	private Heart heart1, heart2, heart3;

	public Level3(ActorGame game) {
		super(game);

		this.game = game;
	}

	// créer les object dur monde simulé
	public void createAllActors() {
		generateTerrainShape();

		// spikes1 = new Spikes(game, new Vector(42.f, 3.0f));
		// crate1 = new Crate(game, false ,new Vector(13.0f, 6.f), "box.4.png", 1.f,
		// 1.f);

		finish = new Finish(game, new Vector(145.0f, 2.0f));
		bike = new Bike(game, new Vector(-17f, 25.0f)); // why if i place it on the same post it doesnt respawn ??
		terrain = new Terrain(game, polyline, Color.WHITE, Color.GRAY);
		// finish = new Finish(game, new Vector(36.f, 7.0f));
		background = new Background(game, Color.cyan);
		cloud2 = new Cloud(game, new Vector(-17f, 25.0f), 5.f, 4.f, 1.0f);
		cloud1 = new Cloud(game, new Vector(31.f, 16.f), 6.f, 4.f, 0.95f);
		cloud4 = new Cloud(game, new Vector(35.5f, -16.0f), 10.f, 4.0f, 1.0f);
		cloud3 = new Cloud(game, new Vector(27.0f, -17.0f), 10.f, 6.0f, 1.0f);
		cloud5 = new Cloud(game, new Vector(46.0f, 13.5f), 7.f, 5.f, 0.85f);
		cloud7 = new Cloud(game, new Vector(98.0f, -11.f), 4.0f, 3.0f, 1.f);
		cloud8 = new Cloud(game, new Vector(82.f, 2.5f), 6.f, 4.5f, 0.95f);
		cloud9 = new Cloud(game, new Vector(114.0f, -11.f), 7.0f, 4.0f, 1.f);

		// coin1 = new GoldCoin();
		coin2 = new SilverCoin(game, new Vector(32f, 16.f));
		coin3 = new GoldCoin(game, new Vector(38.f, 13.5f));
		coin4 = new GoldCoin(game, new Vector(44f, 11.f));
		// coin4 = new SilverCoin();
		coin5 = new DiamondCoin(game, new Vector(142.0f, 2.5f));

		angrySun1 = new AngrySun(game, new Vector(46.0f, 14.5f));
		angrySun2 = new AngrySun(game, new Vector(67.0f, 2.5f));
		angrySun3 = new AngrySun(game, new Vector(77.0f, 1.6f));
		// spikes that hidden under the clouds at the bottom of pitts
		spikes1 = new Spikes(game, new Vector(29.f, -15.0f));
		spikes2 = new Spikes(game, new Vector(31.f, -15.0f));
		spikes3 = new Spikes(game, new Vector(34.f, -15.0f));
		spikes4 = new Spikes(game, new Vector(37.f, -15.0f));
		spikes5 = new Spikes(game, new Vector(40.f, -15.0f));
		spikes6 = new Spikes(game, new Vector(43.f, -15.0f));
		spikes7 = new Spikes(game, new Vector(98.f, -10.f));
		spikes8 = new Spikes(game, new Vector(115.f, -10.0f));
		spikes9 = new Spikes(game, new Vector(118.f, -10.0f));

		heart1 = new Heart(game, new Vector(46f, 7.f));
		heart2 = new Heart(game, new Vector(80f, -4.f));

		bascule1 = new Bascule(game, new Vector(101.5f, 2.0f));
		bascule2 = new Bascule(game, new Vector(110.5f, 2.0f));
		game.setViewCandidate(bike);
		game.setPlayLoad(bike);

	}

	private void generateTerrainShape() {
		polyline = new Polyline(
			   -300.0f, -50.0f,
			   -30.0f, 100.f,
			   -20.f, 25.f,
			   10.f, 7.f,
			   13.f, 6.f,
			   18.0f, 7.0f,
			   29.0f, 14.0f,
			   29.0f, -15.0f,
			   44.5f, -15.0f,
			   44.5f, 6.0f,
			   60.0f, -5.0f,
			   85.0f, -5.0f,
			   90.f, -1.0f,
			   94.f, 2.0f,
			   98.f, 2.0f,
			   98.f, -10.f,
			   102.f, -10.f,
			   102.f, 2.0f,
			   115.f, 2.0f,
			   115.f, -10.f,
			   120.f, -10.f,
			   120.f, 2.0f,
			   130.f, 2.0f,
			   130.f, -2.0f,
			   134.2f, -2.f,
			   136.f, -2.0f,
			   140.2f, -2.0f,
			   140.2f, 2.0f,
			   160.f, 2.0f,
			   160.f, 50.f,
			   1000.0f, -300.0f
			   );
	}
}