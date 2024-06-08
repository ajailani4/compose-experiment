package com.ajailani.composeexperiment.util

import androidx.compose.runtime.Composable
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object MixpanelUtil : KoinComponent {
    private val mixpanel: MixpanelAPI by inject()

    fun identify(userId: String, profileUpdatable: Boolean = true) {
        mixpanel.identify(userId, profileUpdatable)
    }

    fun reset() {
        mixpanel.reset()
    }

    fun trackPageView(pageName: String) {
        val jsonObject = JSONObject().apply { put("page", pageName) }

        mixpanel.track("Page View", jsonObject)
    }

    fun track(event: String) {
        mixpanel.track(event)
    }

    fun track(event: String, properties: Map<String, String>) {
        val jsonObject = JSONObject()

        properties.forEach { (key, value) ->
            jsonObject.put(key, value)
        }

        mixpanel.track(event, jsonObject)
    }

    fun updateProfile(attributes: Map<String, String>) {
        attributes.forEach { (key, value) ->
            mixpanel.people.set(key, value)
        }
    }
}

@Composable
fun PageViewTracker(pageName: String) {
    /*LaunchedEffect(Unit) {
        MixpanelUtil.trackPageView(pageName)
    }*/
}