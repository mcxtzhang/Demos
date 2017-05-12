package com.mcxtzhang.realmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Initialize Realm
        Realm.init(MainActivity.this);
        realm = Realm.getDefaultInstance();


        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "zxt/realm";

            @Override
            public void onClick(View v) {
                // Use them like regular java objects
                Worker dog = new Worker();
                dog.setName("Rex");
                dog.setAge(1);

// Query Realm for all dogs younger than 2 years old
                final RealmResults<Worker> puppies = realm.where(Worker.class).lessThan("age", 2).findAll();
                Log.d(TAG, "onClick() called with: puppies.size(); = [" + puppies.size() + "]");
                // => 0 because no dogs have been added to the Realm yet

// Persist your data in a transaction
                realm.beginTransaction();
                final Worker managedDog = realm.copyToRealm(dog); // Persist unmanaged objects
                Company person = realm.createObject(Company.class, 2); // Create managed objects directly
                person.getWorkers().add(managedDog);
                realm.commitTransaction();

// Listeners will be notified when data changes
                puppies.addChangeListener(new RealmChangeListener<RealmResults<Worker>>() {
                    @Override
                    public void onChange(RealmResults<Worker> results) {
                        // Query results are updated in real time
                        Log.d(TAG, "onChange() called with: results = [" + results.size() + "]");
                        Log.d(TAG, "onChange() called with: puppies.size(); = [" + puppies.size() + "]");
                        // => 1
                    }
                });

// Asynchronously update objects on a background thread
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        Worker dog = bgRealm.where(Worker.class).equalTo("age", 1).findFirst();
                        dog.setAge(3);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess() puppies.size():" + puppies.size());
                        Log.d(TAG, "onSuccess() managedDog.getAge():" + managedDog.getAge());
                        // Original queries and Realm objects are automatically updated.
                        puppies.size(); // => 0 because there are no more puppies younger than 2 years old
                        managedDog.getAge();   // => 3 the dogs age is updated
                    }
                });
            }
        });


        findViewById(R.id.btnQuery).setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "zxt/reaml";

            @Override
            public void onClick(View v) {
                Worker first = realm.where(Worker.class).findFirst();
                Log.d(TAG, "onClick() called with: first = [" + first + "]");
            }
        });
    }
}
