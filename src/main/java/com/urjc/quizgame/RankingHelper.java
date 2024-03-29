package com.urjc.quizgame;

import android.widget.TableRow;
import android.widget.TableLayout;
import android.widget.TextView;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public class RankingHelper {

    String fichero = "ranking.txt";

    public static int MAX_SIZE = 10;


    public class Record implements Comparable<Record>{
        public String name;
        public int points;
        Record(String n, int p){name = n; points = p;}


        @Override
        public int compareTo(Record r) {
            return points < r.points ? 1
                    : points > r.points ? -1
                    : 0;
        }

    }

    static private List<Record> records = new ArrayList<>();

    public void addIfIsRecord(String name, int points){
        Record newRecord = new Record(name, points);
        if(records.isEmpty()){
            records.add(newRecord);
        }else {
            records.add(newRecord);
            if(records.size() > MAX_SIZE)
                removeLower();
        }
    }
    public String toString(){
        JSONObject obj = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            for (Record r : records) {
                JSONObject rec = new JSONObject();
                rec.put("name", r.name);
                rec.put("points", r.points);
                array.put(rec);
            }
            obj.put("ranking", array);
        }catch(JSONException exc){
            Log.d("Error", exc.toString());
        }
        return obj.toString();
    }
    public List<String> getLines(){
        List<String> list = new ArrayList<>();
        for(Record r : records){
            list.add(r.name + "\t" + r.points);
        }
        return list;
    }
    public void clear(){
        records.clear();
    }

    public void sort(){
        Collections.sort(records);
    }

    public int getLower(){
        int lower = 1000;
        for(Record r : records){
            if(r.points < lower)
                lower = r.points;
        }
        return lower;
    }
    public void removeLower(){
        int lower = 0;
        for(int i = 1; i < records.size()-1; i++){
            if(records.get(lower).points > records.get(i).points){
                lower = i;
            }
        }
        records.remove(lower);
    }

    public boolean isFull(){
        return records.size() >= MAX_SIZE;
    }
/*
    public void readRankingFile(){
        List <Record> readRanking = new ArrayList<>();
        try {
            FileInputStream fin = openFileInput(fichero);
            DataInputStream dis = new DataInputStream(fin);
            String text = dis.toString();
            JSONObject obj = new JSONObject(text);
            JSONArray array = obj.getJSONArray("ranking");
            for(int i = 0; i < array.length(); i++){
                JSONObject jo = array.getJSONObject(i);
                readRanking.add(new Record(jo.getString("name"), jo.getInt("points")));
            }
            records = readRanking;
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(JSONException e){
            e.printStackTrace();
        }
    }
    public void displayRankingToTable (TableLayout table){
       for(Record r : records){
           TableRow tr = new TableRow(this);
           TextView text = new TextView(this);
           text.setText("" + r.name + "\t\t" + r.points);
           tr.addView(text);
           table.addView(tr);
       }
    }
    public void saveRankingFile(){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(fichero, MODE_PRIVATE);
            fos.write(toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 */
}