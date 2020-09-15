
package com.reactlibrary;

import android.media.AudioManager;
import android.app.NotificationManager;
import android.content.Context;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

public class RNRingerMode extends ReactContextBaseJavaModule {

	private String TAG = RNRingerMode.class.getSimpleName();
	private static final String VOL_VOICE_CALL = "call";
	private static final String VOL_SYSTEM = "system";
	private static final String VOL_RING = "ring";
	private static final String VOL_MUSIC = "music";
	private static final String VOL_ALARM = "alarm";
	private static final String VOL_NOTIFICATION = "notification";

	private final ReactApplicationContext reactContext;
	private AudioManager am;
	private NotificationManager nm;

	public RNRingerMode(ReactApplicationContext reactContext) {
		super(reactContext);
		this.reactContext = reactContext;
		am = (AudioManager) reactContext.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
		nm = (NotificationManager) reactContext.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public String getName() {
		return this.TAG;
	}

	@ReactMethod
	public void getRingerMode(Promise promise) {
		int mode = am.getRingerMode();
		switch(mode) {
			case AudioManager.RINGER_MODE_SILENT:
				promise.resolve("SILENT");
				break;
			case AudioManager.RINGER_MODE_NORMAL:
				promise.resolve("NORMAL");
				break;
			case AudioManager.RINGER_MODE_VIBRATE:
				promise.resolve("VIBRATE");
				break;
			default:
			    promise.resolve("UNKNOWN");
			    break;
		}
	}

    @ReactMethod
    public void getInterruptionFilter(Promise promise) {
        if (nm == null || android.os.Build.VERSION.SDK_INT < 23) {
            promise.resolve("UNKNOWN");
            return;
        }
        int filter = nm.getCurrentInterruptionFilter();
        switch(filter) {
            case NotificationManager.INTERRUPTION_FILTER_PRIORITY:
                promise.resolve("PRIORITY");
                break;
            case NotificationManager.INTERRUPTION_FILTER_ALARMS:
                promise.resolve("ALARMS");
                break;
            case NotificationManager.INTERRUPTION_FILTER_NONE:
                promise.resolve("NONE");
                break;
            case NotificationManager.INTERRUPTION_FILTER_ALL:
                promise.resolve("ALL");
                break;
            default:
                promise.resolve("UNKNOWN");
                break;
        }
    }

}
