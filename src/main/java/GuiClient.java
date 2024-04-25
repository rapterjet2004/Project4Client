import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.util.ArrayList;

public class GuiClient extends Application{

	private enum ShipType {
		CARRIER,
		DESTROYER,
		PATROL
	}

	boolean isPlayer;

	ShipType type;

	Text carrierText;
	Text destroyerText;
	Text patrolText;

	VBox carrier;
	VBox destroyer;
	VBox patrol;

	ArrayList<Boat> boats = new ArrayList<>();
	int numCarrier = 1;
	int numDestroyer = 1;
	int numPatrol = 1;
	GridPane grid;
	GridPane grid2;
	private Node[][] gridPaneArray = null;
	private Node[][] gridPaneArray2 = null;


	Client clientConnection;
	Button b1;
	Button Ai;
	Button Player;
	BorderPane pane;
	BorderPane pane2;
	Border gridBorder = new Border(
			new BorderStroke(Color.BLACK,
							BorderStrokeStyle.SOLID,
							CornerRadii.EMPTY,
							BorderWidths.DEFAULT));
	Background shipBg = new Background(new BackgroundFill(Color.DARKGRAY, null, null));
	Border shipBr = new Border(
			new BorderStroke(Color.DARKGRAY,
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
		pane.setLeft(buildLeft());
		pane.setRight(buildRight());
		pane.setCenter(buildCenter());
		primaryStage.setScene(new Scene(pane));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void handleMessage(String type) {
		// TODO work with shane to get this working
	}

	private Node buildCenter() {
		carrierText = new Text("Carrier - " + numCarrier);
		carrier = new VBox(carrierText);
			carrier.setPadding(new Insets(10));
			carrier.setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, new CornerRadii(0), null)));
		carrier.setOnMouseClicked(e -> {
			type = ShipType.CARRIER;
		});
		destroyerText = new Text("Destroyer - " + numDestroyer);
		destroyer = new VBox(destroyerText);
			destroyer.setPadding(new Insets(10));
			destroyer.setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, new CornerRadii(0), null)));
		destroyer.setOnMouseClicked(e -> {
			type = ShipType.DESTROYER;
		});
		patrolText = new Text("Patrol - " + numPatrol);
		patrol = new VBox(patrolText);
			patrol.setPadding(new Insets(10));
			patrol.setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, new CornerRadii(0), null)));
		patrol.setOnMouseClicked(e -> {
			type = ShipType.PATROL;
		});

		Text t = new Text("Play against:");
		Ai = new Button("Ai");
		Ai.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(0), null)));
		Ai.setPadding(new Insets(10));
		Ai.setOnAction(e -> isPlayer = false);

		Player = new Button("Player");
		Player.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(0), null)));
		Player.setPadding(new Insets(10));
		Player.setOnAction(e -> isPlayer = true);

		b1.setAlignment(Pos.BOTTOM_CENTER);
		b1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(0), null)));
		b1.setPadding(new Insets(10));
		VBox vbox = new VBox(carrier, destroyer, patrol, t, new HBox(Ai, Player), b1);
		vbox.setSpacing(20);
		vbox.setPadding(new Insets(50));
		vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), new Insets(10))));
		return vbox;
	}

	private Node buildLeft() {
		grid = new GridPane();
		grid.setPadding(new Insets(0));
		grid.setHgap(0);
		grid.setVgap(0);

		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				Button button = new Button();
				button.setBackground(null);
				button.setBorder(gridBorder);
				int finalR = r;
				int finalC = c;
				button.setOnAction(e -> {
					buildShip(finalR, finalC);
				});
				grid.add(button, c, r);
			}
		}
		initializeGridPaneArray();
		VBox vbox = new VBox(grid);
		vbox.setPadding(new Insets(10));
		vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(10))));
		return vbox;
	}

	private Node buildRight() {
		grid2 = new GridPane();
		grid2.setPadding(new Insets(0));
		grid2.setHgap(0);
		grid2.setVgap(0);

		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				Button button = new Button();
				button.setBackground(null);
				button.setBorder(gridBorder);
				grid2.add(button, c, r);
			}
		}
		initializeGridPaneArray2();
		VBox vbox = new VBox(grid2);
		vbox.setPadding(new Insets(10));
		vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(10))));
		return vbox;
	}

	private void initializeGridPaneArray() {
		this.gridPaneArray = new Node[10][10];
		for (Node node : this.grid.getChildren()) {
			this.gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
		}
	}

	private void initializeGridPaneArray2() {
		this.gridPaneArray2 = new Node[10][10];
		for (Node node : this.grid2.getChildren()) {
			this.gridPaneArray2[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
		}
	}

	private void buildShip(int row, int col) {
		// TODO add each ship to boats
		switch (type) {
			case CARRIER: {
				buildCarrier(row, col);
				numCarrier--;
				carrierText = new Text("Carrier - " + numCarrier);
				carrier.getChildren().clear();
				carrier.getChildren().add(carrierText);
				break;
			}
			case DESTROYER: {
				buildDestroyer(row, col);
				numDestroyer--;
				destroyerText = new Text("Destroyer - " + numDestroyer);
				destroyer.getChildren().clear();
				destroyer.getChildren().add(destroyerText);
				break;
			}
			case PATROL: {
				buildPatrol(row, col);
				numPatrol--;
				patrolText = new Text("Patrol - " + numPatrol);
				patrol.getChildren().clear();
				patrol.getChildren().add(patrolText);
				break;
			}
			default:
				break;
		}
	}

	private void buildCarrier(int row, int col) {
		if (row <= 7 && col <= 8 && numCarrier > 0) {
			ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();
			list.add(new Pair<>(row, col));
			list.add(new Pair<>(row, col + 1));
			list.add(new Pair<>(row + 1, col));
			list.add(new Pair<>(row + 1, col + 1));
			list.add(new Pair<>(row + 2, col + 1));
			boats.add(new Boat(list));


			((Button) gridPaneArray[row][col]).setBackground(shipBg);
			((Button) gridPaneArray[row][col + 1]).setBackground(shipBg);
			((Button) gridPaneArray[row + 1][col]).setBackground(shipBg);
			((Button) gridPaneArray[row + 1][col + 1]).setBackground(shipBg);
			((Button) gridPaneArray[row + 2][col + 1]).setBackground(shipBg);

			((Button) gridPaneArray[row][col]).setBorder(shipBr);
			((Button) gridPaneArray[row][col + 1]).setBorder(shipBr);
			((Button) gridPaneArray[row + 1][col]).setBorder(shipBr);
			((Button) gridPaneArray[row + 1][col + 1]).setBorder(shipBr);
			((Button) gridPaneArray[row + 2][col + 1]).setBorder(shipBr);
		}
	}

	private void buildDestroyer(int row, int col) {
		if (row <= 7 && numDestroyer > 0) {
			ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();
			list.add(new Pair<>(row, col));
			list.add(new Pair<>(row + 1, col));
			list.add(new Pair<>(row + 2, col));
			boats.add(new Boat(list));

			((Button) gridPaneArray[row][col]).setBackground(shipBg);
			((Button) gridPaneArray[row + 1][col]).setBackground(shipBg);
			((Button) gridPaneArray[row + 2][col]).setBackground(shipBg);

			((Button) gridPaneArray[row][col]).setBorder(shipBr);
			((Button) gridPaneArray[row + 1][col]).setBorder(shipBr);
			((Button) gridPaneArray[row + 2][col]).setBorder(shipBr);

		}
	}

	private void buildPatrol(int row, int col) {
		if (row <= 8 && numPatrol > 0) {
			ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();
			list.add(new Pair<>(row, col));
			list.add(new Pair<>(row + 1, col));
			boats.add(new Boat(list));

			((Button) gridPaneArray[row][col]).setBackground(shipBg);
			((Button) gridPaneArray[row + 1][col]).setBackground(shipBg);

			((Button) gridPaneArray[row][col]).setBorder(shipBr);
			((Button) gridPaneArray[row + 1][col]).setBorder(shipBr);
		}
	}






}
