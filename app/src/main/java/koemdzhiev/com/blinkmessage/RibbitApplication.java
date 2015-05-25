package koemdzhiev.com.blinkmessage;

import android.app.Application;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by koemdzhiev on 25/05/2015.
 */
public class RibbitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "sn7r0V8XOLdkVGS9TXplNBfackrO958mCro2PdEf", "xA8D4IejgaKzfWKk5rijHtD5Vw9vfQcWTfxiGMYp");
        ParseObject testObject = new ParseObject("TestObject");

        testObject.put("foo", "bar");
        testObject.saveInBackground();
        Toast.makeText(getBaseContext(),"CONNECTED!",Toast.LENGTH_LONG).show();
    }
}
