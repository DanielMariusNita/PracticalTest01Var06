package ro.pub.cs.systems.eim.practicaltest01var06

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var06SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.practical_test01_var06_secondary_activity)
        // Obținem datele transmise prin intenție
        val numar1 = intent.getStringExtra("numar1") ?: ""
        val numar2 = intent.getStringExtra("numar2") ?: ""
        val numar3 = intent.getStringExtra("numar3") ?: ""
        val numarBife = intent.getIntExtra("numarBife", 0)

        // TextView pentru afișarea rezultatului
        val rezultatTextView: TextView = findViewById(R.id.rezultatTextView)

        // Determinarea câștigului
        val mesajCastig: String
        val castig: Int

        // Verificăm dacă cele 3 numere sunt identice (cu joker-ul '*' permis)
        val allMatch = (numar1 == numar2 || numar1 == "*" || numar2 == "*") &&
                (numar2 == numar3 || numar2 == "*" || numar3 == "*") &&
                (numar1 == numar3 || numar1 == "*" || numar3 == "*")

        if (allMatch) {
            mesajCastig = "Gained"
            // Calculăm câștigul pe baza numărului de checkbox-uri bifate
            castig = when (numarBife) {
                0 -> 100
                1 -> 50
                2 -> 10
                else -> 0
            }
        } else {
            mesajCastig = "Not Gained"
            castig = 0
        }

        // Afișăm rezultatul în activitate
        rezultatTextView.text = "$mesajCastig - Câștig: $castig"
        // Pregătim intenția pentru a întoarce rezultatul
        val returnIntent = Intent()
        returnIntent.putExtra("castig", castig)
        setResult(RESULT_OK, returnIntent)
        finish() // Închidem activitatea și trimitem rezultatul
    }
}