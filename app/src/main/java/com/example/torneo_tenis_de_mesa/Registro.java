package com.example.torneo_tenis_de_mesa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.torneo_tenis_de_mesa.Polimorfismo.Persona;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Registro extends AppCompatActivity {

    //@Param Registrar();

    //Se declaran variables para firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private FirebaseAuth mAuth;

    //Se declaran las variables necesarias para la Activity de Registro
    private EditText mEditTextNombreCompleto;
    private EditText mEditTextNombreDeUsuario;
    private EditText mEditTextCorreo;
    private EditText mEditTextContrasena;
    private EditText mEdittextVerContrasena;
    private ImageView[] mimageAvatar = new ImageView[10];
    private ImageView mAvatarSeleccionado;

    //Para evaluar calidad de los datos
    private boolean isok= false;

    //Para validar que fue seleccionado un avatar
    boolean isselect = false;

    private static final String TAG = "EmailPassword";





    //Creación de la activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Inicializando Firebase
        inicializarFirebase();
        mAuth = FirebaseAuth.getInstance();


        //************Se hace la identificacion y localizacion de las vistas y textos****************

        //Vector de avatars
        mimageAvatar[0] = (ImageView) findViewById(R.id.a0);
        mimageAvatar[1] = (ImageView) findViewById(R.id.a1);
        mimageAvatar[2] = (ImageView) findViewById(R.id.a2);
        mimageAvatar[3] = (ImageView) findViewById(R.id.a3);
        mimageAvatar[4] = (ImageView) findViewById(R.id.a4);
        mimageAvatar[5] = (ImageView) findViewById(R.id.a5);
        mimageAvatar[6] = (ImageView) findViewById(R.id.a6);
        mimageAvatar[7] = (ImageView) findViewById(R.id.a7);
        mimageAvatar[8] = (ImageView) findViewById(R.id.a8);
        mimageAvatar[9] = (ImageView) findViewById(R.id.a9);

        //Entradas de texto
        mEditTextNombreCompleto = (EditText) findViewById(R.id.nombre_registro);
        mEditTextNombreDeUsuario = (EditText) findViewById(R.id.usuario_registro);
        mEditTextCorreo = (EditText) findViewById(R.id.correo_electronico);
        mEditTextContrasena = (EditText) findViewById(R.id.contrasena1_registro);
        mEdittextVerContrasena = (EditText) findViewById(R.id.contrasena2_registro);



        //Ciclo para recorrer los avatars y ubicar el que fue seleccionado
        for (int i=0;i<=9;i++){

            //finalI guarda la posicion donde se encuentra el avatar seleccionado
            int finalI = i;
            mimageAvatar[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!isselect){

                        //Al seleccionar el avatar se guarda la variable para el envio a firebase
                        mAvatarSeleccionado = (ImageView) mimageAvatar[finalI];

                        mimageAvatar[finalI].setImageDrawable(getResources().getDrawable(R.drawable.selected));
                        isselect = true;

                    }else {
                        Toast.makeText(Registro.this, "No puedes elegir mas de un avatar", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }//Fin del ciclo


    }


    //Al darle al boton de registar se ejecuta el metodo
    public void Registrar(View v){
        //Cambio de variables a string
        String nombre_completo = mEditTextNombreCompleto.getText().toString();
        String nombre_ususario = mEditTextNombreDeUsuario.getText().toString();
        String correo = mEditTextCorreo.getText().toString();
        String contrasena = mEditTextContrasena.getText().toString();

        //Si todas las validadiones son correctas entra al condicional
        //@Throws A message to complete minimun requeriments for sing up
        
        isok = validaciones_de_entrada(isselect);
        

        if (isok) {

            //Crea usuario en la seccion de autenticacion de firebase
            mAuth.createUserWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //Si la creacion de usuario es exitosa
                            if (task.isSuccessful()){

                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                //Se envia el email para verificar
                                sendEmailVerification();

                                //Redireccionamiento a ingreso para iniciar sesion
                                Intent toingreso = new Intent(Registro.this, Ingreso.class);
                                startActivity(toingreso);
                            } else {

                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Registro.this, "Falla de autenticacion", Toast.LENGTH_LONG).show();
                                updateUI(null);
                            }
                        }
                    });


            // se crea un objeto persona y se envia a la base de datos
            Persona p = new Persona();
            Toast.makeText(Registro.this, "Se creo persona", Toast.LENGTH_LONG).show();
            p.setId(UUID.randomUUID().toString());
            p.setNombre(nombre_completo);
            p.setNombre_usuario(nombre_ususario);
            p.setCorreo(correo);
            p.setContrasena(contrasena);
            p.setTipo_usuario(2);


            databaseReference.child("Persona").child(p.getId()).setValue(p);
            Toast.makeText(Registro.this, "Se guardo satisfactoriamente el usuario", Toast.LENGTH_LONG).show();

        }

    }

    
    private void sendEmailVerification(){


        final  FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Registro.this, "Email enviado", Toast.LENGTH_LONG ).show();
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Registro.this);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    
    public boolean validaciones_de_entrada(boolean isselect) {
        //Declaracion de variables
        String nombre_completo = mEditTextNombreCompleto.getText().toString();
        String nombre_ususario = mEditTextNombreDeUsuario.getText().toString();
        String correo = mEditTextCorreo.getText().toString();
        String contrasena = mEditTextContrasena.getText().toString();
        String ver_contrasena = mEdittextVerContrasena.getText().toString();


        //Se verifica que los campos no se encuentren vacios
        if (nombre_completo.equals("") || nombre_ususario.equals("")
                || contrasena.equals("") || correo.equals("") || ver_contrasena.equals("") || !isselect){
            Toast.makeText(Registro.this, "Completa todos lo campos para realizar el registro", Toast.LENGTH_LONG).show();
            return false;

        }
        //Si las contrasenas no coinciden
        else if(!contrasena.equals(ver_contrasena)){
            Toast.makeText(Registro.this, "Las contraseñas no coiciden", Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }


    }


}
