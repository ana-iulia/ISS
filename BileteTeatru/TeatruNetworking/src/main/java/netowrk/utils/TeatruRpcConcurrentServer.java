package netowrk.utils;


import netowrk.rpcprotocol.TeatruClientRpcWorker;
import teatru.services.ITeatruServices;

import java.net.Socket;

public class TeatruRpcConcurrentServer extends AbsConcurrentServer {
    public TeatruRpcConcurrentServer(int port) {
        super(port);
    }


    private ITeatruServices teatruServer;
    public TeatruRpcConcurrentServer(int port, ITeatruServices chatServer) {
        super(port);
        this.teatruServer = chatServer;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        TeatruClientRpcWorker worker=new TeatruClientRpcWorker(teatruServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}

