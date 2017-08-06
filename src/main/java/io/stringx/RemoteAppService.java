package io.stringx;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.Map;

import io.stringx.TranslationInterface;

public final class RemoteAppService extends Service {

    private final IBinder binder = new TranslationInterface.Stub() {

        @Override
        public TranslationConfig getConfig() throws RemoteException {
            LL.d("Retrieving config");
            TranslationConfig translationConfig = new TranslationConfig();
            translationConfig.packageName = getPackageName();
            Linguist linguist = Linguist.get(getApplicationContext());
            if (linguist != null) {
                translationConfig.strings = linguist.fetch();
                translationConfig.original = Language.fromLocale(linguist.getAppLocale());
                translationConfig.desired = Language.fromLocale(linguist.getDeviceDefaultLocale());
            }
            LL.d("Got " + translationConfig.strings.size() + ": " + translationConfig.original + "=" + translationConfig.desired);
            return translationConfig;
        }

        @Override
        public void onTranslationCompleted(Map translation) throws RemoteException {
            LL.d("Applying translation");
            Linguist linguist = Linguist.get(getApplicationContext());
            if (linguist != null) {
                linguist.applyTranslation(translation);
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}