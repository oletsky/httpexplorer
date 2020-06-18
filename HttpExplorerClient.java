package oletsky.socketexplorer;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpExplorerClient {


    public static void main(String[] args) {


        final String HOST = "www.glavred.info";
        final int PORT = 80;


        try (
                Socket clientSocket = new Socket(HOST, PORT);
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()
                        )
                )
        ){
            String request = "GET / HTTP/1.1\n\n";
            pw.println(request);
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