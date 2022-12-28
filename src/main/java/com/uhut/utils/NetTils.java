package com.uhut.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;

public class NetTils {
    private final String url;
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;

    public NetTils(String url) {
        this.url = url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setTts(boolean tts) {
        this.tts = tts;
    }

    public void execute() throws IOException {
        if (this.content == null) {
            throw new IllegalArgumentException("Set content or add at least one EmbedObject");
        } else {
            NetTils.JSONObject json = new NetTils.JSONObject();
            json.put("content", this.content);
            json.put("username", this.username);
            json.put("avatar_url", this.avatarUrl);
            json.put("tts", this.tts);
            URL url = new URL(this.url);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStream stream = connection.getOutputStream();
            stream.write(json.toString().getBytes());
            stream.flush();
            stream.close();
            connection.getInputStream().close();
            connection.disconnect();
        }
    }

    private class JSONObject {
        private final HashMap<String, Object> map = new HashMap();

        private JSONObject() {
        }

        void put(String key, Object value) {
            if (value != null) {
                this.map.put(key, value);
            }

        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            Set<Entry<String, Object>> entrySet = this.map.entrySet();
            builder.append("{");
            int i = 0;

            for(Entry<String, Object> entry : entrySet) {
                Object val = entry.getValue();
                builder.append(this.quote((String)entry.getKey())).append(":");
                if (val instanceof String) {
                    builder.append(this.quote(String.valueOf(val)));
                } else if (val instanceof Integer) {
                    builder.append(Integer.valueOf(String.valueOf(val)));
                } else if (val instanceof Boolean) {
                    builder.append(val);
                } else if (val instanceof NetTils.JSONObject) {
                    builder.append(val.toString());
                } else if (val.getClass().isArray()) {
                    builder.append("[");
                    int len = Array.getLength(val);

                    for(int j = 0; j < len; ++j) {
                        builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
                    }

                    builder.append("]");
                }

                ++i;
                builder.append(i == entrySet.size() ? "}" : ",");
            }

            return builder.toString();
        }

        private String quote(String string) {
            return "\"" + string + "\"";
        }
    }
}
