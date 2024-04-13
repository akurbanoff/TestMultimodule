package ru.axas.core.base.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.axas.core.base.extension.LocaleRu
import ru.axas.core.base.extension.makeUri
import ru.axas.core.base.extension.outputFile
import java.util.Locale


class PermissionsModule(val context: Context) {
    private fun checkPermission(namePermission: String): Int {
        val inPermission = ContextCompat.checkSelfPermission(context, namePermission)
        logIRRealise("permission checkPermission", namePermission, inPermission == GRANTED)
        return inPermission
    }

    /**
     * Get Permissions
     * */
    private val permissionCamera by lazy { checkPermission(CAMERA) }
    private val permissionAccessCoarseLocation by lazy { checkPermission(ACCESS_COARSE_LOCATION) }
    private val permissionAccessFineLocation by lazy { checkPermission(ACCESS_FINE_LOCATION) }
    private val permissionReadExternalStorage by lazy { checkPermission(READ_EXTERNAL_STORAGE) }
    private val permissionWriteExternalStorage by lazy { checkPermission(WRITE_EXTERNAL_STORAGE) }
    private val permissionReadMediaImages by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            checkPermission(READ_MEDIA_IMAGES)
        else GRANTED
    }
    private val permissionScheduleExactAlarms by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            checkPermission(SCHEDULE_EXACT_ALARM)
        else GRANTED
    }

    private val permissionReadMediaVideo by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            checkPermission(READ_MEDIA_VIDEO)
        else GRANTED
    }
    private val permissionReadMediaAudio by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            checkPermission(READ_MEDIA_AUDIO)
        else GRANTED
    }

    /**
     * Check Permissions
     * */
    fun grantedCamera(): Boolean = permissionCamera == GRANTED
    fun grantedLocationCoarse(): Boolean = permissionAccessCoarseLocation == GRANTED
    fun grantedLocationFine(): Boolean = permissionAccessFineLocation == GRANTED
    fun grantedScheduleExactAlarms(): Boolean = permissionScheduleExactAlarms == GRANTED
    fun grantedReadStorage(): Boolean = permissionReadExternalStorage == GRANTED
    fun grantedWriteStorage(): Boolean = permissionWriteExternalStorage == GRANTED

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun grantedReadMediaImages(): Boolean = permissionReadMediaImages == GRANTED

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun grantedReadMediaVideo(): Boolean = permissionReadMediaVideo == GRANTED

    fun listPermissionsNeededCameraImagesReadWriteStorage(): Array<String> {
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        listPermissionsNeeded.add(CAMERA)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listPermissionsNeeded.add(READ_MEDIA_IMAGES)
            listPermissionsNeeded.add(READ_MEDIA_VIDEO)
        }
        else {
            listPermissionsNeeded.add(READ_EXTERNAL_STORAGE)
            listPermissionsNeeded.add(WRITE_EXTERNAL_STORAGE)
        }
        return listPermissionsNeeded.toTypedArray()
    }

    fun listPermissionsScheduleExactAlarms(): Array<String> {
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            listPermissionsNeeded.add(SCHEDULE_EXACT_ALARM)
        }
        return listPermissionsNeeded.toTypedArray()
    }

    fun checkCameraImagesReadWriteStorage(): Boolean {
        val listPermissionsNeeded: MutableList<Int> = ArrayList()
        listPermissionsNeeded.add(permissionCamera)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listPermissionsNeeded.add(permissionReadMediaImages)
            listPermissionsNeeded.add(permissionReadMediaVideo)
        }
        else {
            listPermissionsNeeded.add(permissionReadExternalStorage)
            listPermissionsNeeded.add(permissionWriteExternalStorage)
        }
        return listPermissionsNeeded.contains(GRANTED)
    }

    fun listPermissionsNeededLocation(): Array<String> = arrayListOf(
        ACCESS_FINE_LOCATION,
        ACCESS_COARSE_LOCATION
    ).toTypedArray()


    companion object {
        private const val GRANTED = PackageManager.PERMISSION_GRANTED
        private const val CAMERA = Manifest.permission.CAMERA
        private const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        private const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        private const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

        @RequiresApi(Build.VERSION_CODES.S)
        private const val SCHEDULE_EXACT_ALARM = Manifest.permission.SCHEDULE_EXACT_ALARM

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private const val READ_MEDIA_IMAGES = Manifest.permission.READ_MEDIA_IMAGES

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private const val READ_MEDIA_VIDEO = Manifest.permission.READ_MEDIA_VIDEO

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private const val READ_MEDIA_AUDIO = Manifest.permission.READ_MEDIA_AUDIO

        @Composable
        fun SetLocation(address: (List<Address>) -> Unit) {
            val context = LocalContext.current
            PermissionCoarseLocation { permitted ->
                if (permitted) {
                    getLocationServices(context) {
                        CoroutineScope(Dispatchers.Main).launch {
                            address.invoke(it)
                        }
                    }
                }
            }
        }

        @Composable
        fun permissionLauncher(returnResult: (Boolean) -> Unit) =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                returnResult(isGranted)
            }

        @Composable
        fun launchPermissionMultiple(response: (Boolean) -> Unit)
                : ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>> {
            return rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                permissions.entries.forEach {
                    logD("permissionMultiple", "${it.key} = ${it.value}")
                }
                response.invoke(permissions.all { (_, permissionValue) -> permissionValue == true })
            }
        }

        @Composable
        fun launchPermissionCameraAndGallery(response: (Boolean) -> Unit): () -> Unit {
            val context = LocalContext.current
            val permissionsModule = remember { PermissionsModule(context) }
            val launch = launchPermissionMultiple(response)
            return {
                if (permissionsModule.checkCameraImagesReadWriteStorage()) {
                    response.invoke(true)
                }
                else {
                    launch.launch(permissionsModule.listPermissionsNeededCameraImagesReadWriteStorage())
                }
            }

        }

        @Composable
        fun launchPermissionScheduleExactAlarms(response: (Boolean) -> Unit): () -> Unit {
            val context = LocalContext.current
            val permissionsModule = remember { PermissionsModule(context) }
            val launch = launchPermissionMultiple(response)
            return {
                if (permissionsModule.grantedScheduleExactAlarms()) {
                    response.invoke(true)
                }
                else {
                    launch.launch(permissionsModule.listPermissionsScheduleExactAlarms())
                }
            }

        }

        @Composable
        fun PermissionCoarseLocation(returnResult: (Boolean) -> Unit) {
            val context = LocalContext.current
            val permissionsModule = remember { PermissionsModule(context) }
            val permission = launchPermissionMultiple { isGranted -> returnResult(isGranted) }
            LaunchedEffect(key1 = Unit, block = {
                val isPermission =
                    permissionsModule.grantedLocationCoarse() && permissionsModule.grantedLocationFine()
                if (!isPermission) {
                    permission.launch(permissionsModule.listPermissionsNeededLocation())
                }
                else {
                    returnResult(true)
                }
            })
        }

        @Composable
        fun getListImage(
            listImage: (List<Uri>) -> Unit,
        ): () -> Unit {
            val context = LocalContext.current
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/* video/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            val pickMultipleMedia =
                rememberLauncherForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ) { uris ->
                    if (uris.resultCode == Activity.RESULT_OK) {
                        val data = uris.data
                        val selectedImages = mutableListOf<Uri>()
                        if (data?.clipData != null) {
                            for (i in 0 until data.clipData!!.itemCount) {
                                val uri = data.clipData!!.getItemAt(i).uri
                                val realUri = context.getFileRealPatch(uri).toUri()
                                selectedImages.add(realUri)
                            }
                        }
                        else if (data?.data != null) {
                            val uri = data.data!!
                            val realUri = context.getFileRealPatch(uri).toUri()
                            selectedImages.add(realUri)
                        }
                        listImage(selectedImages)
                    }
                }
            return {
                try {
                    pickMultipleMedia.launch(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        @Composable
        fun galleryLauncher(
            uploadPhoto: (Uri) -> Unit
        ): () -> Unit {

            val context = LocalContext.current
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            val launchRun =
                rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val uri = result.data?.data ?: run {
                            logIRRealise("Cannot save the image!")
                            return@rememberLauncherForActivityResult
                        }

                        val file = context.outputFile(uri) ?: run {
                            logIRRealise("Cannot save the image!")
                            return@rememberLauncherForActivityResult
                        }
                        uploadPhoto(file.toUri())
                    }
                }
            return {
                try {
                    launchRun.launch(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        @Composable
        fun cameraLauncher(
            uploadPhoto: (Uri) -> Unit
        ): () -> Unit {
            val context = LocalContext.current
            val imageUri by remember { mutableStateOf(context.makeUri()) }
            val launchRun =
                rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                    if (success) {
                        context.outputFile(imageUri)?.let { new ->
                            uploadPhoto(new.toUri())
                        } ?: run {
                            logE("Cannot save the image!")
                        }
                    }
                    else {
                        logE("Cannot save the image!")
                    }
                }
            return {
                try {
                    launchRun.launch(imageUri)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

fun getLocationServices(context: Context, localGeo: (List<Address>) -> Unit): Boolean {
    val locationServices = LocationServices.getFusedLocationProviderClient(context)
    @SuppressLint("MissingPermission")
    if (PermissionsModule(context).grantedLocationFine() &&
        PermissionsModule(context).grantedLocationCoarse()) {
        try {
            locationServices.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    getGeoLocationAddress(latitude, longitude, context) {
                        localGeo.invoke(it)
                    }
                    logD("location ${latitude}: $longitude")
                }
                logD("location $location")
            }.addOnFailureListener {
                logE(it.stackTraceToString())
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
    return false
}

fun getLocationDevice(context: Context, localGeo: (Pair<Double, Double>) -> Unit): Boolean {
    val locationServices = LocationServices.getFusedLocationProviderClient(context)
    @SuppressLint("MissingPermission")
    if (PermissionsModule(context).grantedLocationFine() &&
        PermissionsModule(context).grantedLocationCoarse()) {
        try {
            locationServices.lastLocation.addOnSuccessListener { location ->
                logD("locationAAA $location")
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    localGeo(Pair(latitude, longitude))
                }
                logD("location $location")
            }.addOnFailureListener {
                logE("POPPP", it.toString())
                logE(it.stackTraceToString())
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
    return false
}

fun getGeoLocationAddress(
    latitude: Double,
    longitude: Double,
    context: Context,
    localGeo: (List<Address>) -> Unit
) = CoroutineScope(Dispatchers.IO).launch {
    try {
        val geocoder = Geocoder(context, LocaleRu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                localGeo.invoke(addresses)
            }
        }
        else {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1) ?: listOf()
            localGeo.invoke(addresses)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

suspend fun getMarkerAddressDetails(
    lat: Double, long: Double, context: Context,
    isLoading: () -> Unit,
    isError: (String) -> Unit,
    isSuccess: (Address) -> Unit,
) {
    isLoading.invoke()
    try {
        //Not a good practice to pass context in vm, instead inject this Geocoder
        val geocoder = Geocoder(context, Locale.getDefault())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(
//Pass LatLng and get address
                lat,
                long,
                1,//no. of addresses you want
            ) { p0 ->
                isSuccess.invoke(p0[0])
            }
        }
        else {
            val addresses = geocoder.getFromLocation(
//This method is deprecated for >32
                lat,
                long,
                1,
            )
            if (!addresses.isNullOrEmpty()) {//The address can be null or empty
                isSuccess.invoke(addresses[0])
            }
            else {
                isError.invoke("Address is null")
            }
        }
    } catch (e: Exception) {
        e.message?.let { isError.invoke(it) }
    }
}

