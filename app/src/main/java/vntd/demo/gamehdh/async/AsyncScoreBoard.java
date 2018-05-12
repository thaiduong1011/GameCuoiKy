package vntd.demo.gamehdh.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vntd.demo.gamehdh.Config;
import vntd.demo.gamehdh.Score;
import vntd.demo.gamehdh.game.state.MenuState;
import vntd.demo.gamehdh.game.state.ScoreState;

public class AsyncScoreBoard extends AsyncTask<Void, Void, List<Score>> {
    MenuState menuState;

    public AsyncScoreBoard(MenuState menuState) {
        this.menuState = menuState;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Score> doInBackground(Void... voids) {
        URL url;
        HttpURLConnection conn = null;
        try {
            String url_name = Config.IPADDRESS + "score/GetListScore";

            Log.d("AA", url_name);

            url = new URL(url_name);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            int response_code = conn.getResponseCode();

            if (response_code == HttpURLConnection.HTTP_OK) {
                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                //Log.d("AA", "OK ne 2 " + result.toString());

                return getListScore(result.toString());
            } else {
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("AA", "error 1 => " + e.getMessage().toString());
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                // conn.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Score> result) {
        //this method will be running on UI thread
        Log.d("AA", result + " aa");
        if (result != null) {
            ScoreState.scoreArray = result;
            menuState.setCurrentState(new ScoreState());
        } else {
            Log.d("AA", "Khong load dc du lieu");
        }
    }


    private List<Score> getListScore(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
        List<Score> list = new ArrayList<>();
        JSONObject jsonObject;
        for (int i = 0; i < array.length(); i++) {
            jsonObject = array.getJSONObject(i);
            list.add(new Score(jsonObject.getInt("user_id"),
                    jsonObject.getString("user_name"),
                    jsonObject.getInt("score")));
        }
        Collections.sort(list, Score.comparator);
        return list;
    }


}
