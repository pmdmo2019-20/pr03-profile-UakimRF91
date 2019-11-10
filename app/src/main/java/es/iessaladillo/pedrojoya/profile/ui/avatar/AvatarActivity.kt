package es.iessaladillo.pedrojoya.profile.ui.avatar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.profile.R
import es.iessaladillo.pedrojoya.profile.data.local.Database
import es.iessaladillo.pedrojoya.profile.data.local.entity.Avatar
import es.iessaladillo.pedrojoya.profile.ui.main.ProfileActivity
import kotlinx.android.synthetic.main.avatar_activity.*

class AvatarActivity : AppCompatActivity() {

    //No se inicializan las listas hasta que no se llamen por primera vez
    private val checkboxList: List<CheckBox> by lazy { createCheckboxList() }
    private val imageList: List<ImageView> by lazy { createImageList() }
    private val avatarList: List<Avatar> by lazy { createAvatarList() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.avatar_activity)
        setupViews()
    }

    private fun setupViews() {
        //Recibimos el avatar que nos llega de ProfileActivity y lo activamos
        setSelectedAvatar(intent.getParcelableExtra<Avatar>(EXTRA_AVATAR).id)
        setOnClickListeners()
    }

    // -- MÉTODOS DE INICIALIZACIÓN LAZY DE LAS LISTAS --
    private fun createCheckboxList(): List<CheckBox> {
        return listOf(
            chkAvatar1, chkAvatar2, chkAvatar3, chkAvatar4, chkAvatar5, chkAvatar6,
            chkAvatar7, chkAvatar8, chkAvatar9
        )
    }

    private fun createImageList(): List<ImageView> {
        return listOf(
            imgAvatar1, imgAvatar2, imgAvatar3, imgAvatar4, imgAvatar5, imgAvatar6,
            imgAvatar7, imgAvatar8, imgAvatar9
        )
    }

    private fun createAvatarList(): List<Avatar> {
        return Database.queryAllAvatars()
    }

    // -- LISTENERS PARA CHECKBOX E IMÁGENES --
    private fun setOnClickListeners() {
        setOnClickListenerCheckboxes()
        setOnClickListenerImages()
    }

    private fun setOnClickListenerImages() {
        for ((index, image) in imageList.withIndex()) {
            image.setOnClickListener { setSelectedAvatar(index + 1) }
        }
    }

    private fun setOnClickListenerCheckboxes() {
        for ((index, checkbox) in checkboxList.withIndex()) {
            checkbox.setOnClickListener { setSelectedAvatar(index + 1) }
        }
    }

    // -- MÉTODOS PARA ESTABLECER AVATAR ACTUAL --
    private fun setSelectedAvatar(id: Int) {
        allFalse()
        selectCheckbox(id)
    }

    private fun allFalse() {
        for (checkbox in checkboxList) {
            checkbox.isChecked = false
        }
    }

    private fun selectCheckbox(id: Int) {
        checkboxList[id-1].isChecked = true
    }

    // -- MÉTODOS PARA EL ENVÍO DE AVATAR A PROFILEACTIVITY MEDIANTE INTENT --
    private fun sendAvatar() {
        val id = actualAvatarId()
        startActivity(Intent(this, ProfileActivity::class.java).putExtra(EXTRA_AVATAR, avatarList[id-1]))
    }

    private fun actualAvatarId(): Int {
        for ((index, checkbox) in checkboxList.withIndex()) {
            if (checkbox.isChecked) {
                return index+1
            }
        }
        return 0
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.avatar_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuSelect) {
            sendAvatar()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_AVATAR = "EXTRA_AVATAR"
        //Facilitamos la llamada de la actividad AvatarActivity para que ProfileActivity la utilice
        fun newIntent(context: Context, avatar: Avatar) =
            Intent(context, AvatarActivity::class.java).putExtra(EXTRA_AVATAR, avatar)

    }

}


