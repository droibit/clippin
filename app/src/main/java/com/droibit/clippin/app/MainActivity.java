package com.droibit.clippin.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.droibit.app.Clippin;

public class MainActivity extends AppCompatActivity {

    private View mContainerView;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainerView = findViewById(R.id.container);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        // 初回はコンテナがGONEのためサイズが取れていない？
        if (mContainerView.getVisibility() == View.GONE) {
            Clippin.animate()
                    .target(mContainerView)
                    .circleCenter(v)
                    .show(null);
        } else {
            Clippin.animate()
                    .target(mContainerView)
                    .circleCenter(v)
                    .hide(null);
        }
    }
}
