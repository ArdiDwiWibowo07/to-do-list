package com.ardidwiwibowo.todolist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ardidwiwibowo.todolist.databinding.ActivityMainBinding
import java.io.*

lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Membuat variabel untuk menampung binding componet yang bertipe EditText
        val fileName = binding.txtNamaFile
        val waktuText = binding.txtWaktu
        val aktivitasText = binding.txtAktivitas
        val fileData = binding.txtHasil

        //Membuat variabel untuk menampung binding componet yang bertipe Button
        val btnSave = binding.btnSave
        val btnView = binding.btnView
        val btnDelete= binding.btnDelete

        //Deklarasi nilai awal untuk variabel hasil
        var hasil = "";

        //ketika btnSave diklik
        btnSave.setOnClickListener(View.OnClickListener {
            //Membuat nama file yang bertetipe string dan ketambahan txt
            val file:String = fileName.text.toString() +".txt"

            //Mengubah kedalam bentuk string
            val waktu:String = waktuText.text.toString()
            val aktivitas:String = aktivitasText.text.toString()

            //Nilai variabel hasil diperoleh dari gabungan variabel hasil +waktu+"->"+aktivitas+" , "
            hasil = hasil +waktu+"->"+aktivitas+" , "


            val fileOutputStream:FileOutputStream
            //Mencoba
            try {
                //Membuat file dengan nama yang difenisikann oleh variabel file
                fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                //Menuliskan ke file dengan tulisan dari nilai hasil
                fileOutputStream.write(hasil.toByteArray())
            //Penangkapan jika file tidak ditemukan
            //Menangkap error dan diabaikan
            }catch (e: Exception){
                e.printStackTrace()
            }
            //Muncul popup dengan text "Data Tersimpan"
            Toast.makeText(applicationContext,"Data Tersimpan",Toast.LENGTH_LONG).show()

            //Bersihkan text
            fileData.text.clear()
            waktuText.text.clear()
            aktivitasText.text.clear()
        })

        btnView.setOnClickListener(View.OnClickListener {
            //Mencoba
            try {
                //Membuat filename yang bertetipe string dan ketambahan txt
                val filename = fileName.text.toString()+".txt"
                //cek apakah filename tidak kosong
                if (filename.toString().trim() != "") {
                    //Pembuatan pembacaan file
                    var fileInputStream: FileInputStream? = null
                    fileInputStream = openFileInput(filename)
                    var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                    val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                    val stringBuilder: StringBuilder = StringBuilder()
                    var text: String? = null
                    //Membaca isi file yang mana di var text
                    while ({ text = bufferedReader.readLine(); text }() != null) {
                        //Menmabahan var text ke stringBuilder
                        stringBuilder.append(text)
                        //Set nilai hasil dari text
                        hasil= text.toString()
                    }
                    //Tampilkan data pada fileData/binding.txtHasil
                    fileData.setText(stringBuilder.toString()).toString()
                } else {
                    //Jika if tidak memenuhi, muncul pop up "file name cannot be blank"
                    Toast.makeText(
                        applicationContext,
                        "file name cannot be blank",
                        Toast.LENGTH_LONG
                    ).show()
                }
                //Menangkap error dan diabaikan
            }catch (e: Exception) {
                //muncul pop up "File tidak ada"
                Toast.makeText(this, "File tidak ada", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }

        })

        //ketika btnDekete diklik
        btnDelete.setOnClickListener(View.OnClickListener{
            //memncoba
            try {
                val filename = fileName.text.toString()+".txt"
                //Mencari direktori file
                val dir = filesDir
                //Menemukan file
                val file = File(dir, filename)
                //file dihapus
                file.delete()
                //set ulang hasil
                hasil = ""
                //Menangkap error dan diabaikan
            }catch (e: Exception) {
                //muncul pop up "Tidak bisa dihaspus"
                Toast.makeText(this, "Tidak bisa dihaspus", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }

            //bersihkan text
            fileName.text.clear()
            fileData.text.clear()
            waktuText.text.clear()
            aktivitasText.text.clear()
        })

    }
}