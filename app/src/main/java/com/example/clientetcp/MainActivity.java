package com.example.clientetcp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button accion;
    Socket socket;
    BufferedWriter writer;
    boolean isUP = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accion = findViewById(R.id.accion);

        //Esta linea hace la solicitud de conexion al servidor
        new Thread(
                () -> {
                    try {
                        //Esta linea envia solicitud de conexion
                        //En la seccion del host voy a poner la IP del servidor
                        //En el puerto, voy a poner el puerto en el que escucha el servidor
                        socket = new Socket("172.30.165.157", 5000);


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

        accion.setOnTouchListener(
                (v, event) -> {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            isUP = false;
                            accion.setText("DOWN");
                            break;

                        case MotionEvent.ACTION_MOVE:
                            accion.setText("MOVE");
                            break;

                        case MotionEvent.ACTION_UP:
                            isUP = true;
                            accion.setText("UP");
                            break;
                    }
                    return true;
                }
        );

        new Thread(
                () -> {
                    while (true) {
                        while (isUP) {}
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
    }
}
