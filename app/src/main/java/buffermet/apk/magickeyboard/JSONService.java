package buffermet.apk.magickeyboard;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

class JSONService {
    static JSONObject loadJSON() {
        JSONObject app = new JSONObject();

        try {
            final FileInputStream in = AppContext.getContext().openFileInput("config.json");
            final InputStreamReader inputStreamReader = new InputStreamReader(in);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            final StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            app = new JSONObject(sb.toString());
            in.close();
        } catch (FileNotFoundException e) {
            try {
                final InputStream in = AppContext.getContext().getAssets().open("config.json");
                final int len = in.available();
                final byte[] buffer = new byte[len];
                in.read(buffer);
                in.close();
                app = new JSONObject(new String(buffer, "UTF-8"));
                saveJSON(app);
            } catch (Exception e1) {
                e1.printStackTrace();
                final StringWriter writer = new StringWriter();
                e1.printStackTrace(new PrintWriter(writer));
                DebugService.writeLogs("Error trying to read or copy initial config file.\n" + writer.toString() + "\n\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            final StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            DebugService.writeLogs("Error trying to read existing config file.\n" + writer.toString() + "\n\n");
        }

        return app;
    }

    static void saveJSON(final JSONObject app) {
        try {
            final FileOutputStream outputStream = AppContext.getContext().openFileOutput("config.json", Context.MODE_PRIVATE);
            outputStream.write(app.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            final StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            DebugService.writeLogs("Error trying to save config file.\n" + writer.toString() + "\n\n");
        }
    }

    static boolean containsStringIgnoreCase(JSONArray array, String string) {
        try {
            for (int i = 0; i < array.length(); i++) {
                final String s = array.getString(i);

                if (s.equalsIgnoreCase(string)) {
                    return true;
                }
            }
        } catch (Exception ignore) {
            // do nothing
        }

        return false;
    }

    static boolean containsString(JSONArray array, String string) {
        try {
            for (int i = 0; i < array.length(); i++) {
                if (array.getString(i).equals(string)) {
                    return true;
                }
            }
        } catch (Exception ignore) {
            // do nothing
        }

        return false;
    }

    static int indexOfString(JSONArray array, String string) {
        try {
            for (int i = 0; i < array.length(); i++) {
                if (array.getString(i).equals(string)) {
                    return i;
                }
            }
        } catch (Exception ignore) {
            // do nothing
        }

        return -1;
    }
}
