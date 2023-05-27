package main.spacegame.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.core.collection.Array;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.almasb.fxgl.dsl.FXGL.*;
import static  main.spacegame.SpaceGameType.BULLET;
public class GridComponent extends Component {
    private ArrayList <Line> lines = new ArrayList<Line>();
    private ArrayList <ExtraLines> extraLines = new ArrayList<ExtraLines>();
    private ArrayList <Spribg> springs = new ArrayList <Spring>();

    private ExtendedPoint [][] points;
    private ExtendedPoint [][] fixedPoints;
    
    private Canvas onScreenCanvas = new Canvas(getAppWidth(), getAppHeight());
    private Canvas offScreenCanvas = new Canvas(getAppWidth(), getAppHeight());

    private GridRenderThread gridRenderThread = new GridRenderThread();

    private ArrayList<Entity> bullets = new ArrayList<Entity>();

    public GridComcponent(){
        Point2D spacing = new Point2D(19.4, 20.0);

        int numberOfColumns = (int) (getAppWidth() / spacing.getX()) + 2;
        int numberOfRows = (int) (getAppHeight() / spacing.getY()) + 2;

        points = new ExtendedPoint[numberOfColumns][numberOfRows];
        fixedPoints = new ExtendedPoint[numberOfColumns][numberOfRows];


        double xCoord = 0;
        double yCoord = 0;
        for(int rows = 0; rows < numberOfRows; rows++)
        {
            for(int columns = 0; columns < numberOfColumns; column++)
            {
                points[columns][rows] = new ExtendedPoint(new Vec2(xCoord, yCoord), POINT_MASS_DAMPING, 1);
                fixedPoints[column][row] = new PointMass(new Vec2(xCoord, yCoord), POINT_MASS_DAMPING, 0);
                xCoord += spacing.getX();
            }
            yCoord += spacing.getY();
            xCoord = 0;
        }

        // link the point masses with springs
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numColumns; x++) {
                if (x == 0 || y == 0 || x == numColumns - 1 || y == numRows - 1) {
                    springs.add(new Spring(fixedPoints[x][y], points[x][y], 0.5, 0.1));
                } else if (x % 3 == 0 && y % 3 == 0) {
                    springs.add(new Spring(fixedPoints[x][y], points[x][y], 0.005, 0.02));
                }

                if (x > 0) {
                    springs.add(new Spring(points[x - 1][y], points[x][y], SPRING_STIFFNESS, SPRING_DAMPING));

                    addLine(points[x - 1][y], points[x][y]);
                }

                if (y > 0) {
                    springs.add(new Spring(points[x][y - 1], points[x][y], SPRING_STIFFNESS, SPRING_DAMPING));

                    addLine(points[x][y - 1], points[x][y]);
                }

                // add additional lines
                if (x > 0 && y > 0) {
                    addExtraLine(
                            points[x - 1][y], points[x][y],
                            points[x - 1][y - 1], points[x][y - 1]);

                    addExtraLine(
                            points[x][y - 1], points[x][y],
                            points[x - 1][y - 1], points[x - 1][y]);
                }
            }
        }
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(onScreenCanvas);

        gridRenderThread.start();
    }

    @Override
    public void onUpdate(double tpf) {
        if (gridRenderThread.isRenderDone.get()) {
            bullets = byType(BULLET);

            gridRenderThread.isRenderDone.set(false);
        }
    }

    private class GridRenderThread extends Thread {

        AtomicBoolean isRenderDone = new AtomicBoolean(false);

        GridRenderThread() {
            super("GridRenderThread");
            setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                if (isRenderDone.get()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {

                        Logger.get("GridRenderThread").warning("Render thread was interrupted");
                        return;
                    }

                    continue;
                }

                springs.forEach(Spring::update);

                for (int x = 0; x < points.length; x++) {
                    for (int y = 0; y < points[0].length; y++) {
                        points[x][y].update();
                    }
                }

                // render ???
                offScreenCanvas.getGraphicsContext2D().clearRect(0, 0, getAppWidth(), getAppHeight());

                for (Line line : lines)
                    line.render(offScreenCanvas.getGraphicsContext2D());

                for (ExtraLine line : extraLines)
                    line.render(bullets, offScreenCanvas.getGraphicsContext2D());

                var g = offScreenCanvas.getGraphicsContext2D();

                g.setStroke(EDGE_COLOR);
                g.setLineWidth(7.5);
                g.strokeLine(0, 0, 0, getAppHeight());
                g.strokeLine(0, 0, getAppWidth(), 0);
                g.strokeLine(getAppWidth(), 0, getAppWidth(), getAppHeight());
                g.strokeLine(0, getAppHeight(), getAppWidth(), getAppHeight());
                g.setStroke(IDLE_COLOR);
                g.setLineWidth(1);

                isRenderDone.set(true);

                Platform.runLater(() -> {
                    entity.getViewComponent().removeChild(onScreenCanvas);

                    var tmp = onScreenCanvas;
                    onScreenCanvas = offScreenCanvas;
                    offScreenCanvas = tmp;

                    entity.getViewComponent().addChild(onScreenCanvas);
                });
            }
        }
    }

    public void addLine(ExtendedPoint end1, ExtendedPoint end2) {
        lines.add(new Line(end1, end2));
    }

    public void addExtraLine(ExtendedPoint end11, ExtendedPoint end12, ExtendedPoint end21, ExtendedPoint end22) {
        extraLines.add(new ExtraLine(end11, end12, end21, end22));
    }

    public void applyExplosiveForce(double force, Point2D position, double radius) {
        applyImplosiveForce(-force, position, radius);
    }

    public void applyImplosiveForce(double force, Point2D position, double radius) {
        Vec2 tmpVec = new Vec2();

        for (int x = 0; x < points.length; x++) {
            for (int y = 0; y < points[0].length; y++) {
                double dist = position.distance(points[x][y].getPosition().x, points[x][y].getPosition().y);
                dist *= dist;

                if (dist < radius * radius) {
                    tmpVec.set((float) position.getX(), (float) position.getY());
                    tmpVec.subLocal(points[x][y].getPosition()).mulLocal((float) (10f * force / (10000 + dist)));

                    points[x][y].applyForce(tmpVec);
                    points[x][y].increaseDamping(0.6f);
                }
            }
        }
    }

    // currently unused
    public void applyDirectedForce(Point2D forceDir, Point2D position, double radius) {
        Vec2 tmpVec = new Vec2();

        for (int x = 0; x < points.length; x++) {
            for (int y = 0; y < points[0].length; y++) {
                double dist = position.distance(points[x][y].getPosition().x, points[x][y].getPosition().y);
                dist *= dist;

                if (dist < radius * radius) {
                    tmpVec.set(forceDir).mulLocal(10 / (10000 + dist));

                    points[x][y].applyForce(tmpVec);
                }
            }
        }
    }

    private static class Line {
        private ExtendedPoint end1, end2;

        Line(ExtendedPoint end1, ExtendedPoint end2) {
            this.end1 = end1;
            this.end2 = end2;
        }

        void render(GraphicsContext g) {
            g.strokeLine(end1.getPosition().x, end1.getPosition().y, end2.getPosition().x, end2.getPosition().y);

            g.setStroke(IDLE_COLOR);
        }
    }

    private static class ExtraLine {

        private ExtendedPoint end11, end12, end21, end22;

        private Vec2 position1 = new Vec2();
        private Vec2 position2 = new Vec2();

        ExtraLine(ExtendedPoint end11, ExtendedPoint end12, ExtendedPoint end21, ExtendedPoint end22) {
            this.end11 = end11;
            this.end12 = end12;
            this.end21 = end21;
            this.end22 = end22;
        }

        void render(List<Entity> bullets, GraphicsContext g) {
            position1.x = end11.getPosition().x + (end12.getPosition().x - end11.getPosition().x) / 2;
            position1.y = end11.getPosition().y + (end12.getPosition().y - end11.getPosition().y) / 2;

            position2.x = end21.getPosition().x + (end22.getPosition().x - end21.getPosition().x) / 2;
            position2.y = end21.getPosition().y + (end22.getPosition().y - end21.getPosition().y) / 2;

            var midPoint = position1.midpoint(position2);

            if (
                    bullets.stream()
                            .anyMatch(e -> {
                                // offset by rotation origin
                                var bulletPointX = e.getX();
                                var bulletPointY = e.getY() + 6.5;

                                var distance = midPoint.distance(bulletPointX, bulletPointY);
                                return distance < 70;
                            })
            ) {
                g.setStroke(BULLET_COLOR);
            }

            g.strokeLine(position1.x, position1.y, position2.x, position2.y);

            g.setStroke(IDLE_COLOR);
        }
    }

    private static class ExtendedPoint {

        private Vec2 position;
        private Vec2 velocity = new Vec2();
        private Vec2 acceleration = new Vec2();

        private final float initialDamping;
        private float damping;
        private float inverseMass;

        public PointMass(Vec2 position, double damping, double inverseMass) {
            this.position = position;
            this.damping = (float) damping;
            this.initialDamping = (float) damping;
            this.inverseMass = (float) inverseMass;
        }

        public void applyForce(Vec2 force) {
            acceleration.addLocal(force.mul(inverseMass));
        }

        public void increaseDamping(double factor) {
            damping *= factor;
        }

        public void update() {
            applyAcceleration();
            applyVelocity();

            damping = initialDamping;
        }

        public Vec2 getPosition() {
            return position;
        }

        public Vec2 getVelocity() {
            return velocity;
        }

        private void applyAcceleration() {
            velocity = velocity.add(acceleration);
            acceleration.setZero();
        }

        private void applyVelocity() {
            position.addLocal(velocity.mul(0.6f));

            if (velocity.lengthSquared() < 0.0001) {
                velocity.setZero();
            }

            velocity.mulLocal(damping);
        }
    }

    private static class Spring {
        private final ExtendedPoint end1;
        private final ExtendedPoint end2;

        private final double lengthAtRest;

        private final float stiffness;
        private final float damping;

        public Spring(ExtendedPoint end1, ExtendedPoint end2, double stiffness, double damping) {
            this.end1 = end1;
            this.end2 = end2;
            this.stiffness = (float) stiffness;
            this.damping = (float) damping / 10;
            lengthAtRest = end1.getPosition().distance(end2.getPosition().x, end2.getPosition().y) * 0.95f;
        }

        public void update() {
            Vec2 current = new Vec2(end1.getPosition()).subLocal(end2.getPosition());

            float currentLength = current.length();

            if (currentLength > lengthAtRest) {
                Vec2 dv = new Vec2(end2.getVelocity())
                        .subLocal(end1.getVelocity())
                        .mulLocal(damping);

                Vec2 force = current.normalizeLocal()
                        .mulLocal(currentLength - lengthAtRest)
                        .mulLocal(stiffness)
                        .subLocal(dv);

                end2.applyForce(force);
                end1.applyForce(force.negateLocal());
            }
        }
    }
}




    