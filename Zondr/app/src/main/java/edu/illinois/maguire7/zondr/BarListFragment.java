package edu.illinois.maguire7.zondr;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class BarListFragment extends ListFragment {
    private ArrayList<Bar> mBars;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBars = new ArrayList<Bar>();
        //updateBarStats();
        mBars.add(new Bar("Joe's".toUpperCase(), 50,50,50,2,5));
        mBars.add(new Bar("Cly's".toUpperCase(), 50,50,50,2,5));
        mBars.add(new Bar("Red Lion".toUpperCase(), 50,50,50,2,5));
        mBars.add(new Bar("White Hoe's".toUpperCase(), 50,50,50,2,5));
        mBars.add(new Bar("FireHaus".toUpperCase(), 50,50,50,2,5));
        mBars.add(new Bar("Murphys".toUpperCase(), 50,50,50,2,5));
        mBars.add(new Bar("Legends".toUpperCase(), 50,50,50,2,5));
        BarAdapter adapter = new BarAdapter(mBars);
        setListAdapter(adapter);
    }

    private void updateBarStats() {
        JSONArray barsJSONArray = getBarsJSONArray();
        for(int i = 0 ; i<barsJSONArray.length() ; i++){
            try {
                JSONObject barJSON = barsJSONArray.getJSONObject(i);
                Bar barToAdd = new Bar(barJSON.getString("bar_name"), barJSON.getInt("gents"), barJSON.getInt("ladies"), barJSON.getInt("capacity"), barJSON.getInt("line_length"), barJSON.getInt("cover") );
                mBars.add(barToAdd);
            }
            catch (JSONException e){}
        }
    }

    private JSONArray getBarsJSONArray() {
        JSONArray object = null;
        try{
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://zondr.com/service.php");
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                InputStream instream = entity.getContent();
                String JSONResult = convertStreamToString(instream);
                object = new JSONArray(JSONResult);

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static String convertStreamToString(InputStream instream) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader((new InputStreamReader(instream)));

        try{
            while((rLine = rd.readLine()) != null){
                answer.append(rLine);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return answer.toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View barListView = inflater.inflate(R.layout.fragment_bar_list, container, false);
        return barListView;
    }


    private class BarAdapter extends ArrayAdapter<Bar> {
        public BarAdapter(ArrayList<Bar> bars){
            super(getActivity(), 0, bars);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bar_list_item, null);
            }
            Bar bar = this.getItem(position);
            TextView barNameTextView = (TextView) convertView.findViewById(R.id.bar_list_item_barName);
            TextView percentLadiesTextView = (TextView) convertView.findViewById(R.id.bar_list_item_percentLadiesTextView);
            TextView percentGentsTextView = (TextView) convertView.findViewById(R.id.bar_list_item_percentGentsTextView);
            TextView coverCostTextView = (TextView) convertView.findViewById(R.id.bar_list_item_coverCostTextView);
            TextView coverTextTextView = (TextView) convertView.findViewById(R.id.bar_list_item_coverTextTextView);
            ImageView capacityImageView = (ImageView) convertView.findViewById(R.id.list_item_bar_capacityImageView);
            ImageView lineLengthImageView = (ImageView) convertView.findViewById(R.id.bar_list_item_lineLengthImageView);

            barNameTextView.setText(bar.getBarName());
            percentLadiesTextView.setText(String.valueOf(bar.getPercentLadies())+"%");
            percentGentsTextView.setText(String.valueOf(bar.getPercentGents())+"%");
            coverCostTextView.setText("$ " +String.valueOf(bar.getCoverCost()));


            Typeface helveticaNeue_UltraLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HelveticaNeue-UltraLight.otf");
            barNameTextView.setTypeface(helveticaNeue_UltraLight);
            percentLadiesTextView.setTypeface(helveticaNeue_UltraLight);
            percentGentsTextView.setTypeface(helveticaNeue_UltraLight);
            coverCostTextView.setTypeface(helveticaNeue_UltraLight);
            coverTextTextView.setTypeface(helveticaNeue_UltraLight);

            capacityImageView.setImageResource(getCapacityImageResource(bar.getCapacity()));
            lineLengthImageView.setImageResource(getLineLengthImageResource(bar.getLineLength()));


            return convertView;

        }


        private int getCapacityImageResource(int capacity) {
            if(capacity <= 10) return R.drawable.capacity10;
            else if(capacity <= 20) return R.drawable.capacity20;
            else if(capacity <= 35) return R.drawable.capacity35;
            else if(capacity <= 45) return R.drawable.capacity45;
            else if(capacity <= 60) return R.drawable.capacity60;
            else if(capacity <= 70) return R.drawable.capacity70;
            else if(capacity <= 85) return R.drawable.capacity85;
            else return R.drawable.capacity95;
        }

        private int getLineLengthImageResource(int lineLength) {
            if(lineLength <= 1) return R.drawable.line1;
            else if(lineLength <= 2) return R.drawable.line2;
            else if(lineLength <= 3) return R.drawable.line3;
            else if(lineLength <= 4) return R.drawable.line4;
            else if(lineLength <= 5) return R.drawable.line5;
            else return R.drawable.line6;     }
    }



}
