package redesi.ifb.com.pop3client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alunotgn on 16/05/2018.
 */

public class POP3Client {
    String usuario;
    String senha;
    String host;
    Socket emailSocket;

    InputStream is;
    InputStreamReader isr;
    BufferedReader br;

    public POP3Client(String usuario, String senha, String host) throws IOException{
        this.usuario = usuario;
        this.senha = senha;
        this.host = host;
        Socket emailSocket = new Socket(host, 110);
        is = emailSocket.getInputStream();
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
    }

    public boolean login(){
        try {
            //recebendo a resposta do servidor
            String response;

            OutputStream os = emailSocket.getOutputStream();

            String fullUserCommand =  "USER " + this.usuario + "\r\n";
            os.write(fullUserCommand.getBytes("US-ASCII"));

            String fullPassCommand =  "PASS " + this.senha + "\r\n";
            os.write(fullPassCommand.getBytes("US-ASCII"));

            response = br.readLine();

            if (!response.startsWith("250")) {
                return false;
            }

        }catch (IOException e){
            return false;
        }

        return true;
    }

    public Map<Integer, String> receberEmail() throws IOException{

        String response, command;
        Map<Integer, String> emails = new HashMap<>();
        OutputStream os = emailSocket.getOutputStream();

        command = "LIST ";
        os.write(command.getBytes("US-ASCII"));
        //VERIFICAR SE ESSE COMANDO RETORNA SO A QUANTIDADE DE EMAILS.
        int email_index = 0;
        while((response = br.readLine()) != null){
            emails.put(email_index,response);
            email_index++;
        }

        return emails;
    }

    public String sendCommand(String command) throws IOException{
        OutputStream os = emailSocket.getOutputStream();

        command += "\n";
        os.write(command.getBytes("US-ASCII"));
        os.flush();
        String response = br.readLine();

        return response;
    }

}
