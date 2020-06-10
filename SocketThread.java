package oletsky.socketexplorer;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;


public class SocketThread implements Runnable{
    static int connNumber=0;
    int id;
    static boolean sent=false;
    static ReentrantLock lock = new ReentrantLock();


    private Socket soket;

    public SocketThread(Socket soket) {
        lock.lock();
        try {
            connNumber++;
            this.id=connNumber;
            this.soket = soket;
            System.out.println("Connection initialized, conn= "+
                    connNumber);
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        try (BufferedReader br=new BufferedReader(new InputStreamReader(
                soket.getInputStream()));
             PrintWriter pw = new PrintWriter(
                     new OutputStreamWriter(soket.getOutputStream()));
             BufferedReader brin = new BufferedReader(
                     new InputStreamReader(System.in)
             )

        ) {
            boolean exit=false;

            while(!exit) {
                System.out.println("Request number "+id+":");
                while (br.ready()) {

                    String inp = br.readLine();
                    System.out.println(inp);
                }
                final String http="HTTP/1.1";
                final String plain= "Content-type: text/plain";
                final String html= "Content-type: text/html";


                //System.out.println(">");
                //String s=brin.readLine();

                lock.lock();
                try {
                if (!sent) {
                    pw.print(http + "\n" +
                            html + "\n\n" +
                            "<h1>That's wonderful!</h1>\n");
                    pw.flush();
                    pw.close();
                    System.out.println("---- Response sent! ---- id = " +
                            id);
                    //sent = true;
                }

                }
                finally {
                    lock.unlock();
                }

                exit=true;

            }

        }

        catch (Exception e) {try {
            soket.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }}

    }





}

