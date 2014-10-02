package edu.illinois.maguire7.zondr;

import android.app.Activity;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;



public class BarListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new BarListFragment();
    }
}
