package trabajo.findthechest;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Brujula extends Activity implements SensorEventListener {

    //Definimos la imagen que vamos a usar
    private ImageView image;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    //Declaramos el SensorManager
    private SensorManager mSensorManager;

    //Declaramos el TextView
    TextView lblValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brujula);

        //Inicializamos la imagen
        image = (ImageView) findViewById(R.id.Brujula);

        //Inicializamos el gestor de sensores
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Registra el listener
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Apaga el listener y ahorra batería
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //Cogemos el valor del angulo que tiene el ejeZ
        float degree = Math.round(event.values[0]);

        // Creamos una rotación
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // Cuanto dura la animacion
        ra.setDuration(210);

        //Cuando acabe no se acabará, sino que persistirá
        ra.setFillAfter(true);

        //Iniciamos la animacion
        image.startAnimation(ra);
        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //No se usa
    }
}