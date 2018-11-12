package thiengo.com.br.brasilnews

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import thiengo.com.br.brasilnews.domain.News

class NewsAdapter(
    private val context: Context,
    private val newsList: List<News> ) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int ) : NewsAdapter.ViewHolder {

        val v = LayoutInflater
            .from( context )
            .inflate( R.layout.news, parent, false )

        return ViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int ) {

        holder.setModel( newsList[ position ] )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder( itemView: View ) :
        RecyclerView.ViewHolder( itemView ),
        View.OnClickListener {

        val ivBanner: ImageView
        val tvTitle: TextView

        init {
            itemView.setOnClickListener( this )

            ivBanner = itemView.findViewById( R.id.iv_banner )
            tvTitle = itemView.findViewById( R.id.tv_title )
        }

        fun setModel( news: News ) {
            tvTitle.text = news.title

            Picasso
                .get()
                .load( news.imageUrl )
                .into( ivBanner )

            ivBanner.contentDescription = String.format(
                "%s %s",
                context.resources.getString(R.string.image_of_label),
                news.title
            )
        }

        override fun onClick( v: View ) {
            val intent = Intent( context, NewsDetailsActivity::class.java )
            intent.putExtra( News.KEY, newsList[ adapterPosition ] )
            context.startActivity(intent)
        }
    }
}