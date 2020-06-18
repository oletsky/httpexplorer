package oletsky.socketexplorer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketController {
    public static void main(String[] args) {
        //Checking arguments of command line
        if (args.length!=1) {
            System.out.println("Usage: start java SocketController port");
            System.exit(0);
        }
        //Registration on the port
        int connNumber=0;
        final int PORT=Integer.parseInt(args[0]);
        ServerSocket servSock=null;
        try {
            servSock = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Error in binding to port "+PORT);
            System.exit(0);
        }
        System.out.println("Server started at port "+PORT);


        while (true) {
            Socket soket;
            try {
                soket = servSock.accept();
                System.out.println("New client connected, numb="+
                        (++connNumber));
                var ipAddr = soket.getInetAddress();

                System.out.println("IP client adress: "+
                        ipAddr.toString());
                var fullAddr = soket.getRemoteSocketAddress();
                System.out.println("IP client full adress: "+
                        fullAddr.toString());
                new Thread(new SocketThread(soket)).start();

            }
            catch (IOException e) {
                System.out.println("Attempt of new connection failed");
            }



        }




    }


}
