package com.example.acpractica1.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.acpractica1.R
import com.example.acpractica1.ui.screens.home.Screen
import com.example.acpractica1.ui.theme.GreyBack
import com.example.acpractica1.ui.theme.PinkBack
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Esta función construye la pantalla Detail de la aplicación de la mano de un
// ViewModel que contiene el estado de la pantalla
fun DetailScreen(vm: DetailViewModel, onBack: () -> Unit) {
    // Para no trabajar directamente con el estado que proporciona el ViewModel
    // y poder llamarlo de una manera más simple
    val state by vm.state.collectAsState()
    //val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
    Screen { // Objeto Compose para construir la pantalla
        Scaffold(  // Objeto Compose de layout para crear interfaces con Material
            // Design que ubica componentes predefinidos sobre un contenedor.
            topBar = {
                TopAppBar( // Objeto Compose para construir la barra principal de la pantalla
                    title = {
                        val nompais = state.country?.cname ?: ""
                        val nomCont = state.country?.ccontinent ?: ""
                        Text(text = "$nompais($nomCont)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF03583f)
                    ),
                    navigationIcon = {
                        IconButton(onClick = onBack ) {
                            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.back),
                                tint = Color.White
                            )
                        }
                    },
                    //scrollBehavior = scrollBehaviour
                )
            },
            // Para asegurar que al rotar el dispositivo pueda hacer scroll
            //modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection)
        ) { padding ->
            if (state.loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.country?.let { country ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = country.cflag,
                            contentDescription = country.cflag,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16 / 9f)
                        )
                        //Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = country.cfname,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(GreyBack)
                                .padding(4.dp),
                            style = MaterialTheme.typography.headlineMedium,
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    // Aquí se mete el estilizado de Property
                    Text(text = buildAnnotatedString { 
                        PropertyL1(name = "Habitantes", value = country.cpopul)
                        PropertyL1(name = "Presidente", value = country.cpres)
                        PropertyL1(name = "Moneda", value = country.ccurrency)
                    })
                    // sustituyendo esto
                    /*Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        //horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Habitantes:",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 22.sp,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(2.dp)
                        )
                        Text(
                            text = country.cpopul,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(2.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Presidente:",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 22.sp,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(2.dp)
                        )
                        Text(
                            text = country.cpres,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(2.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Moneda:",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 22.sp,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(2.dp)
                        )
                        Text(
                            text = country.ccurrency,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(2.dp)
                        )
                    }*/
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                            .clip(
                                RoundedCornerShape(
                                    15.dp, 15.dp, 15.dp, 15.dp
                                )
                            )
                            .background(color = PinkBack),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "COVID-19 Info",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 22.sp,
                                maxLines = 1,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            15.dp, 15.dp, 15.dp, 15.dp
                                        )
                                    )
                                    .background(GreyBack)
                                    .padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = buildAnnotatedString {
                            PropertyL1(name = "Casos", value = country.ccases)
                            PropertyL1(name = "Muertes", value = country.cdeaths)
                        })
                        /*Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Casos:",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 22.sp,
                                maxLines = 1,
                                modifier = Modifier
                                    .padding(6.dp)
                            )
                            Text(
                                text = country.ccases,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                modifier = Modifier
                                    .padding(2.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Muertes:",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 22.sp,
                                maxLines = 1,
                                modifier = Modifier
                                    .padding(6.dp)
                            )
                            Text(
                                text = country.cdeaths,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                modifier = Modifier
                                    .padding(2.dp)
                            )
                        }*/
                        Column(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Text(
                                text = "Última Actualización:",
                                modifier = Modifier
                                    .padding(6.dp),
                                fontStyle = FontStyle.Italic,
                                fontSize = 22.sp,
                                maxLines = 1,
                            )
                            Text(
                                text = DateTimeFormatter
                                    .ofPattern("EEEE dd 'de' MMMM 'de' yyyy 'a las' HH:mm:ss")
                                    .withLocale(Locale("es", "ES"))
                                    .format(ZonedDateTime.parse(country.ccovupdated)),
                                modifier = Modifier
                                    .padding(2.dp),
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnnotatedString.Builder.PropertyL1(name: String, value: String, end: Boolean = false) {
    withStyle(ParagraphStyle(textIndent = TextIndent(16.sp), lineHeight = 22.sp)) {
        withStyle(SpanStyle(fontSize = 22.sp)) {
            append("$name: ")
        }
        withStyle(SpanStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)) {
            append(value)
        }
        if(!end){
            append("\n")
        }
    }
}