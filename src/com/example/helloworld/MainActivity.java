package com.example.helloworld;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button button_calculate_bmi;
	EditText height_input;
	EditText weight_input; 
	TextView result_text;
	TextView comment_text;
	Spinner spinner_sex;
	
	DecimalFormat nf = new DecimalFormat("0.00");
	
	double height;
	double weight;
	double bmi_result;
	
	boolean gender = true;

	// ============================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TabHost tabHost = (TabHost)findViewById(R.id.tab_host);
		tabHost.setup();		

		TabSpec spec = tabHost.newTabSpec("tab1");
		spec.setContent(R.id.tab_bmi).setIndicator("BMI");


		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab2");
		spec.setContent(R.id.tab_rank).setIndicator("Rank");

		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		button_calculate_bmi = (Button) findViewById(R.id.main_calculate_bmi_button);
		height_input = (EditText) findViewById(R.id.main_height_input);
		weight_input = (EditText) findViewById(R.id.main_weight_input);
		result_text = (TextView) findViewById(R.id.main_result_text);
		comment_text = (TextView) findViewById(R.id.main_comment_text);
		spinner_sex = (Spinner) findViewById(R.id.main_sex_spinner);
		
		// ========================================

		ArrayAdapter<CharSequence> adapter_sex_list = ArrayAdapter.createFromResource(this, R.array.spinner_sex_list, android.R.layout.simple_spinner_item);
		
		adapter_sex_list.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		spinner_sex.setAdapter(adapter_sex_list);
		
		spinner_sex.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					break;
				case 1:
					gender = false;
					break;
				}				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}		
		});

		// ========================================

		button_calculate_bmi.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				if (!height_input.getText().toString().equals("") && !weight_input.getText().toString().equals(""))
				{	
					try {
						height = Double.parseDouble(height_input.getText().toString()) / 100;
						weight = Double.parseDouble(weight_input.getText().toString());
					} catch (NumberFormatException e) {
						// TODO: handle exception
						height = 0.0;
						weight = 0.0;
					}
					
					bmi_result = weight / (height * height);

					result_text.post(new Runnable() {
						public void run() {
							result_text.setText("Your BMI is " + nf.format(bmi_result));
							comment_text.setText(generateBMIComment(bmi_result));
						}
					});							
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public String generateBMIComment(double bmi_result2) {
		String result = ""; 
//		BMI 18.5~23.9為正常範圍 
//		BMI24~ 26.9 為過重 
//		27~ 30輕度肥胖 
//		30 ~ 35中度肥胖
//		大於35重度肥胖 
		
		if (bmi_result2 < 18.5) {
			result = "非人也";
		}
		else if (bmi_result2 <= 23.9) {
			result = "恭喜，正常範圍！";
		}
		else if (bmi_result2 <= 26.9) {
			result = "過重...";
		}
		else if (bmi_result2 <= 30) {
			result = "輕度肥胖";
		}
		else if (bmi_result2 <= 35) {
			result = "中度肥胖";
		}
		else {
			result = "重度肥胖！！危險啦";
		}
		
		return result;
	}
}
