package com.bag.animalfacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Spinner;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<AnimalFact> animalFacts = new ArrayList<>();
    public RecyclerView recyclerView;
    public static boolean isRandomFragment;
    public DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AllFactsFragment())
                    .commit();
            isRandomFragment = false;
        } else {
            isRandomFragment = savedInstanceState.getBoolean("isRandomFragment");
        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRandomFragment", isRandomFragment);
    }


    public void onClick (View view) {

        if (isRandomFragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new AllFactsFragment()).commit();
            isRandomFragment = false;
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new RandomFactFragment()).commit();
            isRandomFragment = true;
        }
    }

    public void onClickRandomAll (View view)  {
        animalFacts.clear();
        if (isRandomFragment == false) {
            Spinner spinner = findViewById(R.id.spinner);
            AllFactAnimalAsyncTask allTask = new AllFactAnimalAsyncTask(this, spinner.getSelectedItem().toString());
            allTask.execute();
       } else {
            RandomFactAnimalAsyncTask randomTask = new RandomFactAnimalAsyncTask(this, getResources().getStringArray(R.array.animalslist));
            randomTask.execute();
        }
    }



    class AllFactAnimalAsyncTask extends AsyncTask<Void, Void, ArrayList<AnimalFact> > {

        private Context mContext;
        private String nameAnimal;

        AllFactAnimalAsyncTask (Context context, String nameAnimal) {
            mContext = context;
            this.nameAnimal=nameAnimal;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<AnimalFact> doInBackground(Void... param) {

            ArrayList<AnimalFact> anFact = new ArrayList<>();
                    URL githubEndpoint = null;
                    try {
                        githubEndpoint = new URL("https://cat-fact.herokuapp.com/facts?animal_type="+nameAnimal);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        HttpsURLConnection myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
                        InputStream inputStream = myConnection.getInputStream();
                        JsonReader jsonReader;
                        InputStreamReader responseBodyReader;
                        responseBodyReader = new InputStreamReader(inputStream, "UTF-8");
                        String upvotes = "";
                        String nameAnimaljson = "";
                        String data = "01-01-0001";
                        String fact = "";
                        jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject();
                        jsonReader.nextName();
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String key = jsonReader.nextName();
                            if (key.equals("text")) {
                                fact = jsonReader.nextString();
                            } else {
                                if (key.equals("type")) {
                                    nameAnimaljson = jsonReader.nextString();
                                } else {
                                    if (key.equals("updatedAt")) {
                                        data = jsonReader.nextString();
                                    } else {
                                        if (key.equals("upvotes")) {
                                            upvotes = jsonReader.nextString();
                                        } else {
                                            jsonReader.skipValue();
                                        }
                                    }
                                }
                            }
                        }// end while
                        jsonReader.endObject();
                        anFact.add(new AnimalFact(nameAnimaljson, data, fact, upvotes));
                    }
                        jsonReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            return anFact;
        }

        @Override
        protected void onPostExecute(ArrayList<AnimalFact> result) {
            animalFacts=result;
            reDate();
            super.onPostExecute(result);
        }
    }

    class RandomFactAnimalAsyncTask extends AsyncTask<String, Void, ArrayList<AnimalFact> > {

        private Context mContext;
        private String[] nameAnimal;

        RandomFactAnimalAsyncTask (Context context, String[] nameAnimal) {
            mContext = context;
            this.nameAnimal=nameAnimal;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<AnimalFact> doInBackground(String... nameAn) {
            ArrayList<AnimalFact> anFact = new ArrayList<>();
            for (int i = 0; i < nameAnimal.length; i++) {
            URL githubEndpoint = null;
            try {
                githubEndpoint = new URL("https://cat-fact.herokuapp.com/facts/random?animal_type="+nameAnimal[i]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpsURLConnection myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
                InputStream inputStream = myConnection.getInputStream();
                JsonReader jsonReader;
                InputStreamReader responseBodyReader;
                responseBodyReader = new InputStreamReader(inputStream, "UTF-8");
                String upvotes ="0";
                String nameAnimaljson="";
                String data="01-01-0001";
                String fact="";
                jsonReader = new JsonReader (responseBodyReader);
                jsonReader.beginObject();
                    while (jsonReader.hasNext()){
                        String key = jsonReader.nextName();
                        if (key.equals("text")) {
                            fact = jsonReader.nextString();
                        } else {
                            if (key.equals("type")) {
                                nameAnimaljson = jsonReader.nextString();
                            } else {
                                if (key.equals("updatedAt")) {
                                    data = jsonReader.nextString();
                                } else {
                                    if (key.equals("upvotes")) {
                                        upvotes = jsonReader.nextString();
                                    } else {
                                        jsonReader.skipValue();
                                    }
                                }
                            }
                        }
                    }// end while
                    jsonReader.endObject();
                    anFact.add(new AnimalFact (nameAnimaljson, data, fact, upvotes));
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }

            return anFact;
        }

        @Override
        protected void onPostExecute(ArrayList<AnimalFact> result) {
            animalFacts=result;
            reDate();
            super.onPostExecute(result);
        }

    }
    public void reDate() {

        recyclerView = findViewById(R.id.listActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new DataAdapter(getApplicationContext(), animalFacts);
        recyclerView.setAdapter(adapter);

    }
}
