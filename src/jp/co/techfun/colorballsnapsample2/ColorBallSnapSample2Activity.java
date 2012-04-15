package jp.co.techfun.colorballsnapsample2;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class ColorBallSnapSample2Activity extends Activity {

	/** class fields
	 * 
	 */
	// SensorManager
	private SensorManager sensorManager;
	
	// DrawableView
	private DrawableView2 drawableView;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // instantiate: DrawableView
        drawableView = new DrawableView2(this);
        
        // setup: keep the backlight on
        drawableView.setKeepScreenOn(true);
        
        // designate the instance for display
        setContentView(drawableView);
        
        // instantiate the SensorManager
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        
//        setContentView(R.layout.main);
    }//public void onCreate(Bundle savedInstanceState)
    
    @Override
    protected void onResume() {
		super.onResume();
		
		// get an accelerometer sensor object
		List<Sensor> accelerometerSensors = 
						sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		// register listeners to the sensors
		if (accelerometerSensors.size() > 0) {			
			// set a listener
			sensorManager.registerListener(sensorEventListener, 
							accelerometerSensors.get(0), SensorManager.SENSOR_DELAY_GAME);
		}//if (accelerometerSensors.size() > 0)
		
	}//onResume()
    
    // onStop()
    @Override
    protected void onStop() {
    	super.onStop();
    	sensorManager.unregisterListener(sensorEventListener);
    }//protected void onStop()
    
    private final SensorEventListener sensorEventListener = 
    				new SensorEventListener() {
    	
    	// low-pass filter
    	private float lowX;
    	private float lowY;
    	private float lowZ;
    	
    	// filter range
    	private static final float FILTERING_VALUE = 0.2f;

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO 自動生成されたメソッド・スタブ
			// no process
		}//public void onAccuracyChanged(Sensor sensor, int accuracy)

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO 自動生成されたメソッド・スタブ
			// get the value the sensor catched
			float x = event.values[SensorManager.DATA_X];
			float y = event.values[SensorManager.DATA_Y];
			float z = event.values[SensorManager.DATA_Z];
			
			// process filtering
			lowX = getLowPassFilterValue(x, lowX);
			lowY = getLowPassFilterValue(x, lowY);
			lowZ = getLowPassFilterValue(x, lowZ);
			
			// switching the event
			switch (event.sensor.getType()) {
				// if the sensor is of accelerometer
				case Sensor.TYPE_ACCELEROMETER:
					// reset the location data
					drawableView.effectAccelaration(lowX, lowY, lowZ);
					
					// redraw the screen
					drawableView.invalidate();
					break;
					
				// other than accelerometer
				default:
					break;
					
			}//switch (event.sensor.getType())
			
		}//public void onSensorChanged(SensorEvent event)

		private float getLowPassFilterValue(float eventValue, float lowValue) {
			// TODO 自動生成されたメソッド・スタブ
			return eventValue * FILTERING_VALUE + 
							lowValue * (1.0f - FILTERING_VALUE);
		}
    	
    };
    
}//public class ColorBallSnapSample2Activity extends Activity
