package fr.exemple.blind_test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

     ImageButton buttonStart;
     ImageButton buttonPause;
     ImageButton buttonStop;
     ImageButton buttonRandom;
  public   MediaPlayer mediaPlayer;
     SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = (ImageButton) findViewById(R.id.bt_start);
        buttonPause=(ImageButton)findViewById(R.id.bt_pause);
        buttonStop = (ImageButton) findViewById(R.id.bt_stop);
        buttonRandom = (ImageButton) findViewById(R.id.bt_random);

        //créer une liste pour stocker chaque image
        final List<Integer> listMusic = new ArrayList<>();
        listMusic.add(R.raw.alan);
        listMusic.add(R.raw.demain);
        listMusic.add(R.raw.dommage);
        listMusic.add(R.raw.heybrother);
        listMusic.add(R.raw.onsfaitdumal);
        listMusic.add(R.raw.surmaroute);
        listMusic.add(R.raw.revesbizarre);
        listMusic.add(R.raw.raprapetou);
        listMusic.add(R.raw.wait);
        listMusic.add(R.raw.warrior);
        listMusic.add(R.raw.baptisescommejamais);

        /* liste artiste */
        final ArrayList<String> arrayListArtiste=new ArrayList<>();
        arrayListArtiste.add("Alan Walker");
        arrayListArtiste.add("Bigflo et Oli");
        arrayListArtiste.add("Bigflo et Oli");
        arrayListArtiste.add("Avicii");
        arrayListArtiste.add("Black m");
        arrayListArtiste.add("Black m");
        arrayListArtiste.add("Orelsan");
        arrayListArtiste.add("OTH");
        arrayListArtiste.add("Maroon 5");
        arrayListArtiste.add("Mark With");
        arrayListArtiste.add("Golden Moustache");

        /* liste piste */
        final ArrayList<String> arrayListPiste=new ArrayList<>();
        arrayListPiste.add("Faded");
        arrayListPiste.add("Demain");
        arrayListPiste.add("Dommage");
        arrayListPiste.add("Hey brother");
        arrayListPiste.add("On s'fait du mal");
        arrayListPiste.add("Sur ma route");
        arrayListPiste.add("Reves bizarre");
        arrayListPiste.add("Le rap des rapetou");
        arrayListPiste.add("Wait");
        arrayListPiste.add("Warrior");
        arrayListPiste.add("Baptisés comme jamais");

        final Random random = new Random();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.GRAY);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.YELLOW, Color.RED);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 4000);
            }
        });

        buttonRandom.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                // connaitre la taille de la liste
                int limite = listMusic.size();

                // nombre aléatoire dans la liste
                int nombreAleatoire = random.nextInt(limite);

                // correspondance numero dans la liste avec poisson
                int ressourceMusic = listMusic.get(nombreAleatoire);

                //lance musique random
                mediaPlayer = MediaPlayer.create(getApplicationContext(), ressourceMusic);
                mediaPlayer.start();
                NotificationHelper notificationHelper = new NotificationHelper(MainActivity.this);
                notificationHelper.notify(1, false, arrayListPiste.get(nombreAleatoire),arrayListArtiste.get(nombreAleatoire));

            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                buttonStop.setEnabled(true);
                buttonStart.setEnabled(false);
                buttonPause.setEnabled(true);
                Toast.makeText(getApplicationContext(),"Play",Toast.LENGTH_SHORT).show();
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                buttonStop.setEnabled(true);
                buttonStart.setEnabled(true);
                buttonPause.setEnabled(false);
                Toast.makeText(getApplicationContext(),"Pause",Toast.LENGTH_SHORT).show();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                buttonPause.setEnabled(true);
                Toast.makeText(getApplicationContext(),"Stop",Toast.LENGTH_SHORT).show();
            }
        });
    }
}