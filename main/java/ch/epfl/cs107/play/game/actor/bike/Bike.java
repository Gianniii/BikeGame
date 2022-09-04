/*
 *	Author:      Elia Saquand
 *	Date:        10.10.2017
 */

package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Window;

public class Bike extends GameEntity implements Actor {
	private ImageGraphics graphics;
	private Wheel leftWheel;
	private Wheel rightWheel;
	/* vitesse seuil en dessous de laquelle il faut motoriser les roues
	 * pour les faire bouger
	 */
	private final static float MAX_WHEEL_SPEED = 20.0f;
	// indique s'il y a chute du cycliste
	private boolean hit;
	// indique si le cycliste regarde vers la droite ou vers la gauche
	private boolean versLaDroite = true;
	// différents graphiques permettant de dessiner le cycliste
	private ShapeGraphics armGraphics;
	private ShapeGraphics bodyGraphics;
	private ShapeGraphics headGraphics;
	private ShapeGraphics leftFacingthighGraphics;
	private ShapeGraphics leftLegGraphics;
	private ShapeGraphics rightLegGraphics;
	private ShapeGraphics thighGraphics;
	// indique quand les bras du cycliste sont levés
	private boolean rise; 
	// la distance parcourue par les pieds au cours de deltaTime
	private float footAngle;
	// permet de faire baisser les bras du cycliste après un certain temps
	private float armsTimer;

	public Bike(ActorGame game, Vector position) {
		super(game, false, position);
		((BikeGame) game).setStartingPoint(position);
		
		ContactListener listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if (contact.getOther().isGhost())
					return;
				if (leftWheel.isContact(contact) || rightWheel.isContact(contact)) 
					return;
				hit = true;
			}
			@Override
			public void endContact(Contact contact) {
			}
		};
		getEntity().addContactListener(listener);
		
		leftWheel = new Wheel(game, new Vector(position.getX() - 1.0f, position.getY()), 0.5f);
		rightWheel = new Wheel(game, new Vector(position.getX() + 1.0f, position.getY()), 0.5f);

		leftWheel.attach(getEntity(), new Vector(-1.0f, 0.0f), new Vector(-0.5f, -1.0f));
		rightWheel.attach(getEntity(), new Vector(1.0f, 0.0f), new Vector(0.5f, -1.0f));
		
		createHitBox();

		game.ajouterActor(this);
	}
    
	// permet de faire lever les bras au cycliste pendant un certain temps 
	public void rise() {
		rise = true;
		armsTimer = 0;
	}

	public Vector getPosition() {
		return getEntity().getPosition();
	}

	// Genere HitBox
	private void createHitBox() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Polygon polygon = new Polygon(0.0f, 0.5f, 0.5f, 1.0f, 0.0f, 2.0f, -0.5f, 1.0f);
		partBuilder.setGhost(true);
		partBuilder.setShape(polygon);
		partBuilder.build();
	}

	public float getAngularVelocity() {
		return getEntity().getAngularVelocity();
	}

	public void update(float deltaTime) {
		if (hit) { // teste si il y a chute
			((BikeGame) getOwner()).setBikeIsDead(true);
			destroy();
			getOwner().supprimerActor(this);// s'il y a chute il doit etre détruit
			return;
		}
		
		armsTimer += deltaTime;
		if (armsTimer > 0.5f) { // fais baisser les bras au cycliste
			rise = false;
		}
		
		// par defaut le moteur des roues est désactivé
		rightWheel.relax();
		leftWheel.relax();
        
		// change l'orientation du cycliste
		if (((Window) getOwner().getCanvas()).getKeyboard().get(KeyEvent.VK_SPACE).isReleased()) {
			versLaDroite = !versLaDroite;
		}
		
		// permet d'obtenir la distance parcourue par le pied au cours de deltaTime
		if (!versLaDroite) {
			footAngle = (footAngle - 1 * leftWheel.getSpeed() * deltaTime / 2) % (float) (2 * Math.PI);
		} else {
			footAngle = (footAngle + rightWheel.getSpeed() * deltaTime / 2) % (float) (2 * Math.PI);
		}

		// give power to the appropriate wheel when the up arrow is pressed
		if (((Window) getOwner().getCanvas()).getKeyboard().get(KeyEvent.VK_UP).isDown()) {
			if (!versLaDroite && rightWheel.getSpeed() < MAX_WHEEL_SPEED) { // j'ai mis les 2 conditions en 1
				rightWheel.power(MAX_WHEEL_SPEED);
			} else if (versLaDroite && leftWheel.getSpeed() > -MAX_WHEEL_SPEED) { // pas compris le moins
				leftWheel.power(-MAX_WHEEL_SPEED);
			}
		}
		// make the wheels brake when the down arrow is pressed
		if (((Window) getOwner().getCanvas()).getKeyboard().get(KeyEvent.VK_DOWN).isDown()) {
			rightWheel.power(0.0f);
			leftWheel.power(0.0f);
		}

		if (((Window) getOwner().getCanvas()).getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
			applyAngularForce(15.f); 
		}

		if (((Window) getOwner().getCanvas()).getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
			applyAngularForce(-15.f);
		}
	}

	public void applyAngularForce(float force) {
		getEntity().applyAngularForce(force);
	}

	// dessine le cycliste et sa hitBox
	public void draw(Canvas canvas) {
		// draw hitBox
		Polyline hitBox = new Polyline(true, 0.0f, 0.5f, 0.5f, 1.0f, 0.0f, 2.0f, -0.5f, 1.0f);
		((Window) canvas).drawShape(hitBox, getEntity().getTransform(), Color.RED, Color.RED, 0.0f, 0.0f, 0.0f);
		// draw head
		Circle head = new Circle(0.2f, getHeadLocation());
		((Window) canvas).drawShape(head, getEntity().getTransform(), Color.WHITE, null, 0.0f, 1.0f, 1.0f);
		// draw rightArm
		Polyline rightArm = new Polyline(getShoulderLocation(), getRightHandLocation());
		((Window) canvas).drawShape(rightArm, getEntity().getTransform(), Color.WHITE, Color.WHITE, 0.1f, 1.0f, 1.0f);
		// draw leftArm
		Polyline leftArm = new Polyline(getShoulderLocation(), getLeftHandLocation());
		((Window) canvas).drawShape(leftArm, getEntity().getTransform(), Color.WHITE, Color.WHITE, 0.1f, 1.0f, 1.0f);
		// draw thigh
		Polyline thighRight = new Polyline(getButtLocation(), getKneeRightLocation());
		((Window) canvas).drawShape(thighRight, getEntity().getTransform(), Color.WHITE, Color.WHITE, 0.1f, 1.0f, 1.0f);
		Polyline thighLeft = new Polyline(getButtLocation(), getKneeLeftLocation());
		((Window) canvas).drawShape(thighLeft, getEntity().getTransform(), Color.WHITE, Color.WHITE, 0.1f, 1.0f, 1.0f);
		// draw body
		Polyline body = new Polyline(getShoulderLocation(), getButtLocation());
		((Window) canvas).drawShape(body, getEntity().getTransform(), Color.WHITE, Color.WHITE, 0.1f, 1.0f, 1.0f);
		// draw leftLeg
		Polyline leftLeg = new Polyline(getKneeLeftLocation(), getLeftFootLocation());
		((Window) canvas).drawShape(leftLeg, getEntity().getTransform(), Color.WHITE, Color.WHITE, 0.1f, 1.0f, 1.0f);
		// draw rightLeg
		Polyline rightLeg = new Polyline(getKneeRightLocation(), getRightFootLocation());
		((Window) canvas).drawShape(rightLeg, getEntity().getTransform(), Color.WHITE, Color.WHITE, 0.1f, 1.0f, 1.0f);
	}

	// Head location, in local coordinates
	private Vector getHeadLocation() {
		return new Vector(0.0f, 2.00f);
	}

	// Butt location, in local coordinates
	private Vector getButtLocation() {
		if (versLaDroite) {
			return new Vector(-0.35f, 0.90f);
		} else {
			return new Vector(0.35f, 0.90f);
		}
	}
	
	// retourne les coordonnées du genou quand le cycliste pédale
	private Vector getKneeCoordonates(float x1, float y1, float x2, float y2) {
		float xadd = (x2 + x1);
		float yadd = (y2 + y1);
		xadd *= 0.5;
		yadd *= 0.5;
		float xdiff = x2 - x1;
		float ydiff = y2 - y1;
		float xdiff1 = x1 - x2;
		float rayon = xdiff * xdiff + ydiff * ydiff; // rayon au carré
		float rayonAdd = (float) Math.sqrt(0.49 / rayon);
		rayonAdd *= 0.5;
		float x = 0;
		float y = 0;
		if (!versLaDroite) {
			x = (float) (xadd + rayonAdd * ydiff);
			y = (float) (yadd + rayonAdd * xdiff1);
		} else {
			x = (float) (xadd - rayonAdd * ydiff);
			y = (float) (yadd - rayonAdd * xdiff1);
		}
		return new Vector(x / (2.5f), y * (1.65f)); // permet de plus centrer les genoux 
	}

	// la position du genou droit quand le cycliste pédale
		private Vector getKneeRightLocation() {
			if (!versLaDroite) {
				return (getKneeCoordonates(-0.5f, 1.0f, getRightFootLocation().getX(), getRightFootLocation().getY()));
			} else {
				return (getKneeCoordonates(0.5f, 1.0f, getRightFootLocation().getX(), getRightFootLocation().getY()));
			}
		}
		
	// la position du genou gauche quand le cycliste pédale
	private Vector getKneeLeftLocation() {
		if (!versLaDroite) {
			return (getKneeCoordonates(-0.5f, 1.0f, getLeftFootLocation().getX(), getLeftFootLocation().getY()));
		} else {
			return (getKneeCoordonates(0.5f, 1.0f, getLeftFootLocation().getX(), getLeftFootLocation().getY()));
		}
	}

	// Shoulder location, in local coordinates
	private Vector getShoulderLocation() {
		return new Vector(-0.1f, 1.65f);
	}

	// RightHand location, in local coordinates
	private Vector getRightHandLocation() { 
		if (versLaDroite) {
			if (rise) { // pour que le cycliste lève le bras droit
				return new Vector(0.5f, 2.3f);
			} else {
				return new Vector(0.5f, 1.0f);
			}
		} else {
			if (rise) {
				return new Vector(-0.5f, 2.3f);
			} else {
				return new Vector(-0.5f, 1.0f);
			}
		}
	}

	// LeftHand location, in local coordinates
	private Vector getLeftHandLocation() { 
		if (versLaDroite) {// pour que le cycliste lève le bras gauche
			if (rise) {
				return new Vector(-0.5f, 2.3f);
			} else {
				return new Vector(0.5f, 1.0f);
			}
		} else {
			if (rise) {
				return new Vector(0.5f, 2.3f);
			} else {
				return new Vector(-0.5f, 1.0f);
			}
		}
	}

	// la position du pied droit quand le cycliste pédale
	private Vector getRightFootLocation() {
		return new Vector(0.25f * (float) Math.cos(footAngle), -0.25f * (float) Math.sin(footAngle));
	}
	
	// la position du pied gauche quand le cycliste pédale
	private Vector getLeftFootLocation() {
		return new Vector(-0.25f * (float) Math.cos(footAngle), 0.25f * (float) Math.sin(footAngle));
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVelocity() {
		return super.getVelocity();
	}

	// permet de détruire le vélo et ses roues
	public void destroy() {
		super.destroy();
		leftWheel.destroy();
		rightWheel.destroy();
		//had to add this here cuz yanno 
		getOwner().supprimerActor(this);
	}
}