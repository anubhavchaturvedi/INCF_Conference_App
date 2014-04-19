package org.g_node.incfconferenceapp.abstracts;

import org.g_node.incfconferenceapp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class Abstracts extends Fragment{
	
	private ListView abstractList;
	private EditText abstractSearch;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_abstract, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		abstractList = (ListView) getActivity().findViewById(R.id.abstracts_list);
		abstractSearch = (EditText) getActivity().findViewById(R.id.abstracts_search);
		
		populateList();
		
		super.onActivityCreated(savedInstanceState);
	}
	
	private void populateList()
	{
		String abstracts[]={"absrtact 1",
				"absrtact 2",
				"absrtact 3",
				"absrtact 4",
				"absrtact 5",};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,abstracts );
		
		abstractList.setAdapter(adapter);
	}
	
}
