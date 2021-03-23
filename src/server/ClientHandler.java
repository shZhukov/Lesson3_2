package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickName;
    private String login;

    public ClientHandler(Server server, Socket socket) {

        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    socket.setSoTimeout(5000);
//                    socket.setSoTimeout(0);
                    // цикл аутентифиукаии
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth")) {
                            String[] token = str.split("\\s");
                            if (token.length <3){
                                continue;
                            }
                            String newNick = null;
                            try {
                                newNick = server.getAuthService()
                                        .getNickByLoginAndPassword(token[1], token[2]);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            login = token[1];
                            if (newNick != null) {
                                if (!server.isLoginAuthenticated(login)) {
                                    nickName = newNick;
                                    sendMsg("/authok " + nickName);
                                    server.subscribe(this);
                                    System.out.println("Клиент " + nickName + " подключился");
                                    socket.setSoTimeout(0);
                                    break;
                                } else {
                                    sendMsg("С данной учетной записью уже зашли");
                                }
                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }

                        if(str.startsWith("/reg")){
                            String[] token = str.split("\\s");
                            if(token.length < 4){
                                continue;
                            }
                            boolean isRegistration = server.getAuthService()
                                    .registration(token[1], token[2], token[3]);
                            if (isRegistration){
                                sendMsg("/regok");
                            }else {
                                sendMsg("/regno");
                            }
                        }
                    }

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/")) {
                            System.out.println(str);
                            if (str.equals("/end")) {
                                out.writeUTF("/end");
                                break;
                            }
                            if (str.startsWith("/w")) {
                                String[] token = str.split("\\s+", 3);
                                if (token.length < 3) {
                                    continue;
                                }
                                server.privateMsg(this, token[1], token[2]);
                            }
                        } else {
                            server.broadcastMsg(this, str);
                        }
                    }
                } catch (java.net.SocketTimeoutException e){
                    try {
                        socket.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Клиент отключился");
                    server.unsubscribe(this);
                    try {
                        socket.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNickName() {
        return nickName;
    }

    public String getLogin() {
        return login;
    }
}
