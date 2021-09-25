package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Star extends GameEntity implements Actor {
	private final static String nomImage = "star.png";
	private ImageGraphics graphics;
	private float timer = 0;
	private final float rayon = 0.2f;
	// random vertical force between 30 and 10
	float randomVerticalForce = (float) Math.random() * 40 + 10;
	// random horizontal force between 20 and 5
	float randomHorizontalForce = (float) Math.random() * 30 + 5;

	public Star(ActorGame game, Vector position) {
		super(game, false, position);

		PartBuilder partBuilder = getEntity().createPartBuilder();
		Circle circle = new Circle(rayon + 0.5f);
		partBuilder.setShape(circle);
		partBuilder.setGhost(true);
		partBuilder.build();

		getEntity().setAngularVelocity(5.f);

		graphics = new ImageGraphics(nomImage, 2.0f * rayon, 2.0f * rayon, new Vector(0.5f, 0.5f));
		graphics.setParent(getEntity());
		game.ajouterActor(this);
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	public void destroy() {
		super.destroy();
		getOwner().supprimerActor(this);
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
		// wants them to dissapear after 0.4 seconds
		if (timer > 0.4) {
			destroy();
			getOwner().supprimerActor(this);
		}
		if (getOwner().getPlayLoad().getVelocity().getX() > 0) {
			getEntity().applyForce(new Vector(randomHorizontalForce, randomVerticalForce), new Vector(0.0f, 1.0f));
		}
		if (getOwner().getPlayLoad().getVelocity().getX() < 0) {
			getEntity().applyForce(new Vector(-randomHorizontalForce, randomVerticalForce), Vector.ZERO);
		}
	}
}


