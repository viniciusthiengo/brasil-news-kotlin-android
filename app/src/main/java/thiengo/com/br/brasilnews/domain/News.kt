package thiengo.com.br.brasilnews.domain

import android.os.Parcel
import android.os.Parcelable

class News(
        val title: String,
        val imageUrl: String,
        val description: String
    ) : Parcelable {

    constructor( source: Parcel ) : this(
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel( dest: Parcel, flags: Int ) = with( dest ) {
        writeString( title )
        writeString( imageUrl )
        writeString( description )
    }

    companion object {
        @JvmStatic
        val KEY = "news_item"

        @JvmField
        val CREATOR: Parcelable.Creator<News> = object : Parcelable.Creator<News> {
            override fun createFromParcel( source: Parcel ): News = News( source )
            override fun newArray( size: Int ): Array<News?> = arrayOfNulls( size )
        }
    }
}