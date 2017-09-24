import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.sun.glass.ui.PlatformFactory;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {

	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		TextArea ta = new TextArea();

		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("Server Programı");
		primaryStage.setScene(scene);
		primaryStage.show();

		new Thread(() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(8000);
				Platform.runLater(() -> ta.appendText("Server çalışmaya başladı. " + new Date() + " \n"));
				Socket socket = serverSocket.accept();

				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

				while (true) {
					double radius = inputFromClient.readDouble();

					double area = radius * radius * Math.PI;

					outputToClient.writeDouble(area);

					Platform.runLater(() -> {
						ta.appendText("İstemciden yarıçap değeri alındı. Yarıçap: " + radius + "\n");
						ta.appendText("Area is: " + area + "\n");
					});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();

	}

}
