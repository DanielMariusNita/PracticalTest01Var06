package ro.pub.cs.systems.eim.practicaltest01var06

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var06MainActivity : AppCompatActivity() {
    private var scor = 0 // Variabilă pentru scorul cumulativ
    private var scorAnterior = 0 // Variabilă pentru scorul anterior (ne-modificat)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var06_main)
        // Restaurăm scorul dacă activitatea este recreată
        if (savedInstanceState != null) {
            scor = savedInstanceState.getInt("scor")
            scorAnterior = savedInstanceState.getInt("scorAnterior")
        }
        // Referințe pentru câmpurile de introducere a numerelor și checkbox-uri
        val numar1: EditText = findViewById(R.id.numar1)
        val numar2: EditText = findViewById(R.id.numar2)
        val numar3: EditText = findViewById(R.id.numar3)
        val checkbox1: CheckBox = findViewById(R.id.checkbox1)
        val checkbox2: CheckBox = findViewById(R.id.checkbox2)
        val checkbox3: CheckBox = findViewById(R.id.checkbox3)

        // Referință la butonul "Play"
        val buttonPlay: Button = findViewById(R.id.buttonPlay)
        val buttonCompute: Button = findViewById(R.id.buttonCompute)

        // Ascultător pentru butonul "Play"
        buttonPlay.setOnClickListener {
            // Generare numere aleatoare (1, 2, 3 sau "*")
            val valoriPosibile = listOf("1", "2", "3", "*")
            val numarAleator1 = valoriPosibile.random()
            val numarAleator2 = valoriPosibile.random()
            val numarAleator3 = valoriPosibile.random()

            // Suprascrie EditText-urile doar dacă checkbox-urile nu sunt bifate
            if (!checkbox1.isChecked) {
                numar1.setText(numarAleator1)
            }
            if (!checkbox2.isChecked) {
                numar2.setText(numarAleator2)
            }
            if (!checkbox3.isChecked) {
                numar3.setText(numarAleator3)
            }

            // Afișează în Logcat valorile generate
            Log.d("MainActivity", "Numere pregătite: $numarAleator1, $numarAleator2, $numarAleator3")
            // Actualizăm scorul anterior
            scorAnterior = scor
        }
        // Ascultător pentru butonul "Compute" - lansează activitatea secundară
        buttonCompute.setOnClickListener {
            Log.d("MainActivity", "Scor înainte de Compute: $scor")
            if (scor == scorAnterior) {
                // Dacă scorul nu s-a schimbat, afișăm scorul direct
                Toast.makeText(this, "Scorul actual este: $scor", Toast.LENGTH_SHORT).show()
            } else {
                // Dacă scorul s-a schimbat, pornim activitatea secundară
                val numarBife = listOf(checkbox1, checkbox2, checkbox3).count { it.isChecked }

                val intent = Intent(this, PracticalTest01Var06SecondaryActivity::class.java)
                intent.putExtra("numar1", numar1.text.toString())
                intent.putExtra("numar2", numar2.text.toString())
                intent.putExtra("numar3", numar3.text.toString())
                intent.putExtra("numarBife", numarBife)

                startActivityForResult(intent, 1) // Pornim activitatea pentru rezultat
            }
        }
    }
    // Metodă care primește rezultatul de la PracticalTest01Var06SecondaryActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val castig = data?.getIntExtra("castig", 0) ?: 0
            Log.d("MainActivity", "Castig primit: $castig")
            scor += castig // Adăugăm la scorul total
            Log.d("MainActivity", "Scor actualizat: $scor")
            Toast.makeText(this, "Scorul actual: $scor", Toast.LENGTH_SHORT).show()
        }
    }
    // Salvăm scorul în caz de distrugere a activității
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("scor", scor)
        outState.putInt("scorAnterior", scorAnterior) // Salvăm și scorul anterior
    }

    // Restaurăm scorul atunci când activitatea este recreată
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        scor = savedInstanceState.getInt("scor")
        scorAnterior = savedInstanceState.getInt("scorAnterior")
    }
}