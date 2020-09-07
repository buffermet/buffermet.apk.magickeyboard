package buffermet.apk.magickeyboard;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

class DebugService {
  private static void debug(final String message) {
    Toast.makeText(AppContext.getContext(), "debug\n\n" + message, Toast.LENGTH_LONG).show();
    Log.i("$$$ $$$ $$$", message);
  }

  private static String loadLogs() {
    String logStr = "";

    try {
      final FileInputStream in = AppContext.getContext().openFileInput("log");
      final InputStreamReader inputStreamReader = new InputStreamReader(in);
      final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      final StringBuilder sb = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        sb.append(line).append("\n");
      }
      logStr = sb.toString();
      in.close();
    } catch (FileNotFoundException e) {
      try {
        final InputStream in = AppContext.getContext().getAssets().open("log");
        final int len = in.available();
        final byte[] buffer = new byte[len];
        in.read(buffer);
        in.close();
        logStr = new String(buffer, Charset.forName("UTF-8"));
      } catch (Exception e1) {
        debug(e1.toString());
        final StringWriter writer = new StringWriter();
        e1.printStackTrace(new PrintWriter(writer));
        DebugService.writeLogs(writer.toString() + "\n\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
      final StringWriter writer = new StringWriter();
      e.printStackTrace(new PrintWriter(writer));
      DebugService.writeLogs(writer.toString() + "\n\n");
    }

    return logStr;
  }

  static void writeLogs(final String message) {
    try {
      final String logs = loadLogs();

      final FileOutputStream outputStream = AppContext.getContext().openFileOutput("log", Context.MODE_PRIVATE);
      outputStream.write((logs + message).getBytes());
      outputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
      final StringWriter writer = new StringWriter();
      e.printStackTrace(new PrintWriter(writer));
      DebugService.writeLogs(writer.toString() + "\n\n");
    }
  }

//    static void clearLogs() {
//        try {
//            final FileOutputStream outputStream = AppContext.getContext().openFileOutput("log", Context.MODE_PRIVATE);
//            outputStream.write(new byte[0]);
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            final StringWriter writer = new StringWriter();
//            e.printStackTrace(new PrintWriter(writer));
//            DebugService.writeLogs(writer.toString() + "\n\n");
//        }
//    }

  static void logStackTrace(final Exception e) {
    e.printStackTrace();
    final StringWriter writer = new StringWriter();
    e.printStackTrace(new PrintWriter(writer));
    writeLogs(writer.toString() + "\n\n");
    debug("logStackTrace\n\n" + writer.toString());
  }
}
