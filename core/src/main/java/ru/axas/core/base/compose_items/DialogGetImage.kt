package ru.axas.core.base.compose_items
//
//import android.net.Uri
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.window.Dialog
//import ru.axas.core.main.PermissionsModule
//import ru.axas.core.theme.ThemeApp
//import ru.axas.core.theme.res.DimApp
//import ru.axas.core.theme.res.TextApp
//
//
//@Composable
//fun DialogGetImage(
//    onDismiss: () -> Unit,
//    uploadPhoto: (Uri) -> Unit,
//) {
//    var isViewDialog by remember { mutableStateOf(false) }
//    val permission = PermissionsModule.launchPermissionCameraAndGallery { permition ->
//        if (permition) {
//            isViewDialog = true
//        } else {
//            isViewDialog = false
//            onDismiss.invoke()
//        }
//    }
//
//    LaunchedEffect(key1 = Unit, block = {
//        permission.invoke()
//    })
//
//
//    if (isViewDialog) {
//        Dialog(onDismissRequest = onDismiss) {
//            val gallerySecondStep =
//                PermissionsModule.galleryLauncher { uri ->
//                    uploadPhoto(uri)
//                    onDismiss()
//                }
//            val cameraLauncherStep =
//                PermissionsModule.cameraLauncher { uri ->
//                    uploadPhoto(uri)
//                    onDismiss()
//                }
//            Column(
//                modifier = Modifier
//                    .wrapContentHeight()
//                    .clip(ThemeApp.shape.mediumAll)
//                    .background(ThemeApp.colors.backgroundMain)
//                    .padding(DimApp.screenPadding)
//            ) {
//                ButtonRowOutLine(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    onClick = {
//                        cameraLauncherStep.invoke()
//                    },
//                    text = TextApp.buttonOpenTheCamera
//                )
//
//                ButtonRowOutLine(
//                    modifier = Modifier
//                        .padding(top = DimApp.screenPadding / 2)
//                        .fillMaxWidth(),
//                    onClick = {
//                        gallerySecondStep.invoke()
//
//                    },
//                    text = TextApp.buttonOpenGallery
//                )
//
//                ButtonAccentApp(onClick = onDismiss,
//                    modifier = Modifier
//                        .padding(top = DimApp.screenPadding / 2)
//                        .fillMaxWidth(),
//                    text = TextApp.buttonCancel
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun DialogGetImageNew(
//    onDismiss: () -> Unit,
//    uploadPhoto: (Uri) -> Unit,
//) {
//    var isViewDialog by remember { mutableStateOf(false) }
//    val permission = PermissionsModule.launchPermissionCameraAndGallery { permition ->
//        if (permition) {
//            isViewDialog = true
//        } else {
//            isViewDialog = false
//            onDismiss.invoke()
//        }
//    }
//
//    LaunchedEffect(key1 = Unit, block = {
//        permission.invoke()
//    })
//
//
//    if (isViewDialog) {
//        Dialog(onDismissRequest = onDismiss) {
//            val gallerySecondStep =
//                PermissionsModule.galleryLauncher { uri ->
//                    uploadPhoto(uri)
//                    onDismiss()
//                }
//            val cameraLauncherStep =
//                PermissionsModule.cameraLauncher { uri ->
//                    uploadPhoto(uri)
//                    onDismiss()
//                }
//            Column(
//                modifier = Modifier
//                    .clip(ThemeApp.shape.mediumAll)
//                    .background(ThemeApp.colors.white)
//                    .padding(DimApp.screenPadding)
//            ) {
//
//                ButtonAccentTextApp(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    onClick = cameraLauncherStep::invoke,
//                    text = TextApp.buttonOpenTheCamera
//                )
//                BoxSpacer()
//                ButtonAccentTextApp(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    onClick = gallerySecondStep::invoke,
//                    text = TextApp.buttonOpenGallery
//                )
//                BoxSpacer()
//                ButtonAccentApp(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    onClick = onDismiss,
//                    text = TextApp.buttonCancel
//                )
//                BoxSpacer()
//            }
//        }
//    }
//}