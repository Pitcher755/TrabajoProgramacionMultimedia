package trabajoEnfoque.calculadora;

import static trabajoEnfoque.calculadora.R.*;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Botones
    private Button btnCalcular;
    private Button btnBorrar;
    private Button btnGuardar;
    private Button btnMostrar;

    // RadioButtonGroup con las operaciones
    private RadioGroup rbgOperaciones;
    private RadioButton rbSumar;
    private RadioButton rbRestar;
    private RadioButton rbMultiplicar;
    private RadioButton rbDividir;

    // Resultado
    private TextView tvResultado;

    // Numeros de entrada
    private EditText etNum1;
    private EditText etNum2;

    // Almacenamiento para guardar resultados
    private SharedPreferences resultadoGuardado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar los componentes
        btnCalcular = findViewById(R.id.btnCalcular);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnMostrar = findViewById(R.id.btnMostrar);

        rbgOperaciones = findViewById(R.id.rbgOperaciones);
        rbSumar = findViewById(R.id.rbSumar);
        rbRestar =findViewById(R.id.rbRestar);
        rbMultiplicar = findViewById(R.id.rbMultiplicar);
        rbDividir = findViewById(R.id.rbDividir);

        tvResultado = findViewById(R.id.tvResultado);
        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);

        resultadoGuardado = getSharedPreferences("CalcularPrefs", MODE_PRIVATE);

        // Configuración de los listeners
        btnCalcular.setOnClickListener(v -> calcularOperacion());
        btnBorrar.setOnClickListener(v -> borrar());
        btnGuardar.setOnClickListener(v -> guardarResultado());
        btnMostrar.setOnClickListener(v -> mostrarGuardado());
    }

    private void calcularOperacion(){
        if (etNum1.getText().toString().isEmpty() || etNum2.getText().toString().isEmpty()){
            Toast.makeText(this, "Por favor, introduce los dos números", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            double num1 = Double.parseDouble(etNum1.getText().toString());
            double num2 = Double.parseDouble(etNum2.getText().toString());
            double resultado = 0;

            // Conseguir el id del RadioButton seleccionado
            int operacionSeleccionada = rbgOperaciones.getCheckedRadioButtonId();

            if (operacionSeleccionada == -1) {
                Toast.makeText(this, "Por favor, selecciona un operación", Toast.LENGTH_SHORT).show();
                return;
            }
           if (operacionSeleccionada == R.id.rbSumar){
               resultado = suma(num1, num2);
           } else if (operacionSeleccionada == id.rbRestar){
               resultado = resta(num1, num2);
           } else if (operacionSeleccionada == id.rbMultiplicar){
               resultado = multiplicacion(num1, num2);
           } else if (operacionSeleccionada == id.rbDividir){
               resultado = division(num1, num2);
           }
            // Mostrar el resultado
            tvResultado.setText(String.valueOf(resultado));
            etNum1.setText("");
            etNum2.setText("");
        } catch (NumberFormatException e){
            Toast.makeText(this, "Introduce números válidos", Toast.LENGTH_SHORT).show();
        } catch (ArithmeticException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Método que suma los números introducidos por el usuario.
     * @param num1 Primer número introducido.
     * @param num2 Segundo número introducido.
     * @return La suma de los dos números.
     */
    public double suma(double num1, double num2){
        return num1 + num2;
    }

    /**
     * Método que resta los números introducidos por el usuario.
     * @param num1 Primer número introducido.
     * @param num2 Segundo número introducido.
     * @return La resta de los dos números.
     */
    public double resta(double num1, double num2){
        return num1 - num2;
    }

    /**
     * Método que multiplica los números introducidos por el usuario.
     * @param num1 Primer número introducido.
     * @param num2 Segundo número introducido.
     * @return La multiplicación de los dos números.
     */
    public double multiplicacion(double num1, double num2){
        return num1 * num2;
    }

    /**
     * Método que divide los números introducidos por el usuario.
     * @param num1 Primer número introducido.
     * @param num2 Segundo número introducido.
     * @return La división de los dos números.
     */
    public double division(double num1, double num2){
        if (num2 == 0) throw new ArithmeticException("No se puede dividir entre 0");
        return num1 / num2;
    }

    /**
     * Método que borra los campos
     */
    public void borrar(){
        etNum1.setText("");
        etNum2.setText("");
        tvResultado.setText("0");
    }

    /**
     * Método para guardar el último resultado
     */
    public void guardarResultado(){
        String resultado = tvResultado.getText().toString();
        if (!resultado.isEmpty()){
            resultadoGuardado.edit().putString("ultimoResultado", resultado).apply();
            Toast.makeText(this, "Resultado guardado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No hay resultado para guardar", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método que muestra el último resultado guardado
     */
    public void mostrarGuardado(){
       String ultimoResultado = resultadoGuardado.getString("ultimoResultado", "No hay resultados guardados");
       tvResultado.setText(ultimoResultado);
       Toast.makeText(this, "Último resultado: " + ultimoResultado, Toast.LENGTH_SHORT).show();
    }

}