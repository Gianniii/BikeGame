package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.AngrySun;
import ch.epfl.cs107.play.game.actor.general.FrostySun;
import ch.epfl.cs107.play.game.actor.general.SlipperyBlock;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.general.WreckingBall;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

public class Level2 extends Level {
	private Crate crate1, crate2, crate3;
	private Coin coin1, coin2, coin3, coin4, coin5, coin6, coin7;
	private Terrain terrain;
	private Bike bike;
	private Spikes spikes1, spikes2, spikes3, spikes4, spikes5, spikes6, spikes7, spikes8, spikes9, spikes10, spikes11,
			spikes12, spikes13, spikes14, spikes15, spikes16, spikes17, spikes18, spikes19, spikes20, spikes21,
			spikes22;
	private Finish finish;
	private ActorGame game;
	private Polyline polyline;
	private SlipperyBlock slipperyBlock;
	private WreckingBall wreckingBall1, wreckingBall2, wreckingBall3;
	private Vector bikePosition;
	private FrostySun frostySun1, frostySun2;
	private Heart heart1, heart2;

	public Level2(ActorGame game) {
		super(game);
		this.game = game;
	}

	// créer les object dur monde simulé
	public void createAllActors() {
		generateTerrainShape();

		coin1 = new GoldCoin(game, new Vector(104.0f, 6.0f));
		coin2 = new SilverCoin(game, new Vector(110.0f, 4.0f));
		coin3 = new DiamondCoin(game, new Vector(113.0f, 0.0f));
		coin4 = new SilverCoin(game, new Vector(190.0f, -23.0f));
		coin5 = new GoldCoin(game, new Vector(198.0f, -25.0f));
		coin6 = new SilverCoin(game, new Vector(47.5f, 8.3f));
		coin7 = new DiamondCoin(game, new Vector(258.0f, -30.0f));

		heart1 = new Heart(game, new Vector(222.f, -39.f));
		heart2 = new Heart(game, new Vector(155.f, -39.f));
		
		spikes1 = new Spikes(game, new Vector(39.f, 3.0f));
		spikes2 = new Spikes(game, new Vector(41.f, 3.0f));
		spikes3 = new Spikes(game, new Vector(43.f, 3.0f));
		spikes4 = new Spikes(game, new Vector(53.f, 3.0f));
		spikes5 = new Spikes(game, new Vector(55.f, 3.0f));
		spikes6 = new Spikes(game, new Vector(57.f, 3.0f));
		spikes7 = new Spikes(game, new Vector(98.f, -50.0f));
		spikes8 = new Spikes(game, new Vector(100.f, -50.0f));
		spikes9 = new Spikes(game, new Vector(102.f, -50.0f));
		spikes10 = new Spikes(game, new Vector(104.f, -50.0f));
		spikes11 = new Spikes(game, new Vector(114.f, -50.0f));
		spikes12 = new Spikes(game, new Vector(106.f, -50.0f));
		spikes13 = new Spikes(game, new Vector(108.f, -50.0f));
		spikes14 = new Spikes(game, new Vector(110.f, -50.0f));
		spikes15 = new Spikes(game, new Vector(112.f, -50.0f));
		spikes16 = new Spikes(game, new Vector(114.f, -50.0f));
		spikes17 = new Spikes(game, new Vector(242.f, -35.0f));
		spikes18 = new Spikes(game, new Vector(244.f, -35.0f));
		spikes19 = new Spikes(game, new Vector(246.f, -35.0f));
		spikes20 = new Spikes(game, new Vector(248.f, -35.0f));
		spikes21 = new Spikes(game, new Vector(250.f, -35.0f));
		spikes22 = new Spikes(game, new Vector(240.f, -35.0f));
		wreckingBall1 = new WreckingBall(game, new Vector(20.0f, 18.0f));
		slipperyBlock = new SlipperyBlock(game, new Vector(44.0f, 7.f), 8.f, 1.f);
		finish = new Finish(game, new Vector(260.0f, -30.0f));
		bike = new Bike(game, new Vector(4.0f, 5.0f));
		terrain = new Terrain(game, polyline, Color.darkGray, Color.GRAY);
		frostySun1 = new FrostySun(game, new Vector(83.0f, 4.0f));
		frostySun2 = new FrostySun(game, new Vector(155.f, -33.0f));
		
		game.setViewCandidate(bike);
		game.setPlayLoad(bike);
	}
	
	private void generateTerrainShape() {
		polyline = new Polyline(-300.0f, -50.0f, -30.0f, 40.f, -9.f, 40.f, 0.0f, 4.0f, 10.0f, 4.0f, 13.0f, 6.0f, 19.0f,
				10.0f, 20.0f, 10.0f, 30.0f, 4.0f, 34.0f, 4.0f, 39.0f, 8.0f, 39.0f, 3.0f, 44.0f, 3.0f, 44.0f, 7.0f,
				52.0f, 7.0f, 52.0f, 8.0f, 53.0f, 8.5f, 53.0f, 3.0f, 58.0f, 3.0f, 58.0f, 7.0f, 65.0f, 3.0f, 67.0f, 3.0f,
				80.0f, -4.0f, 82.0f, -5.0f, 85.0f, -5.0f, 90.0f, -2.0f, 98.0f, 5.0f, 98.0f, -50.0f, 117.0f, -50.0f,
				117.0f, -15.0f, 135.0f, -28.0f, 140.0f, -35.0f, 150.0f, -40.0f, 165.0f, -40.0f, 175.0f, -35.0f, 190.0f,
				-25.0f, 220.0f, -40.0f, 225.0f, -40.0f, 240.0f, -30.0f, 240.0f, -35.0f, 250.5f, -35.0f, 250.5f, -30.0f,
				280.0f, -30.0f, 265.0f, 50.f,

				1000.0f, -300.0f);
	}

}
