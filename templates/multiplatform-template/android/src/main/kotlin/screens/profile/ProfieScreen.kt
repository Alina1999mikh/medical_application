import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.exception.DataException
import com.myapplication.model.FullName
import com.myapplication.model.Profile
import screens.profile.imagePicker
import theme.color.appTheme
import theme.color.getTextFieldColors
import theme.color.saveButton
import theme.darkAndLightTheme.TypeTheme
import theme.darkAndLightTheme.darkAndLightColors.imagePickerTheme
import theme.darkAndLightTheme.radioButtonTheme
import tools.datePickerTextField
import tools.getTextColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun profileScreen(navController: NavHostController, profile: MutableState<Profile>) {
    val widthField = 300.dp
    val editProfile = remember { mutableStateOf(false) }
    val fName = remember { mutableStateOf(profile.value.name.firstName) }
    val sName = remember { mutableStateOf(profile.value.name.secondName) }
    val date = remember { mutableStateOf(profile.value.dateOfBirth) }
    val gender = remember { mutableStateOf(profile.value.genderToString) }

    var prevFName = profile.value.name.firstName
    var prevSName = profile.value.name.secondName
    var prevDate = profile.value.dateOfBirth
    var prevGender = profile.value.genderToString
    val type = TypeTheme.getTypeTheme()
    val typeOfThemeApp = remember { mutableStateOf(type) }

    appTheme {
        Scaffold(
            topBar = {
                TopAppBar {
                    if (editProfile.value) {
                        editProfile.value = iconButtonTopBar(editProfile.value, Icons.Filled.ArrowBack)
                        fName.value = prevFName
                        sName.value = prevSName
                        date.value = prevDate
                        gender.value = prevGender
                    }
                    Text("Healthynetic", fontSize = 22.sp, modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(Modifier.weight(1f, true))
                    if (!editProfile.value) {
                        prevFName = fName.value
                        prevSName = sName.value
                        prevDate = date.value
                        prevGender = gender.value
                        editProfile.value = iconButtonTopBar(editProfile.value, Icons.Filled.Edit)
                    }
                }
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 10.dp).width(widthField),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                imagePickerTheme {
                    imagePicker(profile.value, editProfile.value)
                }
                val context = LocalContext.current

                TextField(
                    value = fName.value,
                    modifier = Modifier.width(widthField),
                    label = { Text(text = "Имя") },
                    singleLine = true,
                    enabled = editProfile.value,
                    colors = getTextFieldColors(),
                    onValueChange = {
                        try {
                            fName.value = it
                            profile.value.name = FullName(it, sName.value)
                        } catch (e: DataException) {
                            //TODO name incorrect
                        }
                    }
                )
                TextField(
                    value = (sName.value),
                    modifier = Modifier.width(widthField),
                    label = { Text(text = "Фамилия") },
                    singleLine = true,
                    enabled = editProfile.value,
                    colors = getTextFieldColors(),
                    onValueChange = {
                        try {
                            sName.value = it
                            profile.value.name = FullName(profile.value.name.firstName, it)
                        } catch (e: DataException) {
                            //TODO name incorrect
                        }
                    }
                )

                date.value = datePickerTextField(
                    context = context,
                    calendar = date.value,
                    enabled = editProfile.value,
                    width = widthField,
                    label = "Дата рождения"
                )
                Row(
                    modifier = Modifier.width(widthField)
                ) {
                    TextField(
                        value = (gender.value),
                        modifier = Modifier.width(getWidthWithIconByEnabled(editProfile.value, widthField, 50.dp)),
                        label = { Text(text = "Пол") },
                        singleLine = true,
                        enabled = false,
                        colors = getTextFieldColors(),
                        onValueChange = {
                        }
                    )
                    if (editProfile.value) {
                        IconButton(
                            onClick = {
                                if (gender.value == "Мужской") gender.value = "Женский"
                                else gender.value = "Мужской"
                            },
                            Modifier.width(50.dp).padding(top = 20.dp)
                        ) {
                            Icon(
                                Icons.Filled.Refresh,
                                "contentDescription",
                            )
                        }
                    }
                }
                if (editProfile.value) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = saveButton,
                                contentColor = getTextColor()
                            ),
                            elevation = null,
                            enabled = editProfile.value,
                            onClick = {
                                //todo update in database
                                editProfile.value = false
                                Toast.makeText(context, "Сохранено", Toast.LENGTH_LONG).show()
                            }
                        ) {
                            Text("Сохранить", fontStyle = FontStyle.Normal, fontSize = 15.sp)
                        }
                    }
                }
                if (!editProfile.value)
                    radioButtonTheme(typeOfThemeApp.value, navController)
                else {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Red
                        ),
                        elevation = null,
                        enabled = editProfile.value,
                        onClick = {
                            //todo
//                            profile.value.clearProfile()
//                            LogIn(navController,profile)
                        }
                    ) {
                        Text("Выйти", fontStyle = FontStyle.Normal, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun iconButtonTopBar(editProfile: Boolean, icon: ImageVector): Boolean {
    var flagEdit by rememberSaveable { mutableStateOf(editProfile) }
    IconButton(
        onClick = {
            flagEdit = !flagEdit
        },
        Modifier.width(50.dp)
    ) {
        Icon(
            icon,
            "contentDescription"
        )
    }
    return flagEdit
}

fun getWidthWithIconByEnabled(enabled: Boolean, widthField: Dp, iconSize: Dp): Dp {
    return if (enabled) widthField - iconSize
    else {
        widthField
    }
}