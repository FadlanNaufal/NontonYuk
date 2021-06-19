package com.fadlandev.nontonyuk.sign.signup

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadlandev.nontonyuk.home.HomeActivity
import com.fadlandev.nontonyuk.R
import com.fadlandev.nontonyuk.utils.Preferences
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_signup_photo.*
import java.util.*


class SignupPhotoActivity : AppCompatActivity() , PermissionListener{

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    lateinit var filePath: Uri

    lateinit var storage : FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_photo)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance("gs://nontonyuk-93ba9.appspot.com/")

        tv_hello.text = "Selamat Datang \n" + intent.getStringExtra("nama")

        iv_add_photo.setOnClickListener{
            if(statusAdd){
                statusAdd = false
                btn_signup_two.visibility = View.VISIBLE
                iv_photo_profile.setImageResource(R.drawable.user_pic)
            }else{
                Dexter.withActivity(this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()
            }
        }

        btn_upload_later.setOnClickListener {
            finishAffinity()
            var intent = Intent(this@SignupPhotoActivity,
                HomeActivity::class.java)
            startActivity(intent)
        }

        btn_signup_two.setOnClickListener {
            if(filePath != null){
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                var ref = storageReference.child("image/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this,"Uploaded",Toast.LENGTH_SHORT).show()
                        ref.downloadUrl.addOnSuccessListener {
                            preferences.setValues("url", it.toString())
                        }

                        finishAffinity()
                        var intent = Intent(this@SignupPhotoActivity,
                            HomeActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener{
                        progressDialog.dismiss()
                        Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener {
                        taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Uploading" + progress.toInt() + "%")
                    }

            } else {

            }
        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Toast.makeText(this,"Denied",Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        Toast.makeText(this,"Upload Nanti Saja",Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            var bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true
            filePath = data.getData()!!
            Glide.with(this)
                .load(bitmap)
                .apply (RequestOptions.circleCropTransform())
                .into(iv_photo_profile)

            btn_signup_two.visibility = View.VISIBLE
            iv_add_photo.setImageResource(R.drawable.ic_delete_black_24dp)
        }
    }
}
