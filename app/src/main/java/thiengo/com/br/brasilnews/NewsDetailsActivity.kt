package thiengo.com.br.brasilnews

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_news_details.*
import kotlinx.android.synthetic.main.content_news_details.*
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import thiengo.com.br.brasilnews.domain.News
import thiengo.com.br.brasilnews.util.getBitmapImageViewUri

class NewsDetailsActivity :
    AppCompatActivity(),
    EasyPermissions.PermissionCallbacks {

    companion object {
        const val PERMISSION_STORAGE = 2456
    }

    lateinit var news : News

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_news_details )
        setSupportActionBar( toolbar )

        supportActionBar?.setDisplayHomeAsUpEnabled( true )

        news = intent.getParcelableExtra( News.KEY )

        /*
         * Colocando dados em tela.
         * */
        tv_title.text = news.title
        tv_description.text = news.description

        Picasso
            .get()
            .load( news.imageUrl )
            .into( iv_banner )

        iv_banner.contentDescription = String.format(
            "%s %s",
            getString(R.string.image_of_label),
            news.title
        )
    }

    /*
     * Hackcode para sempre utilizarmos o único título
     * de categoria disponível no projeto de exemplo.
     * */
    override fun onResume() {
        super.onResume()
        toolbar.title = getString( R.string.item_label_news )
    }

    /*
     * Neste método, que está diretamente vinculado ao
     * botão de compartilhamento, nós solicitaremos a
     * permissão de acesso ao SDCard para que seja possível
     * compartilhar também a imagem principal do artigo.
     * */
    fun shareNewsPermission( view: View){

        EasyPermissions.requestPermissions(
            PermissionRequest
                .Builder(
                    this,
                    PERMISSION_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .setRationale( R.string.permission_inform )
                .build()
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray ) {

        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults )

        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this )
    }

    override fun onPermissionsDenied(
        requestCode: Int,
        perms: MutableList<String> ) {

        Toast
            .makeText(
                this@NewsDetailsActivity,
                getString(R.string.share_without_image),
                Toast.LENGTH_SHORT
            )
            .show()

        shareNewsWithoutImage()
    }

    override fun onPermissionsGranted(
        requestCode: Int,
        perms: MutableList<String> ) {

        shareNewsWithImage()
    }

    /*
     * Método que será invocado quando a permissão de
     * acesso ao SDCard for concedida e assim a imagem
     * principal do artigo também esta liberada para
     * compartilhamento.
     * */
    private fun shareNewsWithImage(){
        val bitmapUri = getBitmapImageViewUri(this, iv_banner)
        val intent = Intent()

        intent.putExtra( Intent.EXTRA_STREAM, bitmapUri )
        intent.type = "image/*"

        shareNewsChooserIntent( intent )
    }

    /*
     * Método que será invocado quando a permissão de
     * acesso ao SDCard não for concedida.
     * */
    private fun shareNewsWithoutImage(){
        val intent = Intent()

        intent.type = "text/plain"

        shareNewsChooserIntent( intent )
    }

    private fun shareNewsChooserIntent( intent: Intent ){

        val body = String.format(
            "%s %s\n\n%s",
            getString( R.string.initial_share_body ),
            news.title,
            news.description
        )

        intent.action = Intent.ACTION_SEND
        intent.putExtra( Intent.EXTRA_SUBJECT, news.title )
        intent.putExtra( Intent.EXTRA_TEXT, body )

        if( intent.resolveActivity( packageManager ) != null ) {
            val intentChooser = Intent.createChooser(
                intent,
                getString( R.string.chooser_title )
            )

            startActivity( intentChooser )
        }
    }
}
