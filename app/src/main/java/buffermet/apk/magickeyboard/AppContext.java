package buffermet.apk.magickeyboard;

import android.app.Application;

public class AppContext extends Application {
  private static AppContext ctxt;

  protected static AppContext getContext() {
    return ctxt;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    ctxt = this;
  }
}
