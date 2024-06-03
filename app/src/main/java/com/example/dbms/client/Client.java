package com.example.dbms.client;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Client {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String input, output;
    private boolean connected, update;
    private String ip;

    public Client(String ip) {
        this.ip = ip;
        connected = false;
        input = "";

        getConnection();
    }

    public void getConnection() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ip, 800);

                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream());

                    output = "";

                    connected = true;

                    while (connected) {
                        String temp = "";
                        if (!(temp = dis.readUTF()).equals("Test_Connection")) {
                            input = temp;
                        }

                        if (!input.isEmpty()) {
                            update = true;
                        }
                    }
                } catch (IOException e1) {
                    connected = false;

                    try {
                        socket.close();
                    } catch (IOException e2) {
                        Log.i("Debug", "Something is Fucked");
                    } catch (NullPointerException e3) {
                        Log.i("Debug", "Something Went Wrong");
                    }
                }
            }
        });

        thread.start();
    }

    public boolean sendOutput(String output) {
        this.output = output;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!output.equals("")) {
                        dos.writeUTF(output);
                        dos.flush();
                    }
                } catch (IOException e1) {
                    Log.i("Debug", "Something is Fucked");
                }
            }
        });

        thread.start();

        for (int i = 0; i < 100; i++) {
            Log.i("Debug", String.format("%s, %s", connected, update));

            if (connected && !update) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return true;
    }

    public boolean getConnected() {return connected;}

    public void setIp(String ip) {this.ip = ip;}

    public String[] getStoreInfo(String storeID) {
        sendOutput(String.format("getStoreInfo/cmdend/%s", storeID));

        for (int i = 0; i < 100 && !update; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e1) {
                Log.i("Debug", "sleep Interrupted");
            }
        }

        String[] storeInfo = input.split("/SPLIT/");

        input = "";
        update = false;

        return storeInfo;
    }

    public ArrayList<String> getPath(ArrayList<String> targets) {
        String temp = "";
        for (String s: targets) {
            temp += String.format("%s/ADD/", s);
        }
        sendOutput(String.format("calculatePath/cmdend/%s", temp));

        for (int i = 0; i < 100 && !update; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e1) {
                Log.i("Debug", "sleep Interrupted");
            }
        }

        ArrayList<String> path = new ArrayList<String>();
        path.addAll(Arrays.asList(input.split("/->/")));

        input = "";
        update = false;

        return path;
    }

    //getShelfProduct
    public ArrayList<String> getProduct(String shelfID) {
        sendOutput(String.format("getProducts/cmdend/%s", shelfID));
        //picture
        //name
        //price
        //description
        //comments
        //storeID

        for (int i = 0; i < 100 && !update; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e1) {
                Log.i("Debug", "sleep Interrupted");
            }
        }

        ArrayList<String> products = new ArrayList<>();
        products.addAll(Arrays.asList(input.split("/ADD/")));

        input = "";
        update = false;

        return products;
    }

    public ArrayList<String> getStoreProducts(String storeID) {
        sendOutput(String.format("getStoreProducts/cmdend/%s", storeID));

        for (int i = 0; i < 100 && !update; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e1) {
                Log.i("Debug", "sleep Interrupted");
            }
        }

        ArrayList<String> products = new ArrayList<>();
        products.addAll(Arrays.asList(input.split("/ADD/")));

        input = "";
        update = false;

        return products;
    }

    public String getTargetShelf(String storeID, String productName) {
        sendOutput(String.format("getTargetShelves/cmdend/%s/ADD/%s", storeID, productName));

        for (int i = 0; i < 100 && !update; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e1) {
                Log.i("Debug", "sleep Interrupted");
            }
        }

        String targetShelf = "";

        if (!input.equals("NotFound")) {
            targetShelf = input;
        }

        input = "";
        update = false;

        return targetShelf;
    }

    public boolean registerUser(String userID, String password, String username) {
        sendOutput(String.format("registerUser/cmdend/%s/ADD/%s/ADD/%s", userID, password, username));

        for (int i = 0; i < 100 && !update; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e1) {
                Log.i("Debug", "sleep Interrupted");
            }
        }

        boolean foundUser = input.equals("True");

        input = "";
        update = false;

        return foundUser;
    }

    public ArrayList<String> getUserComments(String userID) {
        sendOutput(String.format("getUserComments/cmdend/%s", userID));

        for (int i = 0; i < 100 && !update; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e1) {
                Log.i("Debug", "sleep Interrupted");
            }
        }

        ArrayList<String> comments = new ArrayList<String>();
        comments.addAll(Arrays.asList(input.split("/ADD/")));

        input = "";
        update = false;

        return comments;
    }

    public ArrayList<String> getProductComments(String productID) {
        sendOutput(String.format("getProductComments/cmdend/%s", productID));

        for (int i = 0; i < 100 && !update; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e1) {
                Log.i("Debug", "sleep Interrupted");
            }
        }

        ArrayList<String> comments = new ArrayList<String>();
        comments.addAll(Arrays.asList(input.split("/ADD/")));

        input = "";
        update = false;

        return comments;
    }

    public void writeComments(String userID, String product, String comment) {
        sendOutput(String.format("writeComments/cmdend/%s/ADD/%s/ADD/%s", product, userID, comment));

        for (int i = 0; i < 100 && !update; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e1) {
                Log.i("Debug", "sleep Interrupted");
            }
        }

        input = "";
        update = false;
    }

    public ArrayList<String> getHistoryCart(String userID) {
        sendOutput(String.format("getHistoryCart/cmdend/%s", userID));

        for (int i = 0; i < 100 && !update; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e1) {
                Log.i("Debug", "sleep Interrupted");
            }
        }

        ArrayList<String> carts = new ArrayList<String>();
        carts.addAll(Arrays.asList(input.split("/ADD/")));

        input = "";
        update = false;

        return carts;
    }

    public String getUser(String userID, String password) {
        sendOutput(String.format("getUser/cmdend/%s/ADD/%s", userID, password));

        for (int i = 0; i < 100 && !update; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e1) {
                Log.i("Debug", "sleep Interrupted");
            }
        }

        String foundUser = "";

        if (!input.equals("NotFound")) {
            foundUser = input;
        }

        input = "";
        update = false;

        return foundUser;
    }

    public void close() {
        connected = false;

        try {
            socket.close();
        } catch (IOException e2) {
            Log.i("Debug", "Something is Fucked");
        } catch (NullPointerException e3) {
            Log.i("Debug", "Something Went Wrong");
        }
    }
}
