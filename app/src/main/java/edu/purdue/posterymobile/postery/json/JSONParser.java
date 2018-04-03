package edu.purdue.posterymobile.postery.json;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import edu.purdue.posterymobile.postery.CardInfo;

/**
 * Created by kane on 10/17/15.
 */

public class JSONParser {
    private URL url;

    public JSONParser(String jsonUrl) {
        try {
            this.url = new URL(jsonUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public List<CardInfo> loadCardsFromJson() {
        try {
            URLConnection conn = url.openConnection();
            return readJsonStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<CardInfo> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readCardsArray(reader);
        }finally{
            reader.close();
        }
    }

    public List readCardsArray(JsonReader reader) throws IOException {
        List cards = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            cards.add(readCard(reader));
        }
        reader.endArray();
        return cards;
    }

    public CardInfo readCard(JsonReader reader) throws IOException {
        long eid = -1;
        String title = null;
        String description = null;
        String imgurl = null;

        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("eid")) {
                eid = reader.nextLong();
            } else if (name.equals("title")) {
                title = reader.nextString();
            } else if (name.equals("description") && reader.peek() != JsonToken.NULL) {
                description = reader.nextString();
            } else if (name.equals("img")) {
                imgurl = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        CardInfo info = new CardInfo();
        info.cid = eid;
        info.title = title;
        info.desc = description;
        info.imgUrl = imgurl;

        return info;
    }
}
