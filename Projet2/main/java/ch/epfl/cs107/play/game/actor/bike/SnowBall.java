/*
 *	Author:      Elia Saquand
 *	Date:        10.10.2017
 */

package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.general.Trigger;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SnowBall extends Trigger implements Actor {
	private final static String nomImage = "glass.11.png";
	private ImageGraphics graphics;
	private float timer = 0;
	private final float rayon = 0.4f;
	//random horizontal force between 5 and and -5
	private float randomHorizontalForce = (float) Math.random() * 5.f  - 5.f;

	public SnowBall(ActorGame game, Vector position) {
		super(game, false, position);

		PartBuilder partBuilder = getEntity().createPartBuilder();
		Circle circle = new Circle(rayon);
		partBuilder.setShape(circle);
		partBuilder.setGhost(true);
		partBuilder.build();

		getEntity().isRotationFixed();

		graphics = new ImageGraphics(nomImage, 2.0f * rayon, 2.0f * rayon, new Vector(0.5f, 0.5f));
		graphics.setParent(getEntity());
		graphics.setDepth(-1.f);
		
		game.ajouterActor(this);
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Canvas canvas) {
		graphics.draw(canvas);
	}

	public void update(float deltaTime) {
		timer += deltaTime;
		// make start dissapear after 0.3 seconds
		if (timer > 3.0) {
			destroy();  
		}

		// if has contact with the biker 
		if (isHit()) {
			((BikeGame) getOwner()).freeze();
			destroy(); 
		}

		getEntity().applyForce(new Vector(randomHorizontalForce, 0.0f), Vector.ZERO);
	}
}