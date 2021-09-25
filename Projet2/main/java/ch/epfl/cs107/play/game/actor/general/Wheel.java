/*
 *	Author:      Elia Saquand
 *	Date:        10.10.2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Constraint;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.math.WheelConstraint;

public class Wheel extends GameEntity implements Actor {
	private final static String nomImage = "spinner.dead.png"; 
	private ImageGraphics graphics;
	private WheelConstraint constraint;
	private Entity vehicle;
	private float timer; // pas la peine de l'initialiser à zero ?

	public Wheel(ActorGame game, Vector position, float rayon) {
		super(game, false, position);
		if (rayon < 0) {
			throw new IllegalArgumentException("le rayon d'un cercle doit être strictement positif");
		}

		PartBuilder partBuilder = getEntity().createPartBuilder();
		Circle circle = new Circle(rayon);
		partBuilder.setFriction(10.0f); // voir si vrmt utile
		partBuilder.setShape(circle);
		partBuilder.build();

		graphics = new ImageGraphics(nomImage, 2.0f * rayon, 2.0f * rayon, new Vector(0.5f, 0.5f));
		graphics.setParent(getEntity());

		game.ajouterActor(this);
	}

	public void attach(Entity vehicle, Vector anchor, Vector axis) {
		this.vehicle = vehicle;

		WheelConstraintBuilder constraintBuilder = getOwner().createWheelConstraintBuilder();
		constraintBuilder.setFirstEntity(vehicle);
		// point d'ancrage du véhicule :
		constraintBuilder.setFirstAnchor(anchor);
		// Entity associée à la roue :
		constraintBuilder.setSecondEntity(getEntity());
		// point d'ancrage de la roue (son centre) :
		constraintBuilder.setSecondAnchor(Vector.ZERO);
		// axe le long duquel la roue peut se déplacer :
		constraintBuilder.setAxis(axis);
		// fréquence du ressort associé
		constraintBuilder.setFrequency(3.0f);
		constraintBuilder.setDamping(0.5f);
		// force angulaire maximale pouvant être appliquée
		// à la roue pour la faire tourner :
		constraintBuilder.setMotorMaxTorque(10.0f);
		constraint = constraintBuilder.build();
	}

	public void power(float speed) {
		constraint.setMotorEnabled(true);
		constraint.setMotorSpeed(speed);
	}

	public void destroy() {
		if (constraint != null) {
			detach();
		}
		super.destroy();
		getOwner().supprimerActor(this);
	}

	public void relax() {
		constraint.setMotorEnabled(false);
	}

	public void detach() {
		constraint.destroy();
	}

	/**
	 * @return relative rotation speed , in radians per second
	 */
	public float getSpeed() {
		if (constraint != null) { // car elle éventuellement pas attaché à un véhicule
			return Math.abs(vehicle.getAngularVelocity() - getEntity().getAngularVelocity());
		} else {
			return getEntity().getAngularVelocity();
		}
	}

	public void update(float deltaTime) {
		timer += deltaTime;
		while (timer > 0.1) {
			// spawn a star if horizontal bike velocity is higher then 10.f
			if (getOwner().getPlayLoad().getVelocity().getLength() > 11.5f) {
				Star star = new Star(getOwner(),
						new Vector(getEntity().getPosition().getX() - 0.5f, getEntity().getPosition().getY() - 0.5f));
			}
			// spawn second star if horizontal bike veloctiy is higher then 14.5f
			if (getOwner().getPlayLoad().getVelocity().getLength() > 17f) {
				Star star = new Star(getOwner(),
						new Vector(getEntity().getPosition().getX(), getEntity().getPosition().getY() - 0.5f));
			}
			// spawn second star if horizontal bike veloctiy is higher then 18.ff
			if (getOwner().getPlayLoad().getVelocity().getLength() > 20f) {
				Star star = new Star(getOwner(),
						new Vector(getEntity().getPosition().getX(), getEntity().getPosition().getY() - 0.5f));
			}
			// reset timer
				timer = 0;
			
		}
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

}
