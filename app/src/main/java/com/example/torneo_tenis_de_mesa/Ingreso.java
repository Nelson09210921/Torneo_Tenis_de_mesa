package com.example.torneo_tenis_de_mesa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.torneo_tenis_de_mesa.Polimorfismo.Persona;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Ingreso extends AppCompatActivity {

    
    //Declaracion de variables
    private EditText mEmail;

    private EditText mContrasena;


    //Variable que se usara para asegurarse que la cuenta es verificada
    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    //Creacion de la activity
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        //@Param Ingresar
        mEmail = (EditText) findViewById(R.id.email_ingres);
        mContrasena = (EditText) findViewById(R.id.contrasena_ingreso);

        mAuth = FirebaseAuth.getInstance();


    }

    /*Se verifica si existe un usuario que haya iniciado sesion antes
    No se implementa por compelto 
    @See reload();
    */
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
        reload();
        }else {
        Toast.makeText(Ingreso.this, "No se ha iniciado sesion", Toast.LENGTH_LONG).show();
        }

    }

    //Al presionar el boton ingresar
    public void Ingresar(View view){
        String email = mEmail.getText().toString();
        String contrasena = mContrasena.getText().toString();


        //Se hace uso del modulo atutenticacion de firebase
        mAuth.signInWithEmailAndPassword(email, contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();

                //Se validan las credeciales
                if(task.isSuccessful()){

                    //Se revisa que el email ya este verificado
                    if(user.isEmailVerified()){
                        Log.d(TAG, "signInWithEmail:success");
                        Toast.makeText(Ingreso.this, "Se inicio sesion", Toast.LENGTH_LONG).show();
                        updateUI(user) ;

                        //Se llama a la base de datos para conocer el tipo de usuario
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference datareference = database.getReference("Persona");

                        //Se hace la consulta en la base de datos tomando el correo como referencia
                        Query query = datareference.orderByChild("correo").equalTo(email);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                //ciclo para obtener el objeto(usuario) solicitado
                                for (DataSnapshot ds : snapshot.getChildren()){

                                    Persona usuario = ds.getValue(Persona.class);

                                    //Se envia a la activity correspondiente de acuerdo al roll del usuario(Administrador / Participante)
                                    //Tipos:
                                    //Administrador: 1            Participante: 2
                                    if(usuario.getTipo_usuario() == 1){
                                        Intent toadmin = new Intent(Ingreso.this, Administrador.class);
                                        startActivity(toadmin);
                                        Toast.makeText(Ingreso.this, "Usuario administrador", Toast.LENGTH_LONG).show();
                                    }
                                    if(usuario.getTipo_usuario() == 2){
                                        Intent toPart = new Intent(Ingreso.this, Participante.class);
                                        startActivity(toPart);
                                        Toast.makeText(Ingreso.this, "Usuario participante", Toast.LENGTH_LONG).show();
                                    }
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }else {
                        //**********************IMPORTANTE************************
                        // Se permiten ingresar usuarios no verificados con el fin de
                        // hacer pruebas en la etapa de desarrollo
                        //En el momento del lanzamienzo, se tienen que eliminar estas lineas de codigo
                        // Para asi dejar ingresar solo a usuarios verificados

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference datareference = database.getReference("Persona");

                        //Se hace la consulta en la base de datos
                        Query query = datareference.orderByChild("correo").equalTo(email);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                //ciclo para obtener el objeto(Usuario) solicitado

                                for (DataSnapshot ds : snapshot.getChildren()){

                                    Persona usuario = ds.getValue(Persona.class);
                                    if(usuario.getTipo_usuario() == 1){
                                        Intent toadmin = new Intent(Ingreso.this, Administrador.class);
                                        startActivity(toadmin);
                                        Toast.makeText(Ingreso.this, "Usuario administrador", Toast.LENGTH_LONG).show();
                                    }
                                    if(usuario.getTipo_usuario() == 2){
                                        Intent toPart = new Intent(Ingreso.this, Participante.class);
                                        startActivity(toPart);
                                        Toast.makeText(Ingreso.this, "Usuario participante", Toast.LENGTH_LONG).show();
                                    }
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        //**************Finaliza ***************
                        //Toast.makeText(Ingreso.this, "El email no se encuentra verificado", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(Ingreso.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
    }

    private void reload() {
    }
}
