/*
 *	Author:      Elia Saquand
 *	Date:        10.10.2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.bike.FireBall;
import ch.epfl.cs107.play.game.actor.bike.SnowBall;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class FrostySun extends GameEntity implements Actor {
	private final static String nomImage ="sun.gray.1.png";
	private final static String nomImage1 ="sun.gray.2.png";
	private ImageGraphics graphics;
	private float rayon;
	private float timer;

	public FrostySun(ActorGame game, Vector position) {
		super(game, true, position);
		
		rayon = 2.0f;
		graphics = new ImageGraphics(nomImage, 2.0f * rayon, 2.0f * rayon, new Vector(0.5f, 0.5f));
		graphics.setParent(getEntity());

		partBuilder();
		// add to list of actors
		game.ajouterActor(this);
	}

	// construit le corp physique de L'objet
	public void partBuilder() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Circle circle = new Circle(rayon);
		partBuilder.setFriction(10.0f);
		partBuilder.setShape(circle);
		partBuilder.build();
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
	public void destroy() {
		super.destroy();
		getOwner().supprimerActor(this);
	}
	
	public void update(float deltaTime) {
		timer += deltaTime;
		if (timer > 0.7) {
			// spawn a snowBall
			SnowBall snowBall = new SnowBall(getOwner(), getEntity().getPosition());
			// reset timer
			timer = 0;
		}
		// animate the sun by changing its graphics every 0.5 secs
		if (timer > 0.35) {
			graphics.setName(nomImage1);
		} else {
			graphics.setName(nomImage);
		}

	}
}

