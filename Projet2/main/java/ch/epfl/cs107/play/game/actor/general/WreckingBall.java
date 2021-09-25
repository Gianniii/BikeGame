package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class WreckingBall extends GameEntity implements Actor {
	private final static String nomImage ="metal.broken.11.png";
	private ImageGraphics graphics;
	private BaseBlock base;
	private float rayon = 0.8f;
	private Polyline rope;
	private ShapeGraphics ropeGraphics;

	public WreckingBall(ActorGame game, Vector position) {
		super(game, false, new Vector(position.getX() + 4, position.getY()));

		// set graphics for wrecking ball
		graphics = new ImageGraphics(nomImage, 2.0f * rayon, 2.0f * rayon, new Vector(0.5f, 0.5f));
		graphics.setParent(getEntity());

		base = new BaseBlock(game, position, 1.f, 1.f);

		partBuilder();

		// build the constraint between the base and the WreckingBall
		base.buildRopeConstraint(getEntity());
		game.ajouterActor(this);
	}

	// construit le corp physique de L'objet
	public void partBuilder() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Circle circle = new Circle(1.f);
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
		rope = new Polyline(getEntity().getPosition(), base.getTransform().onPoint(0.5f, 0.5f));
		ropeGraphics = new ShapeGraphics(rope, Color.white, Color.white, .2f, 1, -1);
		ropeGraphics.setParent(getEntity());
		canvas.drawShape(rope, Transform.I, Color.GRAY, Color.GRAY, 0.2f, 1.0f, -1);
	}

	public void destroy() {
		base.destroy();
		super.destroy();
		getOwner().supprimerActor(this);
	}
}