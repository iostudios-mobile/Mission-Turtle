package turtle.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.function.IntConsumer;

/**
 * This displays a message to the user and a series of button responses.
 *
 * @author Henry Wang
 */
public class DialogBoxUI extends Pane {
    private static final double MARGINS = 5.0;
    private static final double MIN_BUTTON_WIDTH = 100;

    private final EqualGridPane pnlButtons;
    private final VBox pnlDialog;

    private IntConsumer respondHand;

    /**
     * Creates a new DialogBoxUI and initializes UI.
     *
     * @param message the message to display the user
     * @param buttons the array of buttons user can select.
     */
    public DialogBoxUI(String message,
                       String... buttons) {

        Label lblMessage = new Label(message);
        lblMessage.setWrapText(true);
        lblMessage.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(lblMessage, Pos.CENTER);

        Pane pnlMessage = new StackPane();
        pnlMessage.getStyleClass().add("DialogMessage");
        VBox.setMargin(pnlMessage, new Insets(0, MARGINS, MARGINS, MARGINS));
        pnlMessage.getChildren().add(lblMessage);

        pnlButtons = new EqualGridPane(1, buttons.length);
        initButtons(buttons);

        pnlDialog = new VBox();
        pnlDialog.getStyleClass().add("ldialog");
        pnlDialog.setPadding(new Insets(MARGINS));
        pnlDialog.getChildren().addAll(pnlMessage, pnlButtons);

        getChildren().add(pnlDialog);
    }

    /**
     * Obtains the handler when user responds
     *
     * @return the response handler.
     */
    public IntConsumer getOnResponse() {
        return respondHand;
    }

    /**
     * Initializes the buttons panel UI
     *
     * @param buttons the array of buttons.
     */
    private void initButtons(String[] buttons) {
        pnlButtons.setHgap(MARGINS);
        for (int i = 0; i < buttons.length; i++) {
            Label lblText = new Label(buttons[i]);
            StackPane.setAlignment(lblText, Pos.CENTER);
            StackPane.setMargin(lblText, new Insets(MARGINS));

            StackPane button = new StackPane();
            button.setMinWidth(MIN_BUTTON_WIDTH);
            button.getChildren().add(lblText);
            button.getStyleClass().add("lbutton");

            int curIndex = i;

            button.setOnMouseClicked(event ->
            {
                if (respondHand != null) {
                    respondHand.accept(curIndex);
                }
            });

            pnlButtons.getChildren().add(button);
        }
        VBox.setMargin(pnlButtons, new Insets(MARGINS, MARGINS, MARGINS,
                MARGINS));
    }

    /**
     * Sets a new handler when user clicks the buttons.
     *
     * @param handler an handler that accepts an index of button.
     */
    public void onResponse(IntConsumer handler) {
        respondHand = handler;
    }

    /**
     * Layouts this dialog so that it takes up up to half of width/height
     * of this invisible pane.
     */
    @Override
    public void layoutChildren() {
        pnlDialog.setMaxSize(getWidth() / 2, getHeight() / 2);
        layoutInArea(pnlDialog, 0, 0, getWidth(), getHeight(), 0, null,
                false, false, HPos.CENTER, VPos.CENTER);
    }
}

