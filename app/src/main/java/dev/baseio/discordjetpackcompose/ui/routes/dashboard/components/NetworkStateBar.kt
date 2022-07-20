package dev.baseio.discordjetpackcompose.ui.routes.dashboard.components

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.Typography
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

@Composable
fun NetworkStateBar(
    modifier: Modifier = Modifier
) {

    val connection by connectivityState()
    val airplane by isAirplaneMode()

    val isConnected by remember(key1 = connection) {
        mutableStateOf(connection == ConnectionState.Available)
    }

    val isAirplaneModeOn by remember(key1 = airplane) {
        mutableStateOf(airplane == ConnectionState.AirplaneModeOn)
    }


    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        //Logic Needs to be fixed. Not updating always
        if (isAirplaneModeOn) {
            Icon(
                painter = painterResource(id = R.drawable.ic_airplanemode),
                contentDescription = null,
                modifier = Modifier.rotate(90f),
                tint = DiscordColorProvider.colors.textPrimary
            )
            NetworkStateText { R.string.airplane_mode_is_active }
        } else {
            if (isConnected) {
                ConnectingAnimation()
            } else {
                NoNetwork()
                NetworkStateText { R.string.no_internet_status }
            }
        }
    }
}


@Composable
fun ConnectingAnimation(
    speed: Double = 0.5
) {
    val maxCounter = 3
    var counter by remember {
        mutableStateOf(0)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = speed, block = {
        scope.launch {
            while (true) {
                delay(speed.times(500).toLong())
                counter = if (counter == (maxCounter - 1)) 0 else counter + 1
            }
        }
    })

    var isVisible by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(
        key1 = null,
        block = {
            scope.launch {
                delay(3000)
                isVisible = !isVisible
            }
        }
    )

    AnimatedVisibility(visible = isVisible) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(maxCounter) { index ->
                Bars(counter = counter, index = index)
            }
            NetworkStateText { R.string.connecting }
        }
    }
}

@Composable
fun NetworkStateText(textProvider: () -> Int) {
    Text(
        text = stringResource(id = textProvider()),
        style = Typography.caption.copy(color = DiscordColorProvider.colors.textPrimary),
        modifier = Modifier.padding(start = 4.dp)
    )
}

@Composable
fun Bars(
    counter: Int,
    index: Int
) {
    val colors by animateColorAsState(
        targetValue = when (counter) {
            index -> {
                Color(0xFFA77C30)
            }
            (index - 1) -> {
                Color(0xFF6E5A3F)
            }
            else -> {
                Color(0xFF404047)
            }
        }
    )
    Box(
        modifier = Modifier
            .width(6.dp)
            .height(
                when (index) {
                    0 -> 8.dp
                    1 -> 12.dp
                    2 -> 16.dp
                    else -> 0.dp
                }
            )
            .padding(end = 2.dp)
            .background(colors, shape = RoundedCornerShape(3.dp))
    )
}

@Composable
fun NoNetwork() {
    Row(verticalAlignment = Alignment.Bottom) {
        NonAnimatedBar(height = 8.dp, color = Color(0xFFEC4345))
        NonAnimatedBar(height = 12.dp, color = Color(0xFF36393F))
        NonAnimatedBar(height = 16.dp, color = Color(0xFF36393F))
    }
}

@Composable
fun NonAnimatedBar(
    height: Dp,
    color: Color
) {
    Box(
        modifier = Modifier
            .width(6.dp)
            .height(height)
            .padding(end = 2.dp)
            .background(color, shape = RoundedCornerShape(3.dp))
    )
}


sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
    object AirplaneModeOn : ConnectionState()
    object AirplaneModeOff : ConnectionState()
}


/**
 * Network utility to get current state of internet connection
 */
val Context.currentConnectivityState: ConnectionState
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }


private fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager
): ConnectionState {
    val connected = connectivityManager.allNetworks.any { network ->
        connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }


    return if (connected) {
        ConnectionState.Available
    } else {
        ConnectionState.Unavailable
    }
}

/**
 * Network Utility to observe availability or unavailability of Internet connection
 */
@ExperimentalCoroutinesApi
fun Context.observeConnectivityAsFlow() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = networkCallback { connectionState -> trySend(connectionState) }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    val currentState = getCurrentConnectivityState(connectivityManager)
    trySend(currentState)

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}


fun networkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.Unavailable)
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityState) {
        context.observeConnectivityAsFlow().collect { value = it }
    }
}


@Composable
fun isAirplaneMode(): State<ConnectionState> {
    val context = LocalContext.current
    val mode = Settings.System.getInt(
        context.contentResolver,
        Settings.Global.AIRPLANE_MODE_ON, 0
    ) != 0

    return remember {
        mutableStateOf(
            if (mode) {
                ConnectionState.AirplaneModeOn
            } else {
                ConnectionState.AirplaneModeOff
            }
        )
    }
}

