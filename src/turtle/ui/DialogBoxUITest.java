package turtle.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.util.function.IntConsumer;

/**
 * DialogBoxUITest.java
 * This tests the functionality of the dialog-box UI.
 *
 * @author Henry
 *         Date: 5/9/17
 *         Period: 2
 */
public class DialogBoxUITest extends Application
{

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    /**
     * Starts the test application
     *
     * @param primaryStage the primary window
     */
    @Override
    public void start(Stage primaryStage)
    {
        DialogBoxUI root = new DialogBoxUI("Test test test:",
                "Menu", "Cancel");

        /**
         * Listens for a user response (index of button pressed).
         */
        root.onResponse(new IntConsumer()
        {
            /**
             * Called when user responds
             * @param value the index of response.
             */
            @Override
            public void accept(int value)
            {
                System.out.println("You entered index " + value);
                primaryStage.hide();
            }
        });
        Scene s = new Scene(root, WIDTH, HEIGHT);
        s.getStylesheets().add("/turtle/ui/styles.css");
        primaryStage.setScene(s);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();

    }

}
