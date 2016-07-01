package unicauca.movil.testpermisos;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_WRITE_STORAGE = 101;

    TextView txt;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txt);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void checkPermission(Activity activity, String permission ){
        if(ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED){
            //ENTRA AL IF SI NO SE HA HABILITADO EL PERMISO O SI ESTA NEGADO
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
                //ENTRA AQUI CUANDO EL PERMISO HA SIDO NEGADO PREVIAMENTE
                //Aqui se deshabilitaria la funcionalidad cuando se inicia la app
                txt.setText("Permiso negado previamente");
            }else{
                //ENTRA AQUI SI ES LA PRIMERA VEZ Q SE SOLICITA
                //se esta solicitando el permiso, esto se puede llamar en cualquier lugar
                //La respuesta a la solicutd llega en el metodo onRequestPermissionsResult
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        REQUEST_WRITE_STORAGE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_WRITE_STORAGE){
            //grantResults tiene el resultado si se acepto o se cancelo el permiso
            //Es un arreglo xq se pueden solicitar mas de un permiso al tiempo
            //si es tamaño es 0 es xq no se acepto
           if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               //Si se habilito
               txt.setText("Permiso habiltado !!");
           }else{
               //No se habilito el permiso
               //Aqui se deshabilitaria la funcionalidad
               txt.setText("Permiso no habilitado !!");
           }

        }
    }

    @Override
    public void onClick(View view) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);
    }
}
