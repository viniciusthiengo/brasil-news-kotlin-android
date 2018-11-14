package thiengo.com.br.brasilnews.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import java.io.ByteArrayOutputStream

/*
 * Obtém o Bitmap do ImageView passado como parâmetro e
 * então retorna a URI desse Bitmap.
 * */
fun getBitmapImageViewUri(
    context: Context,
    imageView: ImageView ): Uri {

    val drawable = imageView.getDrawable() as BitmapDrawable
    val bitmap = drawable.bitmap

    val bytes = ByteArrayOutputStream()
    bitmap.compress(
        Bitmap.CompressFormat.JPEG,
        100,
        bytes
    )

    val path = MediaStore.Images.Media.insertImage(
        context.getContentResolver(),
        bitmap,
        "",
        null
    )

    return Uri.parse( path )
}