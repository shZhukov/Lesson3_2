package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
//import java.util.logging.LogManager;
//import java.util.logging.Logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server{
    SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
    List<ClientHandler> clients;
    private AuthService authService;

    private static int PORT = 12485;
    ServerSocket server = null;
    Socket socket = null;

    private final Logger LOGGER = LogManager.getLogger("");
    private final Logger fileLogger = LogManager.getLogger("fileLogger");

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен");
            LOGGER.info("Сервер запущен");
            fileLogger.info("Сервер запущенн");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                LOGGER.info("Клиент подключился");
                fileLogger.info("Клиент подключился");

//                clients.add(new ClientHandler(this, socket));
//                subscribe(new ClientHandler(this, socket));
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            fileLogger.error(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {

            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void broadcastMsg(ClientHandler sender, String msg) {
        String message = String.format("%s %s : %s",formater.format(new Date()), sender.getNickName(), msg);
        fileLogger.info("Сообщение Всем:" + message);
        for (ClientHandler client : clients) {
            client.sendMsg(message);
        }
    }

    public void privateMsg(ClientHandler sender, String receiver, String msg) {
        String message = String.format("%s [%s] private [%s] : %s",formater.format(new Date()), sender.getNickName(), receiver, msg);
        fileLogger.info("Сообщение От:" + sender + " Получатель:" + receiver + " " + message);
        for (ClientHandler c : clients) {
            if (c.getNickName().equals(receiver)) {
                c.sendMsg(message);
                if (!c.equals(sender)) {    // отправитель != получателю
                    sender.sendMsg(message);
                }
                return;
            }
        }

        sender.sendMsg("not found user: " + receiver);
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }

    public AuthService getAuthService(){
        return authService;
    }

    public boolean isLoginAuthenticated(String login){
        for (ClientHandler c : clients) {
            if (c.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }

    private void broadcastClientList(){
        StringBuilder sb = new StringBuilder("/clientsList ");
        for (ClientHandler c : clients) {
            sb.append(c.getNickName()).append(" ");
        }
        String msg = sb.toString();
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }
}
