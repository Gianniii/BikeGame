package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;

public class BaseBlock extends GameEntity implements Actor {
	private final static String nomImage ="stone.broken.4.png";
	private ImageGraphics graphics;
	private float length;
	private float height;

	public BaseBlock(ActorGame game, Vector position, float length, float height) {
		super(game, true, position);
		if (length < 0 || height < 0) {
			throw new IllegalArgumentException("La hauteur et la longueur doivent être strictement positive");
		}
		
		this.length = length;
		this.height = height;
		graphics = new ImageGraphics(nomImage, length, height);
		graphics.setParent(getEntity());
		partBuilder(length, height);

		game.ajouterActor(this);
	}

	// construit le corps physique de L'objet
	public void partBuilder(float length, float height) {
		PartBuilder partBuilder = getEntity().createPartBuilder();

		// Create a polygon for the block
		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(length, 0.0f), new Vector(length, height),
				new Vector(0.0f, height));
		partBuilder.setShape(polygon);
		partBuilder.build();
	}

	public void buildRevoluteConstraint(Entity plank) {
		RevoluteConstraintBuilder revoluteConstraintBuilder = getOwner().getRevoluteConstraintBuilder();
		revoluteConstraintBuilder.setFirstEntity(getEntity());
		revoluteConstraintBuilder.setFirstAnchor(new Vector(length / 2, (1.0f * 7) / 4));
		revoluteConstraintBuilder.setSecondEntity(plank);
		revoluteConstraintBuilder.setSecondAnchor(new Vector(8.0f / 2, 0.2f / 2));
		revoluteConstraintBuilder.setInternalCollision(true);
		revoluteConstraintBuilder.build();
	}

	public void buildRopeConstraint(Entity ball) {
		RopeConstraintBuilder ropeConstraintBuilder = getOwner().getRopeConstraintBuilder();
		ropeConstraintBuilder.setFirstEntity(getEntity());
		ropeConstraintBuilder.setFirstAnchor(new Vector(1.0f / 2, 1.0f / 2));
		ropeConstraintBuilder.setSecondEntity(ball);
		ropeConstraintBuilder.setSecondAnchor(Vector.ZERO);
		ropeConstraintBuilder.setMaxLength(6.0f);
		ropeConstraintBuilder.setInternalCollision(true);
		ropeConstraintBuilder.build();
	}

	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
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
}
