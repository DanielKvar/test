package com.example.todo;

import android.app.Application;
import org.apache.commons.io.FileUtils;
import android.widget.Toast;

import com.example.structure.Tekma;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

public class MyApp extends Application {
    static private Gson gson;
    static private File file;
    public Tekma Tekma;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Tekma getCurrentTekma() {
        return Tekma;
    }

    public void setCurrentTekma(Tekma tekma) {
        Tekma = tekma;
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        }
        return gson;
    }

    private File getFile() {
        if (file == null) {
            File filesDir = getFilesDir();
            file = new File(filesDir, "toJson.json");
        }
        return file;
    }

    public void saveToFile() {
        try {
            FileUtils.writeStringToFile(getFile(), getGson().toJson(Tekma));
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean readFromFile() {
        if (!getFile().exists()) {
            return false;
        }
        try {
            Tekma = getGson().fromJson(FileUtils.readFileToString(getFile()), Tekma.class);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

