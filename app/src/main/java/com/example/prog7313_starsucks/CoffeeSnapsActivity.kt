package com.example.prog7313_starsucks

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.prog7313_starsucks.databinding.ActivityCoffeeSnapsBinding
import com.google.android.material.navigation.NavigationView
// import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CoffeeSnapsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityCoffeeSnapsBinding
    //  A ListenableFuture that will eventually provide a ProcessCameraProvider instance, which is
    //  used to control the camera's lifecycle and access its features
   // private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    // An object that selects which camera to use (front or back)
    private lateinit var cameraSelector: CameraSelector
    // An ImageCapture object for capturing still images from the camera
    private var imageCapture: ImageCapture? = null
    // An ExecutorService that handles the execution of tasks related to image capture (saving images
    // to a file
    private lateinit var  imgCaptureExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoffeeSnapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.navToolbar2)

        // Creates an ExecutorService that uses a single worker thread to execute tasks.
        // In this case, it is used for sequential execution of tasks in a background thread, when
        // capturing images from the camera.
        // When submitting tasks to this executor, they are executed one after another in the order
        // they are submitted.
        imgCaptureExecutor = Executors.newSingleThreadExecutor()

        // Toggle on and off the navigation view
        var toggleOnOff = ActionBarDrawerToggle(this,
            binding.drawerLayout2, binding.navToolbar2,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        binding.drawerLayout2.addDrawerListener(toggleOnOff)
        toggleOnOff.syncState()

        binding.navView2.bringToFront()
        binding.navView2.setNavigationItemSelectedListener(this)

        // registerForActivityResult is used to register a callback for the result of a permission
        // request. RequestPermission() handles the request for a single permission. When the permission
        // request is received, the permissionGranted parameter will be 'true' if the permission was
        // granted, and 'false' otherwise.
        val cameraProviderResult = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) {permissionGranted->
            if (permissionGranted) {
                //initialise the camera
                startCamera()
            } else {
                Toast.makeText(this, "Cannot take a photo without camera permissions", Toast.LENGTH_SHORT).show()
            }
        }

        // start the camera
        // cameraProviderResult is the result launcher created with registerForActivityResult,
        // to request the 'CAMERA' permission. The launch() method is called with the permission string
        // as an argument, which will trigger the permission request flow.
        cameraProviderResult.launch(android.Manifest.permission.CAMERA)

        // Create an OutputFileOptions object to specify the file where the
        // captured image should be saved. The takePicture() method is then called to capture an image
        // using the 'imageCapture' instance. It takes the 'OutputFileOptions' executor (imgCaptureExecutor)
        // and an 'onImageSavedCallback' object.
        // In the 'onImageSavedCallback' implementation, the onImageSaved method is called when the
        // image capture is successful. It logs the saved image's URI and sets the savedUri as the
        // image source (setImageURI) for an 'ImageView' (imgSavedPhoto) on the UI thread (runOnUiThread)
        binding.photoFab.setOnClickListener() {
            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
                File(externalMediaDirs[0], "Coffee_${System.currentTimeMillis()}")).build()
            imageCapture?.takePicture(outputFileOptions, imgCaptureExecutor,
                object: ImageCapture.OnImageSavedCallback
                {
                    override fun onError(exception: ImageCaptureException)
                    {
                       //Toast.makeText(this, "Image could not be saved", Toast.LENGTH_SHORT).show()
                    }
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults)
                    {
                        Log.d("CoffeeSnapsActivity",
                            "Photo saved to ${outputFileResults.savedUri}")

                        this@CoffeeSnapsActivity.runOnUiThread{
                            binding.imgSavedPhoto.setImageURI(outputFileResults.savedUri)
                        }
                    }
                })
        }
    }

    // Method to start the camera and capture image
    private fun startCamera() {
//        // cameraProviderFuture = ProcessCameraProvider.getInstance(this):
//        // Gets a ProcessCameraProvider (class that manages the camera
//        // subsystem) instance using the getInstance method,
//        // passing the current context (this). This is an asynchronous operation
//        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//
//        //  Sets the cameraSelector to select the default back camera
//        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//        // Adds a listener to the cameraProviderFuture to be notified when the ProcessCameraProvider
//        // instance is ready. When the listener is triggered, it gets the cameraProvider from the
//        // future and sets up the camera preview and image capture.
//        cameraProviderFuture.addListener({
//
//            // .get() Waits if necessary for the computation to complete, and then retrieves its result
//            val cameraProvider = cameraProviderFuture.get()
//
//            // Creates a Preview use case for displaying the camera preview. It sets the surface
//            // provider to the SurfaceProvider of the imgCameraImage view from the binding
//            val preview = Preview.Builder().build().also{
//                it.setSurfaceProvider(binding.imgCameraImage.surfaceProvider)
//            }
//
//            // Creates an ImageCapture use case for capturing images
//            imageCapture = ImageCapture.Builder().build()
//            try {
//                //  Unbinds all use cases from the camera to ensure a clean start
//                cameraProvider.unbindAll()
//
//                // Binds the preview and imageCapture use cases to the camera lifecycle, using the
//                // cameraSelector. This ensures that the camera preview is displayed and
//                // image capture is possible
//                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
//            }
//            catch (e: Exception) {
//                // If any exceptions occur during use case binding, it logs a message indicating the
//                // failure (Use case binding failed)
//                Log.d("CoffeeSnapsActivity", "Use case binding failed")
//            }
//        }, //returns an Executor that runs tasks on the main thread
//            ContextCompat.getMainExecutor(this))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Navigate to the photo activity
        when (item.itemId) {
            R.id.nav_main -> openIntent(this, "", MainActivity::class.java)
            // R.id.nav_photo -> openIntent(this, "", )
            // R.id.nav_history -> openIntent(this, "", )
        }
        binding.drawerLayout2.closeDrawer(GravityCompat.START)

        return true
    }
}

