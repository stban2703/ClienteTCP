package com.example.clientetcp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button accionUp;
    private Button accionDown;
    private Button accionLeft;
    private Button accionRight;
    private Button accionColor;

    Socket socket;
    BufferedWriter writer;
    boolean isUP = true;
    boolean isDOWN = true;
    boolean isLEFT = true;
    boolean isRIGHT = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accionUp = findViewById(R.id.accionUp);
        accionDown = findViewById(R.id.accionDown);
        accionLeft = findViewById(R.id.accionLeft);
        accionRight = findViewById(R.id.accionRight);
        accionColor = findViewById(R.id.accionColor);

        //Esta linea hace la solicitud de conexion al servidor
        new Thread(
                () -> {
                    try {
                        //Esta linea envia solicitud de conexion
                        //En la seccion del host voy a poner la IP del servidor
                        //En el puerto, voy a poner el puerto en el que escucha el servidor
                        socket = new Socket("192.168.0.8", 5000);


                        //------------------------------------------//
                        //Definiendo corriente de salida
                        OutputStream os = socket.getOutputStream();

                        //Como necesitamos mandar String, entonces vamos a mandar  siguientes objetos
                        //Buffer = espacio para almacenar bytes temporalmente
                        OutputStreamWriter osw = new OutputStreamWriter(os);

                        //Esta linea nos permite crear un objeto que manda Strings pero con buffer
                        writer = new BufferedWriter(osw);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();

        accionUp.setOnTouchListener(
                (v, event) -> {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            isUP = false;
                            //accionUp.setText("DOWN");
                            break;

                        case MotionEvent.ACTION_MOVE:
                            //accionUp.setText("MOVE");
                            break;

                        case MotionEvent.ACTION_UP:
                            isUP = true;
                            //accionUp.setText("UP");
                            break;
                    }
                    return true;
                }
        );

        accionDown.setOnTouchListener(
                (v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            isDOWN = false;
                            break;

                        case MotionEvent.ACTION_MOVE:
                            break;

                        case MotionEvent.ACTION_UP:
                            isDOWN = true;
                            break;
                    }
                    return true;
                }
        );

        accionLeft.setOnTouchListener(
                (v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            isLEFT = false;
                            break;

                        case MotionEvent.ACTION_MOVE:
                            break;

                        case MotionEvent.ACTION_UP:
                            isLEFT = true;
                            break;
                    }
                    return true;
                }
        );

        accionRight.setOnTouchListener(
                (v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            isRIGHT = false;
                            break;

                        case MotionEvent.ACTION_MOVE:
                            break;

                        case MotionEvent.ACTION_UP:
                            isRIGHT = true;
                            break;
                    }
                    return true;
                }
        );

        accionColor.setOnClickListener(
                //Envía mensaje para cambiar el color
                (v) -> {
                    new Thread(
                            () -> {
                                try {
                                    Thread.sleep(300);
                                    writer.write("COLOR\n");
                                    writer.flush();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                    ).start();
                });


        //Envia mensaje para subir
        new Thread(
                () -> {
                    while (true) {
                        while (isUP) {
                        }
                        try {
                            Thread.sleep(300);
                            writer.write("UP\n");
                            writer.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();

        //Envia mensaje para bajar
        new Thread(
                () -> {
                    while (true) {
                        while (isDOWN) {
                        }
                        try {
                            Thread.sleep(300);
                            writer.write("DOWN\n");
                            writer.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();

        //Envia mensaje para ir a la izquierda
        new Thread(
                () -> {
                    while (true) {
                        while (isLEFT) {
                        }
                        try {
                            Thread.sleep(300);
                            writer.write("LEFT\n");
                            writer.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();

        //Envia mensaje para ir a la derecha
        new Thread(
                () -> {
                    while (true) {
                        while (isRIGHT) {
                        }
                        try {
                            Thread.sleep(300);
                            writer.write("RIGHT\n");
                            writer.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }
}
