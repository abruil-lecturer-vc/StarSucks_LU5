package com.example.prog7313_starsucks

import android.app.Activity
import android.content.Context
import android.content.Intent

/*
    Explicit intent: Specify which application or activity to open.
    Using explicit intent to open OrderDetails activity and pass the drink order to it.
 */
fun openIntent(context: Context, order: String, activityToOpen: Class<*>) {

    // declare intent
    val intent = Intent(context, activityToOpen)

    // pass through string value with key "order"
    intent.putExtra("order", order)

    // if context is not an activity, add a flag to intent
    if (context !is Activity) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    // start activity
    context.startActivity(intent)
}


/*
    Don't specify a specific application or activity (general action instead).
    Using implicit intent to open the Android share sheet and share the drink name the
    customer has selected, including other information.
 */
fun shareIntent(context: Context, order: String) {

    // create a new intent object for sending data
    var sendIntent = Intent()

    // sets the action of the intent to ACTION_SEND,
    // indicating that the intent is used to send data
    sendIntent.setAction(Intent.ACTION_SEND)

    // adds the text message (order) to the intent as an extra, with the key EXTRA_TEXT
    // This extra is used by the app that receives the intent to retrieve the shared text
    sendIntent.putExtra(Intent.EXTRA_TEXT, order)

    // sets the data that is being sent (think of setter in getters
    // and setters). In this case it is default
    sendIntent.type = "text/plain"

    // set up the share sheet
    // create a chooser intent that allows the user to select which app to use for sharing
    // the text message. sendIntent takes the original text and a title (null) for the
    // chooser dialogue. shareIntent provides the functionality to share the data
    val shareIntent = Intent.createChooser(sendIntent, null)

    // start the activity that shows the share sheet with the list of apps that can handle the
    // sendIntent. The user can choose an app to share the message
    context.startActivity(shareIntent)
}
