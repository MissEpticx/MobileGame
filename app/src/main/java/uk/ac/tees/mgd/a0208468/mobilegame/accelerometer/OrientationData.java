package uk.ac.tees.mgd.a0208468.mobilegame.accelerometer;

//import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.CURRENT_CONTEXT;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import uk.ac.tees.mgd.a0208468.mobilegame.ui.PlayingUI;

//public class OrientationData implements SensorEventListener {
//    private SensorManager manager;
//    private Sensor accelerometer;
//    private Sensor magnometer;
//    private float [] accelOutput, magOutput, orientation = new float[3], startOrientation;
//
//    public OrientationData(){
//        manager = (SensorManager) CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
//        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//    }
//
//    public float[] getOrientation(){
//        return orientation;
//    }
//
//    public float[] getStartOrientation(){
//        return startOrientation;
//    }
//
//    public void newGame(){
//        startOrientation = null;
//    }
//
//    public void registerGame(){
//        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
//        manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
//    }
//
//    public void pauseGame(){
//        manager.unregisterListener(this);
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
//            accelOutput = event.values;
//        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
//            magOutput = event.values;
//        }
//
//        if(accelOutput != null && magOutput != null){
//            float[] rotationMatrix = new float[9];
//            float[] inclineMatrix = new float[9];
//
//            boolean isSuccessful = SensorManager.getRotationMatrix(rotationMatrix, inclineMatrix, accelOutput, magOutput);
//            if(isSuccessful){
//                SensorManager.getOrientation(rotationMatrix, orientation);
//                if(startOrientation == null){
//                    startOrientation = new float[orientation.length];
//                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.length);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int i) {
//
//    }
//}
