package sample.controllers;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class StageUtils {

    private static double x;
    private static double y;
    private static double lastX = 0.0d;
    private static double lastY = 0.0d;
    private static double lastWidth = 0.0d;
    private static double lastHeight = 0.0d;

    private static boolean isMaximized = false;

    private StageUtils() {}


    public static void onPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();

    }

    public static void onDragged(MouseEvent event) {

        if (isMaximized)
            maximize(event);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);

//        if (!isMaximized && stage.getY() < 5) maximize(event);

    }

    public static void maximize(MouseEvent event) {
        isMaximized = !isMaximized;
//        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Node n = (Node) event.getSource();
        Window w = n.getScene().getWindow();

        double currentX = w.getX();
        double currentY = w.getY();
        double currentWidth = w.getWidth();
        double currentHeight = w.getHeight();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        if (currentX != bounds.getMinX() &&
                currentY != bounds.getMinY() &&
                currentWidth != bounds.getWidth() &&
                currentHeight != bounds.getHeight()) {

            w.setX(bounds.getMinX());
            w.setY(bounds.getMinY());
            w.setWidth(bounds.getWidth());
            w.setHeight(bounds.getHeight());

            lastX = currentX;  // save old dimensions
            lastY = currentY;
            lastWidth = currentWidth;
            lastHeight = currentHeight;

        } else {

            // de-maximize the window (not same as minimize)

            w.setX(lastX);
            w.setY(lastY);
            w.setWidth(lastWidth);
            w.setHeight(lastHeight);
        }

        event.consume();  // don't bubble up to title bar
    }


    public static void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public static void closeApp() {
        Platform.exit();
    }
}
