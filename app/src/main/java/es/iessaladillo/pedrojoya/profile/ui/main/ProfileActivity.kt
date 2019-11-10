package es.iessaladillo.pedrojoya.profile.ui.main

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.profile.R
import es.iessaladillo.pedrojoya.profile.data.local.Database
import es.iessaladillo.pedrojoya.profile.data.local.entity.Avatar
import es.iessaladillo.pedrojoya.profile.ui.avatar.AvatarActivity
import es.iessaladillo.pedrojoya.profile.ui.presenter.ProfileViewModel
import es.iessaladillo.pedrojoya.profile.utils.*
import kotlinx.android.synthetic.main.profile_activity.*

class ProfileActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var currentAvatar: Avatar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        setupViews()
    }

    private fun setupViews() {
        recoverData()
        imgAvatar.setOnClickListener {
            //Cuando se vaya a cambiar de actividad se guardan primero los datos actuales
            saveData()
            navigateToAvatarActivity()
        }
        imgEmail.setOnClickListener { emailIntent() }
        imgPhonenumber.setOnClickListener { callIntent() }
        imgAddress.setOnClickListener { searchInMapIntent() }
        imgWeb.setOnClickListener { viewUriIntent() }
        txtName.requestFocus()
    }

    // -- GUARDADO Y RECUPERACIÓN DE DATOS A TRAVÉS DEL VIEWMODEL --
    private fun recoverData() {
        //Recuperamos todos los datos que se han podido perder al destruir la actividad
        txtName.setText(viewModel.getName())
        txtEmail.setText(viewModel.getEmail())
        txtPhonenumber.setText(viewModel.getPhone())
        txtAddress.setText(viewModel.getAddress())
        txtWeb.setText(viewModel.getWeb())
        setCurrentAvatar()
    }

    private fun saveData() {
        //Guardamos todos los datos actuales por si se destruye la actividad, y así poder
        //recuperarlos cuando se vuelva a crear
        viewModel.saveAvatar(currentAvatar)
        viewModel.saveName(txtName.text.toString())
        viewModel.saveEmail(txtEmail.text.toString())
        viewModel.savePhone(txtPhonenumber.text.toString())
        viewModel.saveAddress(txtAddress.text.toString())
        viewModel.saveWeb(txtWeb.text.toString())
    }

    private fun setCurrentAvatar() {
        //El avatar que recibe la actividad lo guardamos para poder establecer nombre e imagen
        // actual, y también para poder enviarlo a la otra actividad
        //El avatar recibido puede ser nulo. En caso de serlo, se establece el avatar por defecto
        currentAvatar = receiveAvatar() ?: Database.queryDefaultAvatar()
        imgAvatar.setImageResource(currentAvatar.imageResId)
        lblAvatar.text = currentAvatar.name
    }

    private fun receiveAvatar(): Avatar? {
        //Kotlin no nos deja hacer una asignación dentro de la expresión
        //El avatar recibido puede ser nulo, por eso lo ponemos de tipo Avatar?
        val avatarReceived: Avatar?
        //Si no ha recibido nada de AvatarActivity se asigna el avatar por defecto que
        // indicamos en el ViewModel
        if (intent.getParcelableExtra<Avatar>("EXTRA_AVATAR") == null) {
            avatarReceived = viewModel.getAvatar()
        } else { //Si ha recibido un avatar de AvatarActivity, asignamos ese
            avatarReceived = intent.getParcelableExtra("EXTRA_AVATAR")
        }
        return avatarReceived
    }

    private fun navigateToAvatarActivity() {
        //Utilizamos el método estático newIntent que hemos creado en AvatarActivity
        val intent = AvatarActivity.newIntent(this, currentAvatar)
        startActivity(intent)
    }

    // -- INTENTS --
    private fun emailIntent() {
        this.imgAvatar.hideSoftKeyboard()
        val currentEmail = txtEmail.text.toString()
        when {
            !currentEmail.isValidEmail() -> showErrorEmail()
            isActivityAvailable(this, newEmailIntent(txtEmail.text.toString())) ->
                startActivity(newEmailIntent(currentEmail))
            else -> toastAppNotFound()
        }
    }

    private fun callIntent() {
        this.imgPhonenumber.hideSoftKeyboard()
        val currentPhone = txtPhonenumber.text.toString()
        when {
            !currentPhone.isValidPhone() -> showErrorPhone()
            isActivityAvailable(this, newDialIntent(txtPhonenumber.text.toString())) ->
                startActivity(newDialIntent(currentPhone))
            else -> toastAppNotFound()
        }
    }

    private fun searchInMapIntent() {
        this.imgAddress.hideSoftKeyboard()
        val currentAddress = txtAddress.text.toString()
        when {
            currentAddress.isBlank() -> showErrorAddress()
            isActivityAvailable(this, newSearchInMapIntent(txtAddress.text.toString())) ->
                startActivity(newSearchInMapIntent(currentAddress))
            else -> toastAppNotFound()
        }
    }

    private fun viewUriIntent() {
        this.imgWeb.hideSoftKeyboard()
        val currentUrl = txtWeb.text.toString()
        when {
            !currentUrl.isValidUrl() -> showErrorUrl()
            isActivityAvailable(this, newViewUriIntent(Uri.parse(txtWeb.text.toString()))) ->
                startActivity(newViewUriIntent(Uri.parse(currentUrl)))
            else -> toastAppNotFound()
        }
    }

    // -- MENSAJES DE ERROR --
    private fun showErrorName() {
        txtName.error = getString(R.string.profile_invalid_name)
    }

    private fun showErrorEmail() {
        txtEmail.error = getString(R.string.profile_invalid_email)
    }

    private fun showErrorPhone() {
        txtPhonenumber.error = getString(R.string.profile_invalid_phonenumber)
    }

    private fun showErrorAddress() {
        txtAddress.error = getString(R.string.profile_invalid_address)
    }

    private fun showErrorUrl() {
        txtWeb.error = getString(R.string.profile_invalid_web)
    }

    private fun checkErrors() {
        when {
            txtName.text.toString().isBlank() -> showErrorName()
            !txtEmail.text.toString().isValidEmail() -> showErrorEmail()
            !txtPhonenumber.text.toString().isValidPhone() -> showErrorPhone()
            txtAddress.text.toString().isBlank() -> showErrorAddress()
            !txtWeb.text.toString().isValidUrl() -> showErrorUrl()
            else -> toast(getString(R.string.saved))
        }
    }

    private fun toastAppNotFound() {
        toast(getString(R.string.appNotFound))
    }

    // -- MENU --
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.profile_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuSave) {
            save()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun save() {
        checkErrors()
    }
}
