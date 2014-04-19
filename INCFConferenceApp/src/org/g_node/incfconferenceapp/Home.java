package org.g_node.incfconferenceapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.g_node.incfconferenceapp.R;
import org.markdown4j.Markdown4jProcessor;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Home extends Fragment{
	

	private TextView information;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		information = (TextView) getActivity().findViewById(R.id.homeText);
		
		populateInformation();
		
		super.onActivityCreated(savedInstanceState);
	}
	
	private void populateInformation()
	{
		
		String raw = readRawTextFile(getActivity(), R.raw.information);
        Markdown4jProcessor markDownPro = new Markdown4jProcessor();
        
        String html="";
        try {
			html = markDownPro.process(raw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        if(information == null)
        	Log.d("populateInformation","information is null");
        information.setText(Html.fromHtml(html));
	}
	
	private String readRawTextFile(Context ctx, int resId) {
        InputStream inputStream = ctx.getResources().openRawResource(resId);
        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while ((line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }
}
