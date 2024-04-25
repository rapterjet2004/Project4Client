
import java.util.HashMap;
import java.util.Objects;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiClient extends Application{


	Client clientConnection;
	Button b1;
	BorderPane pane;
	Border gridBorder = new Border(
			new BorderStroke(Color.BLACK,
							BorderStrokeStyle.SOLID,
							CornerRadii.EMPTY,
							BorderWidths.DEFAULT));
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		clientConnection = new Client(data->{
				Platform.runLater(()->{
//					listItems2.getItems().add(data.toString());
			});
		});
							
		clientConnection.start();
		b1 = new Button("submit");
		b1.setOnAction(e->{
//			clientConnection.send(c1.getText()); c1.clear();
		});
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

		pane = new BorderPane(new VBox(b1));
		pane.setRight(buildRight());
		pane.setCenter(buildCenter());
		primaryStage.setScene(new Scene(pane, 365, 290));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private Node buildRight() {
		VBox carrier = new VBox(new Text("Carrier"));
			carrier.setPadding(new Insets(10));
			carrier.setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, new CornerRadii(0), null)));
		VBox destroyer = new VBox(new Text("Destroyer"));
			destroyer.setPadding(new Insets(10));
			destroyer.setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, new CornerRadii(0), null)));
		VBox patrol = new VBox(new Text("Patrol"));
			patrol.setPadding(new Insets(10));
			patrol.setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, new CornerRadii(0), null)));

		b1.setAlignment(Pos.BOTTOM_CENTER);
		b1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(0), null)));
		b1.setPadding(new Insets(10));
		VBox vbox = new VBox(carrier, destroyer, patrol, b1);
		vbox.setSpacing(20);
		vbox.setPadding(new Insets(50));
		vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), new Insets(10))));
		return vbox;
	}

	private Node buildCenter() {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(0));
		grid.setHgap(0);
		grid.setVgap(0);

		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				Button button = new Button();
				button.setBackground(null);
				button.setBorder(gridBorder);
				grid.add(button, c, r);
			}
		}
		VBox vbox = new VBox(grid);
		vbox.setPadding(new Insets(10));
		vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(10))));
		return vbox;
	}



}
