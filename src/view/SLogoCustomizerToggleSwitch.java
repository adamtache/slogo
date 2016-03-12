package view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


/**
 * ToggleSwitch GUI module used in GUI's Customizer button
 *
 */
public class SLogoCustomizerToggleSwitch extends HBox {
	
	private final Label label = new Label();
	private final Button button = new Button();
	
	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);
	public SimpleBooleanProperty switchOnProperty() { return switchedOn; }
	
	private void init() {
		
		label.setText("DOWN");
		
		getChildren().addAll(label, button);	
		button.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		label.setOnMouseClicked((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		setStyle();
		bindProperties();
	}
	
	private void setStyle() {
		setWidth(80);
		label.setAlignment(Pos.CENTER);
		setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
	}
	
	private void bindProperties() {
		label.prefWidthProperty().bind(widthProperty().divide(2));
		label.prefHeightProperty().bind(heightProperty());
		button.prefWidthProperty().bind(widthProperty().divide(2));
		button.prefHeightProperty().bind(heightProperty());
	}
	
	public SLogoCustomizerToggleSwitch() {
		init();
		switchedOn.addListener((a,b,c) -> {
			if (c) {
                		label.setText("UP");
                		label.toFront();
            		}
            		else {
            			label.setText("DOWN");
                		button.toFront();
            		}
		});
	}
	
	public boolean isDown(){
		System.out.println("YO");
		return label.getText().equals("DOWN");
	}
}