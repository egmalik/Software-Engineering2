package il.ac.haifa.cs.sweng.OCSFSimpleChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ChatClientCLI {

	private SimpleChatClient client;
	private boolean isRunning;
	private static final String SHELL_STRING = "Enter message (or exit to quit)> ";
	private Thread loopThread;

	public ChatClientCLI(SimpleChatClient client) {
		this.client = client;
		this.isRunning = false;
	}///aaaaaaaaaaaaaaaaaa

	public void loop() throws IOException {
		loopThread = new Thread(new Runnable() {

			//@Override
			public void run() {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String message;
				while (client.isConnected()) {
					System.out.print(SHELL_STRING);

					try {
						message = reader.readLine();
						if (message.isBlank())
							continue;

						if (message.equalsIgnoreCase("exit")||message.substring(0, 5).equals("#exit")) {
							System.out.println("Closing connection.");
								client.closeConnection();
						} else {
							
							if(message.equals("#sendSubmitters")) {
								message = "malik, Tarik, Ahmad";
							}else if (message.substring(0, 5).equals("#send")) {
								message = message.substring(5, message.length()-1);
							}

							client.sendToServer(message);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});

		loopThread.start();
		this.isRunning = true;

	}

	public void displayMessage(Object message) {
		if (isRunning) {
			System.out.print("(Interrupted)\n");
		}
		System.out.println("Received message from server: " + message.toString());
		if (isRunning)
			System.out.print(SHELL_STRING);
	}

	public void closeConnection() {
		System.out.println("Connection closed.");
		System.exit(0);
	}
}
