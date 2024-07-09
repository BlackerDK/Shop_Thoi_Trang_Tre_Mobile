package com.example.shop_thoi_trang_mobile.adapter;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CustomDateTypeAdapter extends TypeAdapter<Date> {
    private final SimpleDateFormat dateFormat;

    public CustomDateTypeAdapter() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        out.value(dateFormat.format(value));
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        try {
            return dateFormat.parse(in.nextString());
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}
