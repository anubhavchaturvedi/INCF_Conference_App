package org.g_node.incfconferenceapp.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.g_node.incfconferenceapp.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class INCFLocations extends MapFragment{

	private static GoogleMap mMap;
	
	private View view;
	
    private ArrayList<LatLng> allCoordinates = new ArrayList<LatLng>();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		Log.d("INCFLocations > onCreateView","Started");
        // Inflate the layout for this fragment
		Log.d("INCFLocations > onCreateView","Inflating the layout");
		view = inflater.inflate(R.layout.fragment_map, container, false);
		
		Log.d("INCFLocations > onCreateView","Calling locationMarkers()");
        
        Log.d("INCFLocations > onCreateView","Getting the Google Map object");
        mMap = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        
		locationMarkers();
		Log.d("INCFLocations > onCreateView","Location markers added");

        return view;
    }

    public void locationMarkers() {
        /*
         * Implement Location Markers
         */
        BufferedReader jsonReader = new BufferedReader(new InputStreamReader(this.getResources()
                .openRawResource(R.raw.map)));
        StringBuilder jsonBuilder = new StringBuilder();
        try {
            for (String line = null; (line = jsonReader.readLine()) != null;) {
                jsonBuilder.append(line).append("\n");
            }

            JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
            JSONArray jsonArray = new JSONArray(tokener);

            for (int index = 0; index < jsonArray.length(); index++) {

                JSONObject jsonObject = jsonArray.getJSONObject(index);
                /*
                 * getting Latitude
                 */
                double getLat = jsonObject.getJSONObject("point").getDouble("lat");
                /*
                 * getting Longitude
                 */
                double getLng = jsonObject.getJSONObject("point").getDouble("long");
                /*
                 * getting Location Type
                 */
                int gettype = jsonObject.getInt("type");
                /*
                 * getting zoomto value
                 */
                int getZoomto = jsonObject.getInt("zoomto");
                /*
                 * Venue name
                 */
                String name = jsonObject.getString("name");
                LatLng myLoc = new LatLng(getLat, getLng);
                if (getZoomto == 1) {
                    /*
                     * Adding only food and venue coordinates for automatic zoom
                     * level
                     */
                    allCoordinates.add(myLoc);
                }
                
                if (mMap != null) {
                    /*
                     * implementing different colors markers for different
                     * location's
                     */
                    switch (gettype) {
                        case 0:
                            /*
                             * Conference Venue Marker
                             */
                            mMap.addMarker(new MarkerOptions()
                                    .position(myLoc)
                                    .title(name)
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(R.drawable.conference)));
                            break;
                        case 1:
                            /*
                             * University Marker
                             */
                            mMap.addMarker(new MarkerOptions()
                                    .position(myLoc)
                                    .title(name)
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(R.drawable.university)));
                            break;
                        case 2:
                            /*
                             * Hotel -1 Marker
                             */
                            mMap
                                    .addMarker(new MarkerOptions()
                                            .position(myLoc)
                                            .title(name)
                                            .icon(BitmapDescriptorFactory
                                                    .fromResource(R.drawable.hotel_1)));
                            break;
                        case 3:
                            /*
                             * Hotel -2 Marker
                             */
                            mMap
                                    .addMarker(new MarkerOptions()
                                            .position(myLoc)
                                            .title(name)
                                            .icon(BitmapDescriptorFactory
                                                    .fromResource(R.drawable.hotel_2)));
                            break;
                        case 4:
                            /*
                             * Transport Marker
                             */
                            mMap.addMarker(new MarkerOptions()
                                    .position(myLoc)
                                    .title(name)
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(R.drawable.transport)));
                            break;
                        case 5:
                            /*
                             * Food Marker
                             */
                            mMap.addMarker(new MarkerOptions().position(myLoc).title(name)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.food)));
                            break;

                        default:
                            break;
                    }
                }

            }

            /*
             * Automatic zoom level
             */
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng m : allCoordinates) {
                builder = builder.include(m);
            }
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, this.getResources()
                    .getDisplayMetrics().widthPixels,
                    this.getResources().getDisplayMetrics().heightPixels, 50);
            /*
             * Move Camera
             */
            mMap.moveCamera(cu);
            /*
             * Set My Current Location Enable
             */
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            /*
             * Set Compass Enable
             */
            mMap.getUiSettings().setCompassEnabled(true);
            /*
             * Set Manual ZoomControl Enable
             */
            mMap.getUiSettings().setZoomControlsEnabled(false);
        } catch (FileNotFoundException e) {
            Log.e("jsonFile", "file not found");
        } catch (IOException e) {
            Log.e("jsonFile", "ioerror");
        } catch (JSONException e) {
            Log.e("jsonFile", Log.getStackTraceString(e));
        }

    }
    
    public void onDestroyView() 
    {
            super.onDestroyView(); 
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            android.app.Fragment fragment = (fragmentManager.findFragmentById(R.id.map));  
            FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
    }


}

