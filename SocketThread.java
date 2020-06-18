package oletsky.socketexplorer;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


public class SocketThread implements Runnable {
    static int connNumber = 0;
    int id;
    static boolean sent = false;
    static ReentrantLock lock = new ReentrantLock();


    private Socket soket;

    public SocketThread(Socket soket) {
        lock.lock();
        try {
            connNumber++;
            this.id = connNumber;
            this.soket = soket;
            System.out.println("Connection initialized, conn= " +
                    connNumber);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                soket.getInputStream()));
             PrintWriter pw = new PrintWriter(
                     new OutputStreamWriter(soket.getOutputStream()));
             BufferedReader brin = new BufferedReader(
                     new InputStreamReader(System.in)
             )

        ) {
            boolean exit = false;

            while (!exit) {

                lock.lock();
                try {
                    System.out.println("Request number " + id + ":");
                    int kolLines = 0;
                    while (br.ready()) {

                        String inp = br.readLine();
                        kolLines++;
                        System.out.println(inp);
                    }
                    System.out.println("There were " + kolLines +
                            " lines in this request");
                    final String http = "HTTP/1.1 200 Ok";
                    final String plain = "Content-type: text/plain";
                    final String html = "Content-type: text/html";
                    String[] messages = {
                            "Miaoo!",
                            "Wow!",
                            "Ku-ku!",
                            "Kvak!",
                            "Byak!",
                            "Shmyak!",
                            "That's cool!",
                            "That's great!"
                    };
                    Random rand = new Random();

                    /*if (kolLines > 0 && !sent)*/ {
                        //System.out.println("What should be sent back to browser?");
                        String msg = messages[rand.nextInt(messages.length)];
                        String info = "<h1>"+msg+"</h1>";

                        pw.print(http + "\n" +
                                html + "\n\n" +
                                info +
                                "\n");
                        pw.flush();
                        pw.close();
                        System.out.println("---- Response sent! ---- id = " +
                                id);

                        //sent = true;
                    }
                    System.out.println("*********************");
                } finally {
                    lock.unlock();
                }

                exit = true;

            }

        } catch (Exception e) {
            try {
                soket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    }


}

