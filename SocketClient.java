package oletsky.socketexplorer;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {


    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: start java sokclient host port");
            System.exit(0);
        }

        final String HOST = args[0];
        final int PORT = Integer.parseInt(args[1]);


        try (
            Socket clientSocket = new Socket(HOST, PORT);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()
                    )
            )
            ){
            pw.println("GET");
            pw.flush();
            Thread.sleep(5000);
            System.out.println(br.ready());
            System.out.println("Server response:");

            while (br.ready()) {
                String s=br.readLine();
                System.out.println(s);
            }
            System.out.println("------------");

            } catch (UnknownHostException unknownHostException) {
            unknownHostException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}

