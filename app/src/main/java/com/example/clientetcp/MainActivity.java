package com.example.clientetcp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
                        socket = new Socket("172.30.171.44", 5000);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();

        accion.setOnClickListener(
                (v) -> {
                    new Thread(
                            () -> {
                                try {
                                    //Definiendo corriente de salida
                                    OutputStream os = socket.getOutputStream();

                                    //Como necesitamos mandar String, entonces vamos a mandar  siguientes objetos
                                    //Buffer = espacio para almacenar bytes temporalmente
                                    OutputStreamWriter osw = new OutputStreamWriter(os);

                                    //Esta linea nos permite crear un objeto que manda Strings pero con buffer
                                    BufferedWriter writer = new BufferedWriter(osw);

                                    //\n o salto de linea = indica final del mensaje
                                    writer.write("Hola desde Android\n");
                                    writer.flush();


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    ).start();
                }
        );
    }
}
