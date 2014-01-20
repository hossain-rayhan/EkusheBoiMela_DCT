package com.ekhusheboimela.dct;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	GPSTracker gps;
	Button btnAdd;
	EditText etPublisherName,etStallNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnAdd = (Button) findViewById(R.id.btn_add);
		etPublisherName = (EditText)findViewById(R.id.et_publisher_name);
		etStallNo = (EditText) findViewById(R.id.et_stall_no);


		btnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				// create class object
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled     
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    writeIntoFile(latitude, longitude);

                    // \n is for new line
                        
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                
                
				
		        

			}

			private void writeIntoFile(double lati, double longi) {
				String extStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();				
				File directoryPath = new File( extStoragePath + "/EkusheBoiMela");
				File filePath = new File(directoryPath, "publisher_info.txt");
				
				if (!directoryPath.exists()) {
					directoryPath.mkdirs();
				}
				
				if (!filePath.exists()) {
					try {
						filePath.createNewFile();
					} catch (IOException e) {
					e.printStackTrace();
					}
				}
				
		        try {
		        	FileWriter writer = new FileWriter(filePath, true);
		        	String publisherInfo = "0,"+etPublisherName.getText().toString()+","+etStallNo.getText().toString()+","+lati+","+longi+"\n";
					writer.append(publisherInfo);					
					writer.flush();
			        writer.close();
			        etPublisherName.setText("");
			        etStallNo.setText("");
			        Toast.makeText(getApplicationContext(), "Added Successfully !!!", Toast.LENGTH_LONG).show();
			        
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}