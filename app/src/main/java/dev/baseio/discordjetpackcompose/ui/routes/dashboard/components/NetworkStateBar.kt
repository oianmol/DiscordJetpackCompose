package dev.baseio.discordjetpackcompose.ui.routes.dashboard.components

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.ui.theme.white
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

@Composable
fun NetworkStateBar() {
    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Connecting
    val isAirplaneMode = connection === ConnectionState.AirplaneMode

    if (isConnected) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
        ) {
            ConnectingAnimation()
            Text(
                text = "Connecting...",
                color = white,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    } else if (isAirplaneMode) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            ConnectingAnimation()
            Text(text = "Airplane mode active.", color = white)
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            NoNetwork()
            Text(
                text = "Network connectivity limited or unavailable.",
                color = white,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}


@Composable
fun ConnectingAnimation(
    speed: Double = 0.5,
    text: String = ""
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

    var isVisible = true

    LaunchedEffect(key1 = null, block = {
        scope.launch {
            delay(500)
            isVisible = !isVisible
        }
    })
    AnimatedVisibility(visible = isVisible) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(maxCounter) { index ->
                Bars(counter = counter, index = index)
            }
            Text(text = text, modifier = Modifier.padding(start = 4.dp))
        }
    }
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
                    1 -> 14.dp
                    2 -> 20.dp
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
        Bars(height = 8.dp, color = Color(0xFFEC4345))
        Bars(height = 14.dp, color = Color(0xFF36393F))
        Bars(height = 20.dp, color = Color(0xFF36393F))
    }
}

@Composable
fun Bars(
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
    object Unavailable : ConnectionState()
    object Connecting : ConnectionState()
    object AirplaneMode : ConnectionState()
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
        ConnectionState.Connecting
    } else {
        ConnectionState.Unavailable
    }
}

fun execute(): Boolean {
    return try {
        val socket = Socket()
        socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
        socket.close()
        true
    } catch (e: IOException) {
        Timber.e("No Internet Connection: $e")
        false
    }
}

fun networkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Connecting)
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

