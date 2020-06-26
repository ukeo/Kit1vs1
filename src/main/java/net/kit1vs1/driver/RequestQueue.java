package net.kit1vs1.driver;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class RequestQueue implements Runnable {

    private List<String> requests;
    private boolean running;
    private Thread thread;
    public RequestQueue()
    {
        this.requests = new ArrayList<String>();
        this.running = true;
        this.thread = new Thread(this);
    }
    public void setRunning(boolean running) {
        this.running = running;
        if (running)
            this.thread.start();
    }
    public void addToQueue(String qry)
    {
        this.requests.add(qry);
    }
    public void run() {
        while (this.running) {
            if (this.requests.size() > 0) {
                for (int i = 0; i < this.requests.size(); i++) {
                    String qry = (String)this.requests.get(i);
                    new FutureTask<>(new Runnable() {
                        @Override
                        public void run() {
                            PreparedStatement stmt = null;
                            try {
                                stmt = MySQL.getConnection().prepareStatement(qry);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            try {
                                stmt.executeUpdate();
                                stmt.close();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }, 1).run();
                    this.requests.remove(i);
                }
            }
            try
            {
                Thread.sleep(25L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
